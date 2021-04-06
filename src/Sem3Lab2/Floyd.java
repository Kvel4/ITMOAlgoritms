//package Sem3Lab2;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Floyd {
    private static int[][] matrix;
    private static int n;

    public static void main(String args[]) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        n = Integer.parseInt(br.readLine());
        matrix = new int[n][n];

        for (int i = 0; i < n; i++) {
            String[] splited = br.readLine().split(" ");
            for (int j = 0; j < n; j++) {
                matrix[i][j] = Integer.parseInt(splited[j]);
            }
        }

        for (int k = 0; k < n; k++) {
            for (int i = 0; i < n; i++) {
                for (int j = 0; j < n; j++) {
                    matrix[i][j] = Math.min(matrix[i][j], matrix[i][k] + matrix[k][j]);
                }
            }
        }

        for (int[] row : matrix) {
            for (int el : row) {
                System.out.print(el + " ");
            }
            System.out.println();
        }
    }
}
