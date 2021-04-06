//package Sem4Lab1;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;
import java.util.stream.Collectors;

public class F6_MaxClique2 {
    private static final BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
    private static int cnt = 0;
    private static int m, n, k;
    private static int[] mt;
    private static boolean[] visitedL;
    private static boolean[] visitedR;
    private static List<List<Integer>> G;

    public static void main(String[] args) throws IOException {
        buildGraph();

        for (int i = 0; i < m; i++) {
            matchingDfs(i);
            Arrays.fill(visitedL, false);
        }

//        Set<Integer> matchingL = new HashSet<>();
//        for (int i = 0; i < n; i++) {
//            if (mt[i] != -1) matchingL.add(mt[i]);
//        }

//        for (int i = 0; i < m; i++) {
//            if (!matchingL.contains(i)) partitionDfs(i);
//        }

        write();
    }

    private static void buildGraph() throws IOException {
        k = Integer.parseInt(bufferedReader.readLine());
        G = new ArrayList<>();
        List<List<Integer>> segmentsL = new ArrayList<>();
        List<List<Integer>> segmentsR = new ArrayList<>();

        for (int i = 0; i < k; i++) {
            List<Integer> coords = Arrays.stream(bufferedReader.readLine().split(" ")).map(Integer::parseInt).collect(Collectors.toList());
            if (coords.get(0).equals(coords.get(2))) {
                G.add(new ArrayList<>());
                segmentsL.add(coords);
                m++;
            } else {
                segmentsR.add(coords);
                n++;
            }
        }

        mt = new int[n];
        visitedL = new boolean[m];
        visitedR = new boolean[n];
        Arrays.fill(mt, -1);
        Arrays.fill(visitedL, false);
        Arrays.fill(visitedR, false);

        for (int i = 0; i < m; i++) {
            List<Integer> coordsL = segmentsL.get(i);
            int y1 = coordsL.get(1), y2 = coordsL.get(3);
            int x = coordsL.get(0);

            for (int j = 0; j < n; j++) {
                List<Integer> coordsR = segmentsR.get(j);
                int x1 = coordsR.get(0), x2 = coordsR.get(2);
                int y = coordsR.get(1);
                if (x1 <= x && x <= x2 || x2 <= x && x <= x1) {
                    if (y1 <= y && y <= y2 || y2 <= y && y <= y1) {
                        G.get(i).add(j);
                    }
                }
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

//    private static void partitionDfs(int v) {
//        if (visitedL[v]) return;
//        visitedL[v] = true;
//
//        for (Integer to : G.get(v)) {
//            visitedR[to] = true;
//            partitionDfs(mt[to]);
//        }
//    }

    private static void write() {
        for (int i = 0; i < n; i++) {
            if (mt[i] != -1) cnt++;
        }
        System.out.println(k - cnt);
    }
}
