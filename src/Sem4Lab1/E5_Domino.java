package Sem4Lab1;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class E5_Domino {
    private static int n, m, a, b;
    private static final List<List<Integer>> G = new ArrayList<>();
    private static int[] mt;
    private static boolean[] visited;

    public static void main(String[] args) throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
        String[] splited = bufferedReader.readLine().split(" ");
        n = Integer.parseInt(splited[0]);
        m = Integer.parseInt(splited[1]);
        a = Integer.parseInt(splited[2]);
        b = Integer.parseInt(splited[3]);

        int l = n * m / 2, r = n * m / 2;
        if (n % 2 == 1 && m % 2 == 1) l++;

        visited = new boolean[l];
        mt = new int[r];
        Arrays.fill(mt, -1);

        for (int i = 0; i < l; i++) {
            G.add(new ArrayList<>());
        }

        char[][] matrix = new char[n][m];
        int free = 0;
        for (int i = 0; i < n; i++) {
            String s = bufferedReader.readLine();
            for (int j = 0; j < s.length(); j++) {
                if (s.charAt(j) == '*') free++;
                matrix[i][j] = s.charAt(j);
            }
        }

        if (2 * b < a) {
            System.out.println(b * free);
            System.exit(0);
        }

        int ind = 0;
        int up = m % 2 == 0 ? - m / 2 : - m / 2 - 1, down = m / 2;
        int left = -1, right = 0;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                if (m % 2 == 0) {
                    if (i % 2 == 0) {
                        left = -1;
                        right = 0;
                    } else {
                        left = 0;
                        right = 1;
                    }
                }
                int[][] dirs = {{1, 0, down}, {0, 1, right}, {-1, 0, up}, {0, -1, left}};

                if ((i + j) % 2 == 0) {
                    if (matrix[i][j] == '*') {
                        for (int[] dir : dirs) {
                            int newI = i + dir[0], newJ = j + dir[1], shift = dir[2];
                            if (newI >= 0 && newI < n && newJ >= 0 && newJ < m && matrix[newI][newJ] == '*') {
                                G.get(ind).add(ind + shift);
                            }
                        }
                    }
                    ind++;
                }
            }
        }

        for (int i = 0; i < l; i++) {
            Arrays.fill(visited, false);
            dfs(i);
        }

        int price = 0;
        for (int i = 0; i < r; i++) {
            if (mt[i] != -1) {
                free -= 2;
                price += a;
            }
        }
        price += b * free;
        System.out.println(price);
    }

    private static boolean dfs(int v) {
        if (visited[v]) return false;
        visited[v] = true;

        for (Integer to : G.get(v)) {
            if (mt[to] == -1 || dfs(mt[to])) {
                mt[to] = v;
                return true;
            }
        }
        return false;
    }
}
