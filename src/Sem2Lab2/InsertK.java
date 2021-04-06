//package Sem2Lab2;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Random;

public class InsertK {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String[] s = br.readLine().split(" ");
        int n = Integer.parseInt(s[0]), m = Integer.parseInt(s[1]);

        ImplicitTreap treap = new ImplicitTreap(0);
        for (int i = 1; i < m; i++) {
            treap = ImplicitTreap.merge(treap, new ImplicitTreap(0));
        }

        s = br.readLine().split(" ");
        for (int i = 0; i < n; i++) {
            int pos = Integer.parseInt(s[i]);
            int emptyPos = treap.nextFree(pos);
            treap = treap.deleteIfEmpty(emptyPos);
            if (treap == null) {
                treap = new ImplicitTreap(i + 1);
                continue;
            }
            treap = treap.insert(pos - 1, new ImplicitTreap(i + 1));
        }
        System.out.println(treap.size);
        treap.print();
    }


    private static class ImplicitTreap {
        static private Random random = new Random();
        public int val, y, size;
        private boolean hasEmpty;
        private ImplicitTreap left, right;

        public ImplicitTreap(int val) {
            this.val = val;
            this.y = random.nextInt();
            this.size = 1;
            hasEmpty = val == 0;
        }

        private ImplicitTreap(int val, int y, ImplicitTreap left, ImplicitTreap right) {
            this.val = val;
            this.y = y;
            this.left = left;
            this.right = right;
            refreshEmpty();
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
            refreshEmpty();
            return this;
        }

        public ImplicitTreap deleteIfEmpty(int pos) {
            if (getSize(left) + 1 == pos) {
                if (val == 0) {
                    return merge(left, right);
                }
                return this;
            }

            if (pos > getSize(left) + 1) {
                right = right.deleteIfEmpty(pos - getSize(left) - 1);
            } else {
                left = left.deleteIfEmpty(pos);
            }
            resize();
            refreshEmpty();
            return this;
        }

        public int nextFree(int pos) {
            ImplicitTreap[] splited = split(pos - 1);
            int freePos = 0;
            if (splited[1] != null) {
                freePos = splited[1].findFree(1);
            }
            if (freePos != 0) {
                return getSize(splited[0]) + freePos;
            }
            return getSize(splited[0]) + 1;
        }

        private int findFree(int pos) {
            if (left != null && left.hasEmpty) {
                return left.findFree(pos);
            } else if (val == 0) {
                return getSize(left) + pos;
            } else if (right != null && right.hasEmpty) {
                return right.findFree(pos + getSize(left) + 1);
            }
            return 0;
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

        private void refreshEmpty() {
            hasEmpty = val == 0 || getEmpty(left) || getEmpty(right);
        }

        private static int getSize(ImplicitTreap treap) {
            if (treap == null) {
                return 0;
            }
            return treap.size;
        }

        private static boolean getEmpty(ImplicitTreap treap) {
            if (treap == null) {
                return false;
            }
            return treap.hasEmpty;
        }

        public void print() {
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
