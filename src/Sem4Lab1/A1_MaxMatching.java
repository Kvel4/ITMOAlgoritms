package Sem4Lab1;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class A1_MaxMatching {
    private static int n, m;
    private static int[] mt;
    private static boolean[] visited;
    private static final List<List<Integer>> G = new ArrayList<>();

    public static void main(String[] args) throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
        String[] splited = bufferedReader.readLine().split(" ");
        n = Integer.parseInt(splited[0]);
        m = Integer.parseInt(splited[1]);
        visited = new boolean[n];
        mt = new int[m];
        Arrays.fill(mt, -1);

        for (int i = 0; i < n; i++) {
            G.add(new ArrayList<>());
        }

        for (int i = 0; i < n; i++) {
            splited = bufferedReader.readLine().split(" ");
            for (String element : splited) {
                int el = Integer.parseInt(element);
                if (el != 0) G.get(i).add(el - 1);
            }
        }

        for (int i = 0; i < n; i++) {
            Arrays.fill(visited, false);
            dfs(i);
        }

        int cnt = 0;
        for (int i = 0; i < m; i++) {
            if (mt[i] != -1) cnt++;
        }
        System.out.println(cnt);
        for (int i = 0; i < m; i++) {
            if (mt[i] != -1) {
                System.out.println((mt[i] + 1) + " " + (i + 1));
            }
        }
    }

    private static boolean dfs(int v) {
        if (visited[v]) return false;
        visited[v] = true;

        for (Integer to : G.get(v)) {
            if (mt[to] == -1 || dfs(mt[to])) {
                mt[to] = v;
                return true;
            }
        }
        return false;
    }
}

