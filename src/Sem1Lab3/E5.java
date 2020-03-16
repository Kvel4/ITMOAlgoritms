package Sem1Lab3;

import java.util.Scanner;

public class E5 {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        String s1 = sc.nextLine();
        String s2 = sc.nextLine();

        if (s1.length() == 0 && s2.length() == 0) {
            System.out.println(0);
            System.exit(0);
        } else if (s1.length() == 0 || s2.length() == 0) {
            System.out.println(Math.max(s1.length(), s2.length()));
            System.exit(0);
        }

        int[][] matrix = new int[s1.length() + 1][s2.length() + 1];
        for (int i = 0; i < s1.length(); i++) {
            matrix[i][0] = i;
        }
        for (int j = 0; j < s2.length(); j++) {
            matrix[0][j] = j;
        }

        for (int i = 1; i < s1.length() + 1; i++) {
            for (int j = 1; j < s2.length() + 1; j++) {
                if (s1.charAt(i - 1) == s2.charAt(j - 1)) {
                    matrix[i][j] = matrix[i - 1][j - 1];
                } else {
                    matrix[i][j] = Math.min(Math.min(matrix[i - 1][j - 1], matrix[i - 1][j]), matrix[i][j - 1]) + 1;
                }
            }
        }
        System.out.println(matrix[s1.length()][s2.length()]);
    }
}
