package Sem2Lab1;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;


public class D {
    private static Node[] tree;
    private static int n;

    private static int nextDegree(int n) {
        int tmp = 1;
        while (tmp < n) {
            tmp *= 2;
        }
        return tmp;
    }

    private static void applySet(int v, int lx, int rx) {
        if (tree[v].set == 1) {
            tree[v].amount = 1;
            tree[v].length = rx-lx;
            tree[v].leftBorder = lx;
            tree[v].rightBorder = rx - 1;
        } else {
            tree[v].amount = 0;
            tree[v].length = 0;
            tree[v].leftBorder = Integer.MAX_VALUE;
            tree[v].rightBorder = Integer.MIN_VALUE;
        }
    }

    private static Node unitNodes(Node L, Node R) {
        Node result = new Node();
        result.amount = L.amount + R.amount;
        result.length = L.length + R.length;
        result.leftBorder = Math.min(L.leftBorder, R.leftBorder);
        result.rightBorder = Math.max(L.rightBorder, R.rightBorder);
        if (R.leftBorder - L.rightBorder == 1) {
            result.amount -= 1;
        }
        return result;
    }

    private static void propagate(int v, int lx, int rx) {
        if (rx - lx == 1) {
            return;
        }
        if (tree[v].set == null) {
            return;
        }
        int m = (lx + rx) / 2;
        tree[2 * v].set = tree[v].set;
        applySet(2*v, lx, m);
        tree[2 * v + 1].set = tree[v].set;
        applySet(2*v + 1, m, rx);
        tree[v].set = null;
    }

    private static void set(int l, int r, int value, int v, int lx, int rx) {
        propagate(v, lx, rx);
        if (r <= lx || l >= rx) {
            return;
        }
        if (l <= lx &&  rx <= r) {
            tree[v].set = value;
            applySet(v, lx, rx);
            return;
        }
        int m = (lx + rx) / 2;
        set(l, r, value, 2 * v, lx, m);
        set(l, r, value, 2 * v + 1, m, rx);
        tree[v] = unitNodes(tree[2*v], tree[2*v + 1]);
    }

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        n = nextDegree(1_000_000);
        tree = new Node[n * 2];

        for (int i = 0; i < n * 2; i++) {
            tree[i] = new Node();
        }


        int m = Integer.parseInt(br.readLine());
        for (int i = 0; i < m; i++){
            String[] elements = br.readLine().split(" ");
            int x = Integer.parseInt(elements[1]), l = Integer.parseInt(elements[2]);
            if (elements[0].equals("B")) {
                set(500_000 + x, 500_000 + x + l, 1, 1, 0, n);
                System.out.println(tree[1].amount + " " + tree[1].length);
            } else {
                set(500_000 + x, 500_000 + x + l, 0, 1, 0, n);
                System.out.println(tree[1].amount + " " + tree[1].length);
            }
        }
    }

    private static class Node {
        int amount = 0;
        int length = 0;
        int leftBorder = Integer.MAX_VALUE;
        int rightBorder = Integer.MIN_VALUE;
        Integer set = null;
    }
}
