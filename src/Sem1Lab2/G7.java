package Sem1Lab2;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class G7 {
    static void union(Set first, Set second){
        first = get(first);
        second = get(second);

        if (first != second) {
            if (second.rang > first.rang) {
                Set tmp = second;
                second = first;
                first = tmp;
            }
            if (first.rang == second.rang) {
                first.rang++;
            }

            first.size += second.size;
            first.min = Math.min(first.min, second.min);
            first.max = Math.max(first.max, second.max);
            second.parent = first;
        }
    }

    static Set get(Set v){
        if (v == v.parent){
            return v;
        }
        return v.parent = get(v.parent);
    }

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int n = Integer.parseInt(br.readLine());
        Set[] dsu = new Set[n+1];
        for (int i = 1; i <= n; i++) {
            dsu[i] = new Set(i);
        }
        String tmp;
        while ((tmp = br.readLine())!= null) {
            String[] s = tmp.split(" ");
            if (s[0].equals("union")){
                union(dsu[Integer.parseInt(s[1])], dsu[Integer.parseInt(s[2])]);
            } else {
                Set v = get(dsu[Integer.parseInt(s[1])]);
                System.out.println(v.min + " " + v.max + " " + v.size);
            }
        }


    }

    private static class Set{
        int min, max, size = 1 , rang = 1;
        Set parent = this;

        Set(int el){
            min = el;
            max = el;
        }
    }
}
