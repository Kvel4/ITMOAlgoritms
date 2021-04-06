package Sem3Lab1;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class B_Bridges {
    private static Map<Integer, Set<Edge>> graph = new HashMap<>();
    private static List<Integer> bridges = new ArrayList<>();
    private static int time = 0;
    private static int[] timeIn;
    private static int[] reverse;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String[] splited = br.readLine().split(" ");
        int n = Integer.parseInt(splited[0]), m = Integer.parseInt(splited[1]);
        timeIn = new int[n+1];
        reverse = new int[n+1];

        for (int i = 1; i <= n; i++) {
            graph.put(i, new HashSet<>());
        }

        for (int i = 0; i < m; i++) {
            splited = br.readLine().split(" ");
            int u = Integer.parseInt(splited[0]), v = Integer.parseInt(splited[1]);
            graph.get(u).add(new Edge(v, i + 1));
            graph.get(v).add(new Edge(u, i + 1));
        }

        for (int i = 1; i <= n; i++) {
            if (timeIn[i] == 0) {
                dfs(i, i);
            }
        }

        Collections.sort(bridges);
        System.out.println(bridges.size());
        for (int el : bridges) {
            System.out.print(el + " ");
        }
    }

    private static void dfs(int v, int par) {
        time++;
        timeIn[v] = time;
        reverse[v] = time;

        for (Edge u : graph.get(v)) {
            if (u.to == par) {
                continue;
            }
            if (timeIn[u.to] == 0) {
                dfs(u.to, v);
                reverse[v] = Math.min(reverse[v], reverse[u.to]);
                if (reverse[u.to] > timeIn[v]) {
                    bridges.add(u.index);
                }
            } else {
                reverse[v] = Math.min(reverse[v], timeIn[u.to]);
            }
        }
    }

    private static class Edge {
        int to, index;

        Edge(int to, int index) {
            this.to = to;
            this.index = index;
        }

    }
}
