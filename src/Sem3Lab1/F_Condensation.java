package Sem3Lab1;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class F_Condensation {
    private static Map<Integer, List<Integer>> graph = new HashMap<>();
    private static Map<Integer, List<Integer>> reversedGraph = new HashMap<>();
    private static Set<Integer> currentTrackedComponents = new HashSet<>();
    private static List<Integer> order = new ArrayList<>();
    private static int[] components;
    private static int[] color;
    private static int edgesCnt = 0;
    private static int curComponent = 0;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String[] splited = br.readLine().split(" ");
        int n = Integer.parseInt(splited[0]), m = Integer.parseInt(splited[1]);
        color = new int[n+1];
        components = new int[n+1];

        for (int i = 1; i <= n; i++) {
            graph.put(i, new ArrayList<>());
            reversedGraph.put(i, new ArrayList<>());
        }

        for (int i = 0; i < m; i++) {
            splited = br.readLine().split(" ");
            int u = Integer.parseInt(splited[0]), v = Integer.parseInt(splited[1]);
            graph.get(u).add(v);
            reversedGraph.get(v).add(u);
        }

        for (int i = 1; i <= n; i++) {
            if (color[i] == 0) {
                dfs1(i);
            }
        }
        Arrays.fill(color, 0);
        Collections.reverse(order);
        for (int v : order){
            if (color[v] == 0) {
                dfs2(v);
                currentTrackedComponents.clear();
                curComponent++;
            }
        }

        System.out.println(edgesCnt);
    }

    private static void dfs1(int v) {
        color[v] = 1;

        for (int u : graph.get(v)){
            if (color[u] == 0) {
                dfs1(u);
            }
        }
        color[v] = 2;
        order.add(v);
    }

    private static void dfs2(int v) {
        color[v] = 1;
        components[v] = curComponent;

        for (int u : reversedGraph.get(v)){
            if (color[u] == 0) {
                dfs2(u);
            } else if (color[u] == 2 && components[u] != curComponent && !currentTrackedComponents.contains(components[u])) {
                currentTrackedComponents.add(components[u]);
                edgesCnt++;
            }
        }
        color[v] = 2;
    }
}

