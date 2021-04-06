package Sem2Lab3;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class E_PsevdoHLD {
    private static int[] tree;
    private static int cnt = 0;

    private static Map<Integer, List<Integer>> graph = new HashMap<>();
    private static Set<Integer> used = new HashSet<>();
    private static Node[] nodes;
    private static int n;


    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        n = Integer.parseInt(br.readLine()); n++;
        tree = new int[n];
        nodes = new Node[n];

        for (int i = 1; i < n; i++) {
            nodes[i] = new Node();
            graph.put(i, new ArrayList<>());
        }


        for (int i = 2; i < n; i++) {
            String[] s = br.readLine().split(" ");
            int u = Integer.parseInt(s[0]), v = Integer.parseInt(s[1]);
            graph.get(u).add(v);
            graph.get(v).add(u);
//            nodes[v].parent = u;
//            nodes[u].light.add(v);
        }

        buildTree(1);
        dfs(1, 1);

        int m = Integer.parseInt(br.readLine());
        for (int i = 0; i < m; i++) {
            String[] s = br.readLine().split(" ");
            lcaWithAdd(Integer.parseInt(s[0]), Integer.parseInt(s[1]));
        }
        int cnt = 0;
        int balance = 0;
        for (int i = 0; i < n; i++) {
            balance += tree[i];
            if (balance == 0 && i != n - 1) {
                cnt++;
            }
        }
        if (m == 0) {
            System.out.println(cnt - 1);
        } else {
            System.out.println(cnt);
        }
    }

    private static void lcaWithAdd (int u, int v) {
        int cnt = 0;
        while (true) {
            cnt++;
            if (cnt > 1000000) {
                throw new IllegalStateException();
            }
            int x = nodes[u].top;
            int y = nodes[v].top;
            if (x == y) {
                if (nodes[u].depth < nodes[v].depth) {
                    tree[nodes[u].indInTree] += 1;
                    tree[nodes[v].indInTree + 1] -= 1;
//                    return u;
                    return;
                }
                tree[nodes[v].indInTree] += 1;
                tree[nodes[u].indInTree + 1] -= 1;
//                return v;
                return;
            }
            if (nodes[x].depth < nodes[y].depth) {
                tree[nodes[y].indInTree] += 1;
                tree[nodes[v].indInTree + 1] -= 1;
                v = nodes[y].parent;
            } else {
                tree[nodes[x].indInTree] += 1;
                tree[nodes[u].indInTree + 1] -= 1;
                u = nodes[x].parent;
            }
        }

    }

    private static void buildTree(int v) {
        used.add(v);

        int heavy = 0;
        int maxSize = Integer.MIN_VALUE;

        for (int son: graph.get(v)) {
            if (used.contains(son)) {
                continue;
            }
            nodes[son].parent = v;
            nodes[v].light.add(son);
            nodes[son].depth = nodes[v].depth + 1;
            buildTree(son);
            nodes[v].size += nodes[son].size;
            if (nodes[son].size > maxSize) {
                maxSize = nodes[son].size;
                heavy = son;
            }
        }
        nodes[v].light.remove((Integer) heavy);
        nodes[v].heavy = heavy;
    }

    private static void dfs(int v, int top) {
        nodes[v].top = top;
        nodes[v].indInTree = cnt;
        cnt += 1;
        if (nodes[v].size == 1) {
            return;
        }
        dfs(nodes[v].heavy, top);
        for (int light: nodes[v].light) {
            dfs(light, light);
        }
    }


    private static class Node {
        int depth, size, indInTree, parent, heavy, top;
        List<Integer> light = new ArrayList<>();


        public Node() {
            depth = 1;
            size = 1;
        }

//        public Node(int parent) {
//            this.parent = parent;
//            size = 1;
//        }
    }
}
