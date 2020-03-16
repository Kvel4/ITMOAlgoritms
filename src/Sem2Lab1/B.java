package Sem2Lab1;

import java.util.Scanner;

public class B {
    private static int n;
    private static long[] tree;

    private static int next2degree(int numb) {
        int base = 1;
        while (base < numb) {
            base *= 2;
        }
        return base;
    }

    private static void build() {
        for (int i = n-1; i > 0; --i) {
            tree[i] = tree[2 * i] + tree[2 * i + 1];
        }
    }

    private static void set(int i, long x) {
        i += n;
        tree[i] = x;
        while (i > 1) {
            i /= 2;
            tree[i] = tree[2 * i] + tree[2 * i + 1];
        }
    }

    private static long sum(int l, int r, int x, int lx, int rx) {
        if (l >= rx || r <= lx) {
            return 0;
        }
        if (l <= lx && rx <= r) {
            return tree[x];
        }
        int m = (lx + rx) / 2;
        return sum(l, r, 2*x, lx, m) + sum(l, r, 2*x + 1, m, rx);
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int tmp = sc.nextInt();
        n = next2degree(tmp);
        tree = new long[n * 2];

        for (int i = 0; i < tmp; ++i) {
            tree[n + i] = sc.nextInt();
        }
        build();

        while (sc.hasNext()) {
            if (sc.next().equals("set")) {
                set(sc.nextInt() - 1, sc.nextInt());
            } else {
                System.out.println(sum(sc.nextInt() - 1, sc.nextInt(), 1, 0, n));
            }
        }
    }
}
