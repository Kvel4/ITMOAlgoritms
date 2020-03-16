package Sem2Lab1;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Scanner;

public class E {
    private static int n, r;
    private static int[][] tree;

    private static int next2degree(int numb) {
        int base = 1;
        while (base < numb) {
            base *= 2;
        }
        return base;
    }

    private static int[] matrixMul(int[] m1, int[]m2) {
        int[] res = new int[4];
        res[0] = (m1[0] * m2[0] + m1[1] * m2[2]) % r;
        res[1] = (m1[0] * m2[1] + m1[1] * m2[3]) % r;
        res[2] = (m1[2] * m2[0] + m1[3] * m2[2]) % r;
        res[3] = (m1[2] * m2[1] + m1[3] * m2[3]) % r;
        return res;
    }

    private static void build() {
        for (int i = n-1; i > 0; --i) {
            tree[i] = matrixMul(tree[2 * i], tree[2 * i + 1]);
        }
    }

    private static int[] sum(int l, int r, int x, int lx, int rx) {
        if (l >= rx || r <= lx) {
            return new int[]{1, 0, 0, 1};
        }
        if (l <= lx && rx <= r) {
            return tree[x];
        }
        int m = (lx + rx) / 2;
        return matrixMul(sum(l, r, 2*x, lx, m), sum(l, r, 2*x + 1, m, rx));
    }

    public static void main(String[] args) throws FileNotFoundException {
        Scanner sc = new Scanner(new File("crypto.in"));
        r = sc.nextInt();
        int tmp = sc.nextInt(), m = sc.nextInt();
        n = next2degree(tmp);
        tree = new int[n * 2][4];

        for (int i = 0; i < tmp; ++i) {
            for (int j = 0; j < 4; ++j) {
                tree[n + i][j] = sc.nextInt();
            }
        }
        build();

        PrintWriter printWriter = new PrintWriter("crypto.out");
        for (int i = 0; i < m; ++i) {
            int[] res = sum(sc.nextInt() - 1, sc.nextInt(), 1, 0, n);
            printWriter.println(res[0] + " " + res[1] + "\n" + res[2] + " " + res[3] + "\n");
        }
        sc.close();
        printWriter.close();
    }
}
