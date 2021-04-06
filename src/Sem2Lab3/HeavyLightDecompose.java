//package Sem2Lab3;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class HeavyLightDecompose {
    private static SegmentNode[] tree;
    private static int segmentN;
    private static int cnt = 0;

    private static Map<Integer, List<Integer>> graph = new HashMap<>();
    private static Set<Integer> used = new HashSet<>();
    private static Node[] nodes;
    private static int n;


    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        n = Integer.parseInt(br.readLine()); n++;
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
        buildSegment();

        int m = Integer.parseInt(br.readLine());
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < m; i++) {
            String[] s = br.readLine().split(" ");
            if (s[0].equals("+")) {
                lcaWithAdd(Integer.parseInt(s[1]), Integer.parseInt(s[2]), Integer.parseInt(s[3]));
            } else {
                int ind = nodes[Integer.parseInt(s[1])].indInTree;
                sb.append(sum(ind, ind + 1, 1, 0, segmentN)).append("\n");
            }
        }
        System.out.println(sb);
    }

    private static void lcaWithAdd (int u, int v, int value) {
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
                    add(nodes[u].indInTree, nodes[v].indInTree + 1, value, 1, 0, segmentN);
//                    return u;
                    return;
                }
                add(nodes[v].indInTree, nodes[u].indInTree + 1, value, 1, 0, segmentN);
//                return v;
                return;
            }
            if (nodes[x].depth < nodes[y].depth) {
                add(nodes[y].indInTree, nodes[v].indInTree + 1, value, 1, 0, segmentN);
                v = nodes[y].parent;
            } else {
                add(nodes[x].indInTree, nodes[u].indInTree + 1, value, 1, 0, segmentN);
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



    private static int nextDegree(int segmentN) {
        int tmp = 1;
        while (tmp < segmentN) {
            tmp *= 2;
        }
        return tmp;
    }

    private static void buildSegment() {
        segmentN = nextDegree(n);
        tree = new SegmentNode[segmentN * 2];

        for (int i = 0; i < segmentN * 2; i++) {
            tree[i] = new SegmentNode();
        }

        for (int i = segmentN - 1; i > 0; i--) {
            tree[i].value = tree[2 * i].value + tree[2 * i + 1].value;
        }
    }

    private static void propagate(int v, int lx, int rx) {
        if (rx - lx == 1) {
            return;
        }
        if (tree[v].add == 0) {
            return;
        }
        tree[2 * v].value += tree[v].add;
        tree[2 * v].add += tree[v].add;
        tree[2 * v + 1].value += tree[v].add;
        tree[2 * v + 1].add += tree[v].add;
        tree[v].add = 0;
    }

    private static long sum(int l, int r, int v, int lx, int rx) {
        propagate(v, lx, rx);
        if (r <= lx || l >= rx) {
            return 0;
        }
        if (l <= lx &&  rx <= r) {
            return tree[v].value;
        }
        int m = (lx + rx) / 2;
        return sum(l, r, 2 * v, lx, m) + sum(l, r, 2 * v + 1, m, rx);
    }

    private static void add(int l, int r, long value, int v, int lx, int rx) {
        propagate(v, lx, rx);
        if (r <= lx || l >= rx) {
            return;
        }
        if (l <= lx &&  rx <= r) {
            tree[v].value += value;
            tree[v].add += value;
            return;
        }
        int m = (lx + rx) / 2;
        add(l, r, value, 2 * v, lx, m);
        add(l, r, value, 2 * v + 1, m, rx);
        tree[v].value = tree[2*v].value + tree[2*v + 1].value;
    }

    private static class SegmentNode {
        long value = 0;
        long add = 0;
    }
}