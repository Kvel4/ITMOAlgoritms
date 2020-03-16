package Sem2Lab1;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;


public class C {
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
        for (int i = n - 1; i > 0; i--) {
            tree[i].value = Math.min(tree[2 * i].value, tree[2 * i + 1].value);
        }
    }

    private static void propagate(int v, int lx, int rx) {
        if (rx - lx == 1) {
            return;
        }
        if (tree[v].add == 0 && tree[v].set == null) {
            return;
        }
        if (tree[v].set != null) {
            tree[2 * v].value = tree[v].set;
            tree[2 * v].set = tree[v].set;
            tree[2 * v].add = 0;
            tree[2 * v + 1].value = tree[v].set;
            tree[2 * v + 1].set = tree[v].set;
            tree[2 * v + 1].add = 0;
            tree[v].set = null;
        }
        if (tree[v].add != 0) {
            tree[2 * v].value += tree[v].add;
            tree[2 * v].add += tree[v].add;
            tree[2 * v + 1].value += tree[v].add;
            tree[2 * v + 1].add += tree[v].add;
            tree[v].add = 0;
        }
    }

    private static long min(int l, int r, int v, int lx, int rx) {
        propagate(v, lx, rx);
        if (r <= lx || l >= rx) {
            return Long.MAX_VALUE;
        }
        if (l <= lx &&  rx <= r) {
            return tree[v].value;
        }
        int m = (lx + rx) / 2;
        return Math.min(min(l, r, 2 * v, lx, m), min(l, r, 2 * v + 1, m, rx));
    }

    private static void set(int l, int r, long value, int v, int lx, int rx) {
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

    private static void add(int l, int r, long value, int v, int lx, int rx) {
        propagate(v, lx, rx);
        if (r <= lx || l >= rx) {
            return;
        }
        if (l <= lx &&  rx <= r) {
            tree[v].value += value;
            tree[v].add += value;
            return;
        }
        int m = (lx + rx) / 2;
        add(l, r, value, 2 * v, lx, m);
        add(l, r, value, 2 * v + 1, m, rx);
        tree[v].value = Math.min(tree[2*v].value, tree[2*v + 1].value);
    }

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int m = Integer.parseInt(br.readLine());
        String[] elements = br.readLine().split(" ");
        n = nextDegree(m);
        tree = new Node[n * 2];

        for (int i = 0; i < n * 2; i++) {
            tree[i] = new Node();
            if (i >= n && i < n+m) {
                tree[i].value = Long.parseLong(elements[i-n]);
            }
        }

        build();
        String s;
        while ((s = br.readLine()) != null) {
            elements = s.split(" ");
            String operation = elements[0];
            int l = Integer.parseInt(elements[1]), r = Integer.parseInt(elements[2]);
            if (operation.equals("set")) {
                set(l - 1, r, Long.parseLong(elements[3]), 1, 0, n);
            } else if (operation.equals("add")) {
                add(l - 1 , r, Long.parseLong(elements[3]), 1, 0, n);
            } else {
                System.out.println(min(l - 1, r, 1, 0, n));
            }
        }
    }

    private static class Node {
        long value = Long.MAX_VALUE;
        Long set = null;
        long add = 0;
    }
}
