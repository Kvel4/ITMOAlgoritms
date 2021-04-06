package Sem3Lab1;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class C_Points {
    private static Map<Integer, Set<Integer>> graph = new HashMap<>();
    private static Set<Integer> points = new TreeSet<>();
    private static int time = 0;
    private static int[] in;
    private static int[] up;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String[] splited = br.readLine().split(" ");
        int n = Integer.parseInt(splited[0]), m = Integer.parseInt(splited[1]);
        in = new int[n+1];
        up = new int[n+1];

        for (int i = 1; i <= n; i++) {
            graph.put(i, new HashSet<>());
        }

        for (int i = 0; i < m; i++) {
            splited = br.readLine().split(" ");
            int u = Integer.parseInt(splited[0]), v = Integer.parseInt(splited[1]);
            graph.get(u).add(v);
            graph.get(v).add(u);
        }

        for (int i = 1; i <= n; i++) {
            if (in[i] == 0) {
                dfs(i, i);
            }
        }

        System.out.println(points.size());
        for (int el : points) {
            System.out.print(el + " ");
        }
    }

    private static void dfs(int v, int par) {
        time++;
        in[v] = time;
        up[v] = time;
        int children = 0;

        for (Integer u : graph.get(v)) {
            if (u == par) {
                continue;
            }
            if (in[u] == 0) {
                dfs(u, v);
                children++;
                up[v] = Math.min(up[v], up[u]);
                if (up[u] >= in[v] && par != v) {
                    points.add(v);
                }
            } else {
                up[v] = Math.min(up[v], in[u]);
            }
        }
        if (par == v && children > 1) {
            points.add(v);
        }
    }
}

