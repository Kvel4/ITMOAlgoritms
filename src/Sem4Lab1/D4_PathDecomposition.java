package Sem4Lab1;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

public class D4_PathDecomposition {
    private static final BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
    private static int cnt = 0;
    private static int n, v;
    private static int[] mt;
    private static boolean[] visitedL;
    private static List<List<Integer>> G;

    public static void main(String[] args) throws IOException {
        buildGraph();

        for (int i = 0; i < n; i++) {
            matchingDfs(i);
            Arrays.fill(visitedL, false);
        }

        write();
    }

    private static void buildGraph() throws IOException {
        List<Integer> args = Arrays.stream(bufferedReader.readLine().split(" ")).map(Integer::parseInt).collect(Collectors.toList());
        G = new ArrayList<>();
        n = args.get(0);
        mt = new int[n];
        visitedL = new boolean[n];
        Arrays.fill(mt, -1);
        Arrays.fill(visitedL, false);
        v = args.get(1);

        List<Flight> flights = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            G.add(new ArrayList<>());
            String[] f = bufferedReader.readLine().split(" ");
            Flight flight = new Flight(f[0], f[1], f[2]);
            flights.add(flight);
        }

        for (int i = 0; i < n; i++) {
            Flight flight = flights.get(i);
            for (int j = 0; j < n; j++) {
                Flight flight1 = flights.get(j);
                if (i != j && flight.isInTime(flight1)) {
                    if (flight.time < flight1.time ) {
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

    private static void write() {
        HashSet<Integer> usedL = new HashSet<>();
        HashSet<Integer> used = new HashSet<>();
        for (int i = 0; i < n; i++) {
            if (mt[i] != -1) {
                usedL.add(mt[i]);
                used.add(mt[i]);
                used.add(i);
            }
        }
        for (int i = 0; i < n; i++) {
            if (mt[i] != -1 && !usedL.contains(i)) cnt++;
        }

        System.out.println(cnt + (n - used.size()));
    }



    private static class Flight {
        public int time, x, y;

        public Flight(String time, String x, String y){
            this.time = getTime(time);
            this.x = Integer.parseInt(x);
            this.y = Integer.parseInt(y);
        }

        private int getTimeDiff(Flight flight) {
            return Math.abs(this.time - flight.time);
        }

        private double getDistanceDiff(Flight flight) {
            return getDistanceDiff(this.x, this.y, flight.x, flight.y);
        }

        private double getDistanceDiff(int x1, int y1, int x2, int y2) {
            return Math.sqrt((x1 - x2)*(x1 - x2) + (y1 - y2)*(y1 - y2));
        }

        private int getTime(String t) {
            List<Integer> time = Arrays.stream(t.split(":")).map(Integer::parseInt).collect(Collectors.toList());
            return time.get(0)*60 + time.get(1);
        }

        public boolean isInTime(Flight flight) {
            return getTimeDiff(flight) >= (getDistanceDiff(flight) / v) * 60;
        }
    }
}
