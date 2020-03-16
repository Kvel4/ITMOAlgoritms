package Sem2Lab1;

import java.util.Scanner;

public class L {
    private static int[][][] f;
    private static int n;

    private static int prefSum(int x, int y, int z) {
        int ans = 0;
        for (int i = x; i >= 0; i--) {
            for (int j = y; j >= 0; j--) {
                for (int k = z; k >= 0; k--) {
                    ans += f[i][j][k];
                    k = (k + 1) & k;
                }
                j = (j + 1) & j;
            }
            i = (i + 1) & i;
        }
        return ans;
    }

    private static int sum(int x1, int y1, int z1, int x2, int y2, int z2) {
        x1 -= 1; y1 -= 1; z1 -= 1;
        int t = prefSum(x2, y2, z2) - prefSum(x2, y1, z2) - prefSum(x1, y2, z2) - prefSum(x2, y2, z1) +
                prefSum(x1, y1, z2) + prefSum(x2, y1, z1) + prefSum(x1, y2, z1) - prefSum(x1, y1, z1);
        return t;
    }

    private static void add(int x, int y, int z, int val) {
        for (int i = x; i < n;) {
            for (int j = y; j < n; ) {
                for (int k = z; k < n; ) {
                    f[i][j][k] += val;
                    k = (k + 1) | k;
                }
                j = (j + 1) | j;
            }
            i = (i + 1) | i;
        }
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        n = scanner.nextInt();
        f = new int[n][n][n];

        while (scanner.hasNextInt()) {
            int m = scanner.nextInt();
            switch (m) {
                case 1:
                    add(scanner.nextInt(), scanner.nextInt(), scanner.nextInt(), scanner.nextInt());
                    break;
                case 2:
                    System.out.println(sum(
                        scanner.nextInt(), scanner.nextInt(), scanner.nextInt(),
                        scanner.nextInt(), scanner.nextInt(), scanner.nextInt()));
                break;
                case 3:
                    break;
            }
        }
    }
}
