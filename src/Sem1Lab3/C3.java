package Sem1Lab3;

import java.util.*;

public class C3 {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt();
        int[] seq = new int[n+1];
        int[] seqRep = new int[n+1];
        int[] indexes = new int[n+1];

        seqRep[0] = Integer.MIN_VALUE;
        for (int i = 1; i < n + 1; i++) {
            seq[i] = sc.nextInt();
            seqRep[i] = Integer.MAX_VALUE;
        }

        for (int i = 1; i < n + 1; i++) {
            int ind = upper_bound(seqRep, seq[i]);
            seqRep[ind] = seq[i];
            indexes[i] = ind;
        }

        int ind = 0;
        for (int i = 0; i < n+1; i++) {
            if (seqRep[i] < Integer.MAX_VALUE) {
                ind = i;
            }
        }
        System.out.println(ind);

        List<Integer> ans = new ArrayList<>();
        int cur = Arrays.stream(indexes).max().getAsInt();
        for (int i = n; i >= 1; i--) {
            if (cur == indexes[i]) {
                ans.add(seq[i]);
                cur--;
            }
        }

        for (int i = ans.size()-1; i >= 0; i--) {
            System.out.print(ans.get(i) + " ");
        }
    }



    static int upper_bound(int[] a, int el) {
        int l = 0, r = a.length, m;
        while(r - l != 1) {
            m = (l+r) / 2;
            if (a[m] <= el) {
                l = m;
            } else {
                r = m;
            }
        }
        return r;
    }
}
