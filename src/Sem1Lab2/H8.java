package Sem1Lab2;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class H8 {
    static void union(ClanMember first, ClanMember second){
        first = get(first);
        second = get(second);

        if (first != second) {
            if (second.exp > first.exp) {
                ClanMember tmp = second;
                second = first;
                first = tmp;
            }
            second.parent = first;
            second.oldLeaderExp = first.exp;
        }
    }

    static ClanMember get(ClanMember v){
        if (v == v.parent){
            return v;
        }
        ClanMember newClanLeader = get(v.parent);
        v.exp += v.parent.exp - v.oldLeaderExp;
        v.oldLeaderExp = newClanLeader.exp;
        v.parent = newClanLeader;
        return v.parent;
    }

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String[] nm = br.readLine().split(" ");
        int n = Integer.parseInt(nm[0]), m = Integer.parseInt(nm[1]);
        ClanMember[] dsu = new ClanMember[n+1];
        for (int i = 1; i <= n; i++) {
            dsu[i] = new ClanMember(i);
        }

        for (int i = 0; i < m; i++) {
            String[] s = br.readLine().split(" ");
            if (s[0].equals("join")){
                union(dsu[Integer.parseInt(s[1])], dsu[Integer.parseInt(s[2])]);
            } else if (s[0].equals("get")){
                ClanMember v = get(dsu[Integer.parseInt(s[1])]);
                System.out.println(dsu[Integer.parseInt(s[1])].exp);
            } else {
                ClanMember v = get(dsu[Integer.parseInt(s[1])]);
                v.exp += Integer.parseInt(s[2]);
            }
        }
    }

    private static class ClanMember{
        int exp = 0, oldLeaderExp = 0, index;
        ClanMember parent = this;

        ClanMember (int i){
            this.index = i;
        }
    }
}
