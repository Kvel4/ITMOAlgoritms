package Sem1Lab3;

import java.util.Scanner;

public class D4 {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt();

        long[][] d = new long[n+1][10];
        d[1] = new long[]{0, 1, 1, 1, 1, 1, 1, 1, 0, 1};
        for (int i = 2; i < n+1; i++){
            d[i][0] = (d[i-1][6]+d[i-1][4]) % (long) Math.pow(10,9);
            d[i][1] = (d[i-1][6]+d[i-1][8]) % (long) Math.pow(10,9);
            d[i][2] = (d[i-1][7]+d[i-1][9]) % (long) Math.pow(10,9);
            d[i][3] = (d[i-1][4]+d[i-1][8]) % (long) Math.pow(10,9);
            d[i][4] = (d[i-1][3]+d[i-1][9]+d[i-1][0]) % (long) Math.pow(10,9);
            d[i][5] = 0;
            d[i][6] = (d[i-1][1]+d[i-1][7]+d[i-1][0]) % (long) Math.pow(10,9);
            d[i][7] = (d[i-1][6]+d[i-1][2]) % (long) Math.pow(10,9);
            d[i][8] = (d[i-1][1]+d[i-1][3]) % (long) Math.pow(10,9);
            d[i][9] = (d[i-1][2]+d[i-1][4]) % (long) Math.pow(10,9);
        }
        long ans = 0;
        for (int i = 0; i < 10; i ++){
            ans += d[n][i];
            ans %= (long) Math.pow(10,9);
        }
        System.out.println(ans);
    }
}
