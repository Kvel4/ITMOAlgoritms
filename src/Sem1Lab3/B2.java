package Sem1Lab3;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class B2 {
    public static void main(String[] args) throws FileNotFoundException {
        Scanner sc = new Scanner(new File("input.txt"));
        int n = sc.nextInt(), m = sc.nextInt();

        Cell[][] matrix = new Cell[n+1][m+1];
        for (int i = 0; i < n + 1; i++) {
            for (int j = 0; j < m + 1; j++) {
                if (i == 0 || j == 0) {
                    matrix[i][j] = new Cell(0, -10000000);
                } else {
                    matrix[i][j] = new Cell(sc.nextInt());
                }
            }
        }

        for (int i = 1; i < n + 1; i++) {
            for (int j = 1; j < m + 1; j++) {
                if (i == 1 && j == 1) {
                    matrix[i][j].maxPossible = matrix[i][j].points;
                } else {
                    matrix[i][j].maxPossible = Math.max(matrix[i - 1][j].maxPossible, matrix[i][j - 1].maxPossible) + matrix[i][j].points;
                }
            }
        }

        PrintWriter printWriter = new PrintWriter("output.txt");
        printWriter.println(matrix[n][m].maxPossible);

        for (Cell[] el : matrix){
            System.out.println(Arrays.toString(el));
        }
        List<String> ans = new ArrayList<>();
        int i = n, j = m;
        while (i != 1 || j != 1){
            int tmp = matrix[i][j].maxPossible - matrix[i][j].points;
            if (matrix[i-1][j].maxPossible == tmp) {
                i--;
                ans.add("D");
            } else {
                j--;
                ans.add("R");
            }
        }
        for (i = ans.size() - 1; i >= 0; i--) {
            printWriter.print(ans.get(i));
        }
        printWriter.close();

    }

    private static class Cell {
        int points;
        int maxPossible;

        Cell(int points) {
            this.points = points;
            this.maxPossible = -10000000;
        }

        Cell(int points, int maxPossible) {
            this.points = points;
            this.maxPossible = maxPossible;
        }

        @Override
        public String toString() {
            return "[" + points + ", " + maxPossible + "]";
        }
    }
}

