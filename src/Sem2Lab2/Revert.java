//package Sem2Lab2;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Random;

public class Revert {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String[] s = br.readLine().split(" ");
        int n = Integer.parseInt(s[0]), m = Integer.parseInt(s[1]);

        ImplicitTreap treap = new ImplicitTreap(1);
        for (int i = 2; i <= n; i++) {
            treap = ImplicitTreap.merge(treap, new ImplicitTreap(i));
        }

        for (int i = 0; i < m; i++) {
            s = br.readLine().split(" ");
            treap = treap.revert(Integer.parseInt(s[0]), Integer.parseInt(s[1]));
        }

        treap.print();
    }

    private static class ImplicitTreap {
        private static Random random = new Random();
        private int val, y, size;
        private boolean rev = false;
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

        public ImplicitTreap revert(int l, int r) {
            if (l == r) {
                return this;
            }
            ImplicitTreap[] splited = split(l - 1);
            ImplicitTreap[] rSplited = splited[1].split(r - l + 1);
            rSplited[0].rev = !rSplited[0].rev;
            return merge(splited[0], merge(rSplited[0], rSplited[1]));
        }

        private static ImplicitTreap merge(ImplicitTreap left, ImplicitTreap right) {
            if (left == null) {
                return right;
            }
            if (right == null) {
                return left;
            }
            left.propagate();
            right.propagate();
            if (left.y > right.y) {
                return new ImplicitTreap(left.val, left.y, left.left, merge(left.right, right));
            }
            return new ImplicitTreap(right.val, right.y, merge(left, right.left), right.right);
        }

        private ImplicitTreap[] split(int pos) {
            propagate();
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

        private void propagate() {
            if (rev) {
                ImplicitTreap tmp = left;
                left = right;
                right = tmp;

                if (left != null) {
                    left.rev = !left.rev;
                }
                if (right != null) {
                    right.rev = !right.rev;
                }

                rev = false;
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

        public void print() {
            propagate();

            if (left != null) {
                left.print();
            }
            System.out.print(val + " ");
            if (right != null) {
                right.print();
            }
        }
    }
}
