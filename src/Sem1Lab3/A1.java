package Sem1Lab3;

import java.io.*;
import java.util.Scanner;

public class A1 {
    static int cnt = 0;
    static int[] parents;

    public static void main(String[] args) throws IOException {
        Scanner sc = new Scanner(new File("input.txt"));
        int n = sc.nextInt(), k = sc.nextInt();
        int[] coins = new int[n+1];
        parents = new int[n+1];
        int[] array = new int[n+1];

        for (int i = 2; i < n; i++) {
            coins[i] = sc.nextInt();
        }

        for (int i = 2; i < n + 1; i++) {
            array[i] = -10001;
        }
        sc.close();

        for (int i = 2; i <= n; i++) {
            for (int j = i-1; j >= Math.max(i - k, 1); j--) {
                if (array[j] + coins[i] >= array[i]) {
                    parents[i] = j;
                    array[i] = array[j] + coins[i];
                }
            }
        }

        PrintWriter printWriter = new PrintWriter("output.txt");
        printWriter.println(array[n]);
        getWay(n, printWriter);
        printWriter.close();
    }

    static void getWay(int i, PrintWriter pw){
        if (i == 1) {
            pw.println(cnt);
            pw.print(i + " ");
            return;
        }
        cnt++;
        getWay(parents[i], pw);
        pw.print(i + " ");
    }
}
