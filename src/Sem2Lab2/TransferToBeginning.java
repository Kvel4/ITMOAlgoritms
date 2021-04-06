package Sem2Lab2;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Random;

public class TransferToBeginning {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String[] s = br.readLine().split(" ");
        int n = Integer.parseInt(s[0]), m = Integer.parseInt(s[1]);

        ImplicitTreap treap = new ImplicitTreap(1);
        for (int i = 2; i <= n; i++) {
            treap = treap.insert(i - 1, new ImplicitTreap(i));
        }

        for (int i = 0; i < m; i++) {
            s = br.readLine().split(" ");
            treap = treap.transferToBeginning(Integer.parseInt(s[0]), Integer.parseInt(s[1]));
        }

        System.out.println(treap);
    }

    private static class ImplicitTreap {
        static private Random random = new Random();
        public int val, y, size;
        private ImplicitTreap left, right;

        public ImplicitTreap(int val) {
            this.val = val;
            this.y = random.nextInt();
            this.size = 1;
        }

        private ImplicitTreap(int val, int y, ImplicitTreap left, ImplicitTreap right) {
            this.val = val;
            this.y = y;
            this.left = left;
            this.right = right;
            resize();
        }

        public ImplicitTreap insert(int pos, ImplicitTreap node) {
            if (node.y > y) {
                ImplicitTreap[] splited = split(pos);
                return new ImplicitTreap(node.val, node.y, splited[0], splited[1]);
            }

            if (pos >= getSize(left) + 1) {
                if (right != null) {
                    right = right.insert(pos - getSize(left) - 1, node);
                } else {
                    right = node;
                }
            } else {
                if (left != null) {
                    left = left.insert(pos, node);
                } else {
                    left = node;
                }
            }
            resize();
            return this;
        }

        public ImplicitTreap delete(int pos) {
            if (getSize(left) + 1 == pos) {
                return merge(left, right);
            }

            if (pos > getSize(left) + 1) {
                right = right.delete(pos - getSize(left) - 1);
            } else {
                left = left.delete(pos);
            }
            resize();
            return this;
        }

        public ImplicitTreap transferToBeginning(int l, int r) {
            ImplicitTreap[] splited = split(l - 1);
            ImplicitTreap[] rSplited = splited[1].split(r - l + 1);
            return merge(rSplited[0], merge(splited[0], rSplited[1]));
        }

        private static ImplicitTreap merge(ImplicitTreap left, ImplicitTreap right) {
            if (left == null) {
                return right;
            }
            if (right == null) {
                return left;
            }

            if (left.y > right.y) {
                return new ImplicitTreap(left.val, left.y, left.left, merge(left.right, right));
            }
            return new ImplicitTreap(right.val, right.y, merge(left, right.left), right.right);
        }

        private ImplicitTreap[] split(int pos) {
            if (pos == 0) {
                return new ImplicitTreap[]{null, this};
            }
            if (getSize(left) + 1 == pos) {
                return new ImplicitTreap[]{new ImplicitTreap(val, y, left, null), right};
            }

            if (pos > getSize(left) + 1) {
                ImplicitTreap[] splited = right.split(pos - getSize(left) - 1);
                return new ImplicitTreap[]{new ImplicitTreap(val, y, left, splited[0]), splited[1]};
            } else {
                ImplicitTreap[] splited = left.split(pos);
                return new ImplicitTreap[]{splited[0], new ImplicitTreap(val, y, splited[1], right)};
            }
        }

        private void resize() {
            if (left == null && right != null) {
                size = right.size + 1;
            }
            if (left != null && right == null) {
                size = left.size + 1;
            }
            if (left != null && right != null) {
                size = left.size + right.size + 1;
            }
            if (left == null && right == null) {
                size = 1;
            }
        }

        private static int getSize(ImplicitTreap treap) {
            if (treap == null) {
                return 0;
            }
            return treap.size;
        }

        @Override
        public String toString() {
            StringBuilder stringBuilder = new StringBuilder();

            if (left != null) {
                stringBuilder.append(left.toString());
            }
            stringBuilder.append(val).append(" ");
            if (right != null) {
                stringBuilder.append(right.toString());
            }

            return stringBuilder.toString();
        }

    }
}