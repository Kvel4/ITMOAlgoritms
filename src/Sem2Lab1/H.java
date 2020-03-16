package Sem2Lab1;

import java.io.*;
import java.util.*;


public class H {
    private static Node[] tree;
    private static int n;

    private static int nextDegree(int n) {
        int tmp = 1;
        while (tmp < n) {
            tmp *= 2;
        }
        return tmp;
    }

    private static void build() {
        for (int i = 0; i < 2 * n; i++) {
            tree[i] = new Node();
        }
    }

    private static void propagate(int v, int lx, int rx) {
        if (rx - lx == 1) {
            return;
        }
        if (tree[v].set == null) {
            return;
        }
        tree[2 * v].value = tree[v].set;
        tree[2 * v].set = tree[v].set;
        tree[2 * v + 1].value = tree[v].set;
        tree[2 * v + 1].set = tree[v].set;
        tree[v].set = null;
    }

    private static int min(int l, int r, int v, int lx, int rx) {
        propagate(v, lx, rx);
        if (r <= lx || l >= rx) {
            return Integer.MAX_VALUE;
        }
        if (l <= lx &&  rx <= r) {
            return tree[v].value;
        }
        int m = (lx + rx) / 2;
        return Math.min(min(l, r, 2 * v, lx, m), min(l, r, 2 * v + 1, m, rx));
    }

    private static void set(int l, int r, int value, int v, int lx, int rx) {
        propagate(v, lx, rx);
        if (r <= lx || l >= rx) {
            return;
        }
        if (l <= lx &&  rx <= r) {
            tree[v].value = value;
            tree[v].set = value;
            return;
        }
        int m = (lx + rx) / 2;
        set(l, r, value, 2 * v, lx, m);
        set(l, r, value, 2 * v + 1, m, rx);
        tree[v].value = Math.min(tree[2*v].value, tree[2*v + 1].value);
    }

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new FileReader("rmq.in"));
        String[] elements = br.readLine().split(" ");
        int amountOfElements = Integer.parseInt(elements[0]), m = Integer.parseInt(elements[1]);
        n = nextDegree(amountOfElements);
        Inquiry[] inquiries = new Inquiry[m];
        tree = new Node[n * 2];
        build();

        for (int i = 0; i < m; i++) {
            elements = br.readLine().split(" ");
            inquiries[i] = new Inquiry(Integer.parseInt(elements[0]),
                    Integer.parseInt(elements[1]), Integer.parseInt(elements[2]));
        }

        Arrays.sort(inquiries);

        for (int i = 0; i < m; i++) {
            set(inquiries[i].l - 1, inquiries[i].r, inquiries[i].val, 1, 0, n);
        }

        boolean correct = true;
        for (int i = 0; i < m; i++) {
            if (min(inquiries[i].l - 1, inquiries[i].r,1, 0, n) != inquiries[i].val) {
                correct = false;
            }
        }

        PrintWriter pw = new PrintWriter("rmq.out");
        if (correct) {
            pw.println("consistent");
            for (int i = 0; i < amountOfElements; i++) {
                pw.print(min(i, i + 1,1, 0, n) + " ");
            }
        } else {
            pw.println("inconsistent");
        }
        pw.close();
        br.close();
    }

    private static class Inquiry implements Comparable<Inquiry> {
        int l, r, val;

        Inquiry(int l, int r, int val) {
            this.l = l;
            this.r = r;
            this.val = val;
        }

        @Override
        public int compareTo(Inquiry o) {
            if (this.val > o.val){
                return 1;
            } else if (this.val == o.val) {
                return 0;
            }
            return -1;
        }
    }

    private static class Node {
        int value = Integer.MAX_VALUE;
        Integer set = null;
    }
}
