package Sem2Lab2;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Random;

public class Sum {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int n = Integer.parseInt(br.readLine());
        String prevToken = "+";
        long prevValue = 0;
        Treap treap = null;

        for (int i = 0; i < n; i++) {
            String[] s = br.readLine().split(" ");
            switch (s[0].charAt(0)) {
                case '+':
                    int x = Integer.parseInt(s[1]);
                    if (treap == null) {
                        treap = new Treap(x);
                        break;
                    }
                    if (prevToken.equals("+")) {
                        treap = treap.insert(x);
                    } else {
                        treap = treap.insert((int) ((x + prevValue) % 1_000_000_000));
                        prevToken = "+";
                    }
                    break;
                case '?':
                    int l = Integer.parseInt(s[1]), r = Integer.parseInt(s[2]);
                    prevToken = "?";
                    if (treap == null) {
                        System.out.println(0);
                        prevValue = 0;
                        break;
                    }
                    prevValue = treap.sum(l, r+1);
                    System.out.println(prevValue);
                    break;
            }
        }
    }

    private static class Treap {
        static private Random random = new Random();
        private int x, y;
        long sum;
        private Treap left, right;

        public Treap(int x) {
            this.x = x;
            this.y = random.nextInt();
            this.sum = x;
        }

        private Treap(int x, int y, Treap left, Treap right) {
            this.x = x;
            this.y = y;
            this.left = left;
            this.right = right;
            updateSum();
        }

        public boolean exists(int x) {
            if (this.x == x) {
                return true;
            }
            if (x > this.x) {
                if (right != null) {
                    return right.exists(x);
                }
            }
            if (x < this.x) {
                if (left != null) {
                    return left.exists(x);
                }
            }
            return false;
        }

        public Treap insert(int x) {
            if (exists(x)) {
                return this;
            }
            return add(new Treap(x));
        }

        private Treap add(Treap node) {
            if (node.y > this.y) {
                Treap[] splited = split(node.x);
                return new Treap(node.x, node.y, splited[0], splited[1]);
            }

            if (node.x > this.x) {
                if (right != null) {
                    right = right.add(node);
                } else {
                    right = node;
                }
            }
            if (node.x < this.x){
                if (left != null) {
                    left = left.add(node);
                } else {
                    left = node;
                }
            }
            updateSum();
            return this;
        }

        public long sum(int l, int r) {
            Treap[] splited = split(l);
            if (splited[1] == null) {
                return 0;
            }
            Treap[] rSplited = splited[1].split(r);

            return getSum(rSplited[0]);

        }

        private static Treap merge(Treap left, Treap right) {
            if (left == null) {return right;}
            if (right == null) {return left;}

            if (left.y > right.y) {
                return new Treap(left.x, left.y, left.left, merge(left.right, right));
            }
            return new Treap(right.x, right.y, merge(left, right.left), right.right);
        }

        private Treap[] split(int x) {
            if (x > this.x) {
                if (this.right == null) {
                    return new Treap[] {this, null};
                }
                Treap[] splited = right.split(x);
                return new Treap[] {new Treap(this.x, this.y, this.left, splited[0]), splited[1]};
            } else {
                if (this.left == null) {
                    return new Treap[] {null, this};
                }
                Treap[] splited = left.split(x);
                return new Treap[] {splited[0], new Treap(this.x, this.y, splited[1], this.right)};
            }
        }

        private void updateSum() {
            if (left == null && right != null) { sum = right.sum + x; }
            if (left != null && right == null) { sum = left.sum + x; }
            if (left != null && right != null) { sum = left.sum + right.sum + x; }
            if (left == null && right == null) { sum = x; }
        }

        private static long getSum(Treap treap) {
            if (treap == null) {
                return 0;
            }
            return treap.sum;
        }
    }
}
