package Sem3Lab1;

import java.io.*;
import java.util.*;

public class I_FullGraph_MST {
    private static List<Vertex> vertexes = new ArrayList<>();
    private static double mstWeight = 0.;
    private static boolean[] used;
    private static double[] d;
    private static int n;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        n = Integer.parseInt(br.readLine());

        used = new boolean[n + 1];
        d = new double[n + 1];

        Arrays.fill(d, 1000000.);
        vertexes.add(new Vertex(0, 0));

        for (int i = 1; i <= n; i++) {
            String[] splited = br.readLine().split(" ");
            int x = Integer.parseInt(splited[0]), y = Integer.parseInt(splited[1]);
            vertexes.add(new Vertex(x, y));
        }

        buildMST();
        System.out.println(mstWeight);
    }

    private static void buildMST() {
        d[1] = 0;

        for (int i = 0; i < n; i++) {
            double minWeight = 1000000.;
            int v = -1;
            for (int j = 1; j <= n; j++) {
                if (!used[j] && d[j] < minWeight) {
                    minWeight = d[j];
                    v = j;
                }
            }

            used[v] = true;
            mstWeight += minWeight;
            for (int j = 1; j <= n; j++) {
                if (!used[j] && getDistance(vertexes.get(v), vertexes.get(j)) < d[j]) {
                    d[j] = getDistance(vertexes.get(v), vertexes.get(j));
                }
            }
        }
    }

    public static double getDistance(Vertex a, Vertex b) {
        return Math.sqrt((a.x - b.x) * ((a.x - b.x) + Math.pow(a.y - b.y, 2)));
    }

    private static class Vertex {
        int x, y;

        Vertex(int x, int y) {
            this.x = x;
            this.y = y;
        }
    }
}


//public static double getDistance(int x1, int y1, int x2, int y2) {
//        return Math.sqrt((x1 - x2)*(x1 - x2) + (y1 - y2)*(y1 - y2));
//        }