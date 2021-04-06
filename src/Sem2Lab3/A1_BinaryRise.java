//package Sem2Lab3;

import java.io.*;

public class A1_BinaryRise {
    private static int[][] rises;
    private static int[][] mins;
    private static int[] parents;
    private static int[] weights;
    private static int n, logN;
    private static int[] d;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new FileReader("minonpath.in"));
        PrintWriter pw = new PrintWriter("minonpath.out");
        n = Integer.parseInt(br.readLine()); n++;
        logN = (int) Math.ceil(Math.log(n) / Math.log(2));
        rises = new int[n][logN];
        mins = new int[n][logN];
        parents = new int[n];
        weights = new int[n];
        d = new int[n];

        weights[1] = Integer.MAX_VALUE;
        parents[1] = 1;
        d[1] = 1;

        for (int i = 2; i < n; i ++) {
            String[] s = br.readLine().split(" ");
            int x = Integer.parseInt(s[0]), y = Integer.parseInt(s[1]);
            parents[i] = x;
            weights[i] = y;
            d[i] = d[x] + 1;
        }

        buildRises();

        int m = Integer.parseInt(br.readLine());
        for (int i = 0; i < m; i++) {
            String[] s = br.readLine().split(" ");
            int v = Integer.parseInt(s[0]), u = Integer.parseInt(s[1]);
//            int[] lca = findLCA(v, u);
//            int min = Integer.MAX_VALUE;
//            while (u != lca[0]) {
//                min = Math.min(min, weights[u]);
//                u = parents[u];
//            }
//            while (v != lca[0]) {
//                min = Math.min(min, weights[v]);
//                v = parents[v];
//            }
//            pw.println(min + " " + lca[1]);
            pw.println(findLCA(v, u));
        }
        br.close();
        pw.close();
    }

    private static void buildRises() {
        for (int i = 1; i < n; i++) {
            rises[i][0] = parents[i];
            mins[i][0] = weights[i];
        }

        for (int j = 1; j < logN; j++) {
            for (int i = 1; i < n; i++) {
                rises[i][j] = rises[rises[i][j-1]][j-1];
                mins[i][j] = Math.min(mins[i][j-1], mins[rises[i][j-1]][j-1]);
            }
        }
    }

    private static int findLCA(int v, int u) {
        if (d[v] > d[u]) {
            int tmp = v;
            v = u;
            u = tmp;
        }
        int min = Integer.MAX_VALUE;
        for (int i = logN - 1; i >= 0; i--) {
            if (d[rises[u][i]] - d[v] >= 0) {
                min = Math.min(min, mins[u][i]);
                u = rises[u][i];
            }
        }
        if (u == v) {
            return min;
        }
        for (int i = logN - 1; i >= 0; i--) {
            if (rises[v][i] != rises[u][i]) {
                min = Math.min(min, Math.min(mins[u][i], mins[v][i]));
                u = rises[u][i];
                v = rises[v][i];
            }
        }
        return Math.min(min, Math.min(weights[u], weights[v]));
//        return Math.min(min, Math.min(weights[u], weights[v]));
    }
}
