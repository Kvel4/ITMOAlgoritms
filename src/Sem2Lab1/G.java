package Sem2Lab1;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;


public class G {
    private static Node[] tree;
    private static int n;

    private static Node maxx(Node f, Node s) {
        Node tmp = new Node(0);
        if (f.value > s.value) {
            tmp.value = f.value;
            tmp.x = f.x;
            tmp.y = f.y;
        } else {
            tmp.value = s.value;
            tmp.x = s.x;
            tmp.y = s.y;
        }
        return tmp;
    }

    private static int nextDegree(int n) {
        int tmp = 1;
        while (tmp < n) {
            tmp *= 2;
        }
        return tmp;
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

    private static Node max(int l, int r, int v, int lx, int rx) {
        propagate(v, lx, rx);
        if (r <= lx || l >= rx) {
            return new Node(0);
        }
        if (l <= lx &&  rx <= r) {
            return tree[v];
        }
        int m = (lx + rx) / 2;
        return maxx(max(l, r, 2 * v, lx, m), max(l, r, 2 * v + 1, m, rx));
    }

    private static void add(int l, int r, int value, int v, int lx, int rx) {
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
        tree[v] = maxx(tree[2*v], tree[2*v + 1]);
    }

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int m = Integer.parseInt(br.readLine());
        n = nextDegree(4 * 200000);
        tree = new Node[n * 2];

        for (int i = n; i < 2 * n; i++) {
            tree[i] = new Node(i - n);
        }

        for (int i = n-1; i > 0; i--) {
            tree[i] = maxx(tree[2*i], tree[2*i + 1]);
        }


        RectangleBorder[] rectangleBorders = new RectangleBorder[2 * m];

        for (int i = 0; i < 2 * m; i++) {
            String[] elements = br.readLine().split(" ");
            int x1 = Integer.parseInt(elements[0]), y1 = Integer.parseInt(elements[1]),
                    x2 = Integer.parseInt(elements[2]), y2 = Integer.parseInt(elements[3]);
            rectangleBorders[i++] =  new RectangleBorder(x1, y1, y2, true);
            rectangleBorders[i] =  new RectangleBorder(x2, y1, y2, false);
        }

        Arrays.sort(rectangleBorders);

        Node maxxx = new Node(0);
        for (RectangleBorder rectangleBorder : rectangleBorders) {
            if (rectangleBorder.isOpen) {
                add(rectangleBorder.y1 + n/2, rectangleBorder.y2 + n/2 + 1, 1, 1, 0, n);
            } else {
                add(rectangleBorder.y1 + n/2, rectangleBorder.y2 + n/2 + 1, -1, 1, 0, n);
            }
            Node curMax = max(0, n, 1, 0, n);
            if (maxxx.value < curMax.value) {
                maxxx.value = curMax.value;
                maxxx.x = rectangleBorder.x;
                maxxx.y = curMax.y - n/2;
            }
        }

        System.out.println(maxxx.value + "\n" + maxxx.x + " " + maxxx.y);

    }
    private static class RectangleBorder implements Comparable<RectangleBorder> {
        int x, y1, y2;
        boolean isOpen;

        RectangleBorder(int x, int y1, int y2, boolean isOpen){
            this.x = x;
            this.y1 = y1;
            this.y2 = y2;
            this.isOpen = isOpen;
        }

        @Override
        public int compareTo(RectangleBorder o) {
            if (this.x - o.x == 0){
                if (this.isOpen && !o.isOpen) {
                    return -1;
                } else if (this.isOpen || !o.isOpen) {
                    return 0;
                } else {
                    return 1;
                }
            }
            return this.x - o.x;
        }
    }

    private static class Node {
        int value = 0, add = 0, x = 0, y;
        Node(int i) {
            this.y = i;
        }
    }
}
