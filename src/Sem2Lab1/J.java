package Sem2Lab1;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;


public class J {
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
        for (int i = n; i < 2 * n; i++) {
            tree[i] = new Node(i);
        }

        for (int i = n-1; i > 0; i--) {
            tree[i] = new Node();
            tree[i].pos = tree[2*i].pos;
        }
    }

    private static void propagate(int v, int lx, int rx) {
        if (rx - lx == 1) {
            return;
        }
        if (tree[v].add == 0) {
            return;
        }
        if (tree[v].add > tree[2 * v].value) {
            tree[2*v].value = tree[v].add;
        }
        tree[2 * v].value = Math.max(tree[2 * v].value, tree[v].add);
        tree[2 * v].add = Math.max(tree[v].add, tree[2*v].add);
        tree[2 * v + 1].value = Math.max(tree[2 * v + 1].value, tree[v].add);
        tree[2 * v + 1].add = Math.max(tree[v].add, tree[2*v + 1].add);;
        tree[v].add = 0;
    }

    private static int[] min(int l, int r, int v, int lx, int rx) {
        propagate(v, lx, rx);
        if (r <= lx || l >= rx) {
            return new int[]{Integer.MAX_VALUE, -1};
        }
        if (l <= lx &&  rx <= r) {
            return new int[]{tree[v].value, tree[v].pos};
        }
        int m = (lx + rx) / 2;
        int[] L = min(l, r, 2 * v, lx, m), R = min(l, r, 2 * v + 1, m, rx);
        if (L[0] < R[0]) {
            return L;
        }
        return R;
    }

    private static void add(int l, int r, int value, int v, int lx, int rx) {
        propagate(v, lx, rx);
        if (r <= lx || l >= rx) {
            return;
        }
        if (l <= lx &&  rx <= r) {
            tree[v].value = Math.max(tree[v].value, value);
            tree[v].add = value;
            return;
        }
        int m = (lx + rx) / 2;
        add(l, r, value, 2 * v, lx, m);
        add(l, r, value, 2 * v + 1, m, rx);
        if (tree[2*v].value < tree[2*v + 1].value) {
            tree[v].value = tree[2*v].value;
            tree[v].pos = tree[2*v].pos;
        } else {
            tree[v].value = tree[2*v + 1].value;
            tree[v].pos = tree[2*v + 1].pos;
        }
    }

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String[] elements = br.readLine().split(" ");
        n = nextDegree(Integer.parseInt(elements[0]));
        int m = Integer.parseInt(elements[1]);
        tree = new Node[2 * n];
        build();

        for (int i = 0; i < m; i++) {
            elements = br.readLine().split(" ");
            int l = Integer.parseInt(elements[1]), r = Integer.parseInt(elements[2]);
            if (elements[0].equals("attack")) {
                int[] tmp = min(l - 1, r, 1, 0, n);
                System.out.println(tmp[0] + " " + (tmp[1] - n + 1));
            } else {
                add(l - 1, r, Integer.parseInt(elements[3]), 1,0, n);
            }
        }
    }

    private static class Node {
        int value = 0;
        int add = 0;
        int pos;

        Node() {
            //clear
        }

        Node(int pos) {
            this.pos = pos;
        }
    }
}
