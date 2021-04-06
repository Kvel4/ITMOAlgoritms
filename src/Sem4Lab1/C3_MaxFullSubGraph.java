//package Sem4Lab1;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class C3_MaxFullSubGraph {
    private static final BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
    private static int m, n, k;
    private static int[] mt;
    private static boolean[] visitedL;
    private static boolean[] visitedR;
    private static List<List<Integer>> G;

    public static void main(String[] args) throws IOException {
        k = Integer.parseInt(bufferedReader.readLine());

        for (int t = 0; t < k; t++) {
            initializeMatching();
            buildGraph();

            for (int i = 0; i < m; i++) {
                matchingDfs(i);
                Arrays.fill(visitedL, false);
            }

            Set<Integer> matchingL = new HashSet<>();
            for (int i = 0; i < n; i++) {
                if (mt[i] != -1) matchingL.add(mt[i]);
            }

            for (int i = 0; i < m; i++) {
                if (!matchingL.contains(i)) partitionDfs(i);
            }

            write();
        }
    }

    private static void initializeMatching() throws IOException {
        String[] splited = bufferedReader.readLine().split(" ");
        G = new ArrayList<>();
        m = Integer.parseInt(splited[0]);
        n = Integer.parseInt(splited[1]);
        mt = new int[n];
        visitedL = new boolean[m];
        visitedR = new boolean[n];
        Arrays.fill(mt, -1);
        Arrays.fill(visitedL, false);
        Arrays.fill(visitedR, false);
    }

    private static void buildGraph() throws IOException {
        String[] splited;
        for (int i = 0; i < m; i++) {
            G.add(new ArrayList<>());
        }

        for (int i = 0; i < m; i++) {
            HashSet<Integer> connections = new HashSet<>();
            splited = bufferedReader.readLine().split(" ");
            for (String element : splited) {
                int el = Integer.parseInt(element);
                if (el != 0) connections.add(el - 1);
            }
            for (int j = 0; j < n; j++) {
                if (!connections.contains(j)) G.get(i).add(j);
            }
        }
    }

    private static boolean matchingDfs(int v) {
        if (visitedL[v]) return false;
        visitedL[v] = true;

        for (Integer to : G.get(v)) {
            if (mt[to] == -1 || matchingDfs(mt[to])) {
                mt[to] = v;
                return true;
            }
        }
        return false;
    }

    private static void partitionDfs(int v) {
        if (visitedL[v]) return;
        visitedL[v] = true;

        for (Integer to : G.get(v)) {
            visitedR[to] = true;
            partitionDfs(mt[to]);
        }
    }

    private static void write() {
        int cntL = 0, cntR = 0;
        for (boolean visited : visitedL) {
            if (visited) cntL++;
        }
        for (boolean visited : visitedR) {
            if (!visited) cntR++;
        }
        System.out.println(cntL + cntR);
        System.out.println(cntL + " " + cntR);
        for (int i = 0; i < m; i++) {
            if (visitedL[i]) System.out.print((i + 1) + " ");
        }
        System.out.println();
        for (int i = 0; i < n; i++) {
            if (!visitedR[i]) System.out.print((i + 1) + " ");
        }
        System.out.println();
        System.out.println();
    }
}
