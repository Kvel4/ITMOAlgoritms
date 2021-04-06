package Sem3Lab1;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class D_EdgeConnection {
    private static Map<Integer, List<Integer>> graph = new HashMap<>();
    private static Stack<Integer> stack = new Stack<>();
    private static int time = 0;
    private static int curComponent = 1;
    private static int[] in;
    private static int[] up;
    private static int[] components;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String[] splited = br.readLine().split(" ");
        int n = Integer.parseInt(splited[0]), m = Integer.parseInt(splited[1]);
        in = new int[n+1];
        up = new int[n+1];
        components = new int[n+1];

        for (int i = 1; i <= n; i++) {
            graph.put(i, new ArrayList<>());
        }

        for (int i = 0; i < m; i++) {
            splited = br.readLine().split(" ");
            int u = Integer.parseInt(splited[0]), v = Integer.parseInt(splited[1]);
            graph.get(u).add(v);
            graph.get(v).add(u);
        }

        for (int i = 1; i <= n; i++) {
            if (in[i] == 0) {
                dfs(i, i);
                while (!stack.empty()){
                    components[stack.pop()] = curComponent;
                }
                curComponent++;
            }
        }

        System.out.println(curComponent-1);
        for (int i = 1; i <= n; i ++) {
            System.out.print(components[i] + " ");
        }
    }

    private static void dfs(int v, int par) {
        time++;
        in[v] = time;
        up[v] = time;
        stack.push(v);
        boolean firstTime = true;

        for (Integer u : graph.get(v)) {
            if (u == par && firstTime) {
                firstTime = false;
                continue;
            }
            if (in[u] == 0) {
                dfs(u, v);
                up[v] = Math.min(up[v], up[u]);
                if (up[u] > in[v]) {
                    while (!stack.peek().equals(u)){
                        components[stack.pop()] = curComponent;
                    }
                    components[stack.pop()] = curComponent;
                    curComponent++;
                }
            } else {
                up[v] = Math.min(up[v], in[u]);
            }
        }
    }
}

