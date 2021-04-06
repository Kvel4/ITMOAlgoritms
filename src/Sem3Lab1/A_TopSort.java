package Sem3Lab1;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class A_TopSort {
    private static Map<Integer, Set<Integer>> graph = new HashMap<>();
    private static List<Integer> topSort = new ArrayList<>();
    private static int cnt = 1;
    private static int[] color;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String[] splited = br.readLine().split(" ");
        int n = Integer.parseInt(splited[0]), m = Integer.parseInt(splited[1]);
        color = new int[n+1];

        for (int i = 1; i <= n; i++) {
            graph.put(i, new HashSet<>());
        }

        for (int i = 0; i < m; i++) {
            splited = br.readLine().split(" ");
            int u = Integer.parseInt(splited[0]), v = Integer.parseInt(splited[1]);
            graph.get(u).add(v);
        }

        for (int i = 1; i <= n; i++) {
            if (color[i] == 0) {
                dfs(i);
            }
        }
        for (int i = n - 1; i >= 0; i--) {
            System.out.print(topSort.get(i) + " ");
        }
    }

    private static void dfs(int u) {
        color[u] = 1;
        for (int v : graph.get(u)) {
            if (color[v] == 0) {
                dfs(v);
            }
            if (color[v] == 1) {
                System.out.println(-1);
                System.exit(0);
            }
        }
        color[u] = 2;
        topSort.add(u);
    }
}
