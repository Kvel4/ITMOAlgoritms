package Sem2Lab1;

import java.io.*;

public class K {
    private static int n;
    private static int[] tree;

    private static int next2degree(int numb) {
        int base = 1;
        while (base < numb) {
            base *= 2;
        }
        return base;
    }

    private static int set(int i, int x, int lx, int rx) {
        if (rx - lx == 1) {
            tree[x] = Integer.MIN_VALUE;
            return x - n + 1;
        }
        int m = (lx + rx) / 2;
        int ans;
        if (tree[2*x] >= i) {
            ans = set(i, 2 * x, lx, m);
        } else if (tree[2*x + 1] >= i) {
            ans = set(i, 2 * x + 1, m, rx);
        } else {
            ans = set(0, x, lx, rx);
        }
        tree[x] = Math.max(tree[2*x], tree[2*x + 1]);
        return ans;
    }

    private static void delete(int i) {
        i += n;
        tree[i] = i - n;
        while (i > 1) {
            i /= 2;
            tree[i] = Math.max(tree[2*i], tree[2*i + 1]);
        }
    }

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new FileReader("parking.in"));
        String[] elements = br.readLine().split(" ");
        int tmp = Integer.parseInt(elements[0]), m = Integer.parseInt(elements[1]);
        n = next2degree(tmp);
        tree = new int[2 * n];

        for (int i = n; i < 2 * n; i++) {
            if (i < n + tmp) {
                tree[i] = i - n;
            } else {
                tree[i] = Integer.MIN_VALUE;
            }
        }

        for (int i = n - 1; i > 0; i--) {
            tree[i] = Math.max(tree[2*i], tree[2*i + 1]);
        }


        PrintWriter pw = new PrintWriter("parking.out");
        for (int i = 0; i < m; i++) {
            elements = br.readLine().split(" ");
            if (elements[0].equals("enter")) {
                pw.println(set(Integer.parseInt(elements[1]) - 1, 1,0, n));
            } else {
                delete(Integer.parseInt(elements[1]) - 1);
            }
        }
        pw.close();
    }
}
