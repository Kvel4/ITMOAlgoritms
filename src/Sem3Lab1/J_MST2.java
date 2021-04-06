package Sem3Lab1;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class J_MST2 {
    private static Map<Integer, List<Vertex>> graph = new HashMap<>();
    private static TreeSet<Vertex> priorityQueue = new TreeSet<>();
    private static Set<Integer> A = new HashSet<>();
    private static Vertex[] d;
    private static long mstWeight = 0;
    private static int n, m;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String[] splited = br.readLine().split(" ");
        n = Integer.parseInt(splited[0]);
        m = Integer.parseInt(splited[1]);
        d = new Vertex[n + 1];
        Arrays.fill(d, new Vertex(0, Integer.MAX_VALUE));

        for (int i = 1; i <= n; i++) {
            graph.put(i, new ArrayList<>());
        }

        for (int i = 0; i < m; i++) {
            splited = br.readLine().split(" ");
            int u = Integer.parseInt(splited[0]),
                    v = Integer.parseInt(splited[1]),
                    w = Integer.parseInt(splited[2]);
            graph.get(u).add(new Vertex(v, w));
            graph.get(v).add(new Vertex(u, w));
        }
        buildMST();
        System.out.print(mstWeight);
    }

    private static void buildMST() {
        d[1] = new Vertex(0, 0);
        A.add(1);
        priorityQueue.add(new Vertex(1, 0));


        for (int i = 0; i < n; i++) {
            Vertex v = priorityQueue.pollFirst();
            A.add(v.v);
            mstWeight += v.weight;
            for (Vertex u : graph.get(v.v)) {
                if (!A.contains(u.v) && u.weight < d[u.v].weight) {
                    priorityQueue.remove(d[u.v]);
                    d[u.v] = u;
                    priorityQueue.add(u);
                }
            }
        }
    }

    private static class Vertex implements Comparable<Vertex> {
        int v, weight;

        Vertex(int v, int weight) {
            this.v = v;
            this.weight = weight;
        }

        @Override
        public int compareTo(Vertex edge) {
            if (Integer.compare(weight, edge.weight) == 0) {
                return Integer.compare(v, edge.v);
            }
            return Integer.compare(weight, edge.weight);
        }
    }
}
