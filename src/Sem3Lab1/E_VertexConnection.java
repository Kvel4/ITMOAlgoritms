package Sem3Lab1;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class E_VertexConnection {
    private static Map<Integer, List<Edge>> graph = new HashMap<>();
    private static Map<Edge, Integer> edgesComponent = new HashMap<>();
    private static Stack<Edge> stack = new Stack<>();
    private static int time = 0;
    private static int curComponent = 1;
    private static int[] in;
    private static int[] up;
    private static List<Edge> edges = new ArrayList<>();
    private static Set<Edge> set = new HashSet<>();

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String[] splited = br.readLine().split(" ");
        int n = Integer.parseInt(splited[0]), m = Integer.parseInt(splited[1]);
        in = new int[n+1];
        up = new int[n+1];

        for (int i = 1; i <= n; i++) {
            graph.put(i, new ArrayList<>());
        }

        for (int i = 0; i < m; i++) {
            splited = br.readLine().split(" ");
            int u = Integer.parseInt(splited[0]), v = Integer.parseInt(splited[1]);
            edges.add(new Edge(u, v));
            graph.get(u).add(edges.get(edges.size() - 1));
            graph.get(v).add(edges.get(edges.size() - 1));
        }

        for (int i = 1; i <= n; i++) {
            if (in[i] == 0) {
                boolean flag = false;
                dfs(i, new Edge(0, 0));
                while (!stack.empty()) {
                    flag = true;
                    edgesComponent.put(stack.pop(), curComponent);
                }
                if (flag) curComponent++;
            }
        }

        System.out.println(curComponent-1);
        for (Edge edge : edges){
            System.out.print(edgesComponent.get(edge) + " ");
        }
    }

    private static void dfs(int v, Edge parentEdge) {
        time++;
        in[v] = time;
        up[v] = time;

        for (Edge edge : graph.get(v)) {
            if (edge == parentEdge) {
                continue;
            }
            if (!set.contains(edge)) {
                stack.add(edge);
                set.add(edge);
            }
            if (in[edge.getTo(v)] == 0) {
                dfs(edge.getTo(v), edge);
                up[v] = Math.min(up[v], up[edge.getTo(v)]);
                if (up[edge.getTo(v)] >= in[v]) {
                    while (!stack.peek().equals(edge)) {
                        edgesComponent.put(stack.pop(), curComponent);
                    }
                    edgesComponent.put(stack.pop(), curComponent);
                    curComponent++;
                }
            } else {
                up[v] = Math.min(up[v], in[edge.getTo(v)]);
            }
        }
    }

    private static class Edge {
        int from, to;

        public Edge(int from, int to) {
            this.from = from;
            this.to = to;
        }

        public int getFrom(int v) {
            return v == from ? from : to;
        }

        public int getTo(int v) {
            return v == to ? from : to;
        }

        @Override
        public String toString() {
            return from + " " + to;
        }
    }
}

