package Sem2Lab2;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;

public class Treap {
    static private Random random = new Random();
    public int x, size;
    private int y;
    private Treap left, right;

    public Treap(int x) {
        this.x = x;
        this.y = random.nextInt();
        this.size = 1;
    }

    private Treap(int x, int y, Treap left, Treap right) {
        this.x = x;
        this.y = y;
        this.left = left;
        this.right = right;
        this.size = countSize(left, right);
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
        resize();
        return this;
    }

    public Treap delete(int x) {
        if (this.x == x) {
            return merge(left, right);
        }

        if (x > this.x) {
            if (right != null) {
                right = right.delete(x);
            }
        }
        if (x < this.x) {
            if (left != null) {
                left = left.delete(x);
            }
        }
        resize();
        return this;
    }

    public Treap next(int x) {
        Treap[] splited = split(x + 1);
        Treap cur = splited[1];

        if(cur != null) {
            while(cur.left != null) {
                cur = cur.left;
            }
            return cur;
        }
        return null;
    }

    public Treap prev(int x) {
        Treap[] splited = split(x);
        Treap cur = splited[0];

        if(cur != null) {
            while(cur.right != null) {
                cur = cur.right;
            }
            return cur;
        }
        return null;
    }

    public Treap kMax(int k) {
        if (k == getSize(right) + 1) {
            return this;
        }

        if (k < getSize(right) + 1) {
            return right.kMax(k);
        } else {
            return left.kMax(k - getSize(right) - 1);
        }
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

    private static int countSize(Treap left, Treap right) {
        if (left == null && right != null) { return right.size + 1; }
        if (left != null && right == null) { return left.size + 1; }
        if (left != null && right != null) { return left.size + right.size + 1; }
        return 1;
    }

    private static int getSize(Treap treap){
        if (treap == null) {
            return 0;
        }
        return treap.size;
    }

    private void resize() {
        size = countSize(left, right);
    }

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        Treap treap = null;
        String string;

        while ((string = br.readLine()) != null) {
            String[] s = string.split(" ");
            int x = Integer.parseInt(s[1]);
            switch (s[0]) {
                case "insert":
                    if (treap == null) {
                        treap = new Treap(x);
                        break;
                    }
                    treap = treap.insert(x);
                    break;
                case "delete":
                    if (treap == null) {
                        break;
                    }
                    treap = treap.delete(x);
                    break;
                case "exists":
                    if (treap == null) {
                        System.out.println("false");
                        break;
                    }
                    System.out.println(treap.exists(x));
                    break;
                case "next":
                    if (treap == null) {
                        System.out.println("none");
                        break;
                    }

                    Treap next = treap.next(x);
                    if (next == null) {
                        System.out.println("none");
                        break;
                    }
                    System.out.println(next.x);
                    break;
                case "prev":
                    if (treap == null) {
                        System.out.println("none");
                        break;
                    }

                    Treap prev = treap.prev(x);
                    if (prev == null) {
                        System.out.println("none");
                        break;
                    }
                    System.out.println(prev.x);
                    break;
            }
        }
    }
}
