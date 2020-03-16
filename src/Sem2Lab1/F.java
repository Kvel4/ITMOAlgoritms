package Sem2Lab1;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class F {
    private static int log2(int n) {
        int cnt = 0;
        while (n >= 2) {
            n /= 2;
            cnt += 1;
        }
        return cnt;
    }

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String[] elements = br.readLine().split(" ");
        int n = Integer.parseInt(elements[0]), m = Integer.parseInt(elements[1]);
        int height = log2(n);
        int[][] table = new int[height + 1][n + 1];

        table[0][1] = Integer.parseInt(elements[2]);
        for(int i = 2; i < n + 1; i++){
            table[0][i] = (23 * table[0][i - 1] + 21563) % 16714589;
        }

        for (int k = 1; k <= height; k++) {
            for (int i = 0; i < (int) (n + 2 - Math.pow(2, k)); i++) {
                table[k][i] = Math.min(table[k-1][i], table[k-1][(int) (i + Math.pow(2, k-1))]);
            }
        }

        elements = br.readLine().split(" ");
        int ans, u = Integer.parseInt(elements[0]), v = Integer.parseInt(elements[1]);
        for (int i = 1; i < m; i++) {
            int l = Math.min(u, v), r = Math.max(u, v);
            int k = log2(r - l + 1);
            ans = Math.min(table[k][l], table[k][(int) (r - Math.pow(2, k) + 1)]);
            u = ((17*u + 751 + ans + 2*i) % n) + 1;
            v = ((13*v + 593 + ans + 5*i) % n) + 1;
        }

        int l = Math.min(u, v), r = Math.max(u, v);
        int k = log2(r - l + 1);
        ans = Math.min(table[k][l], table[k][(int) (r - Math.pow(2, k) + 1)]);
        System.out.println(u + " " + v + " " + ans);
    }
}
