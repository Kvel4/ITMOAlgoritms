package Sem3Lab3;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

public class M13_LargestSubstring {
    private static List<HashSet<Integer>> subStringsHashes = new ArrayList<>();
    private static List<String> strings = new ArrayList<>();
    private static List<Long> pDegrees = new ArrayList<>();
    private static Integer mod = 1000000007;
    private static Integer p = 31;
    private static String string;
    private static Integer n = 2;


    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        string = br.readLine();
        for (int i = 0; i < n - 1; i++) {
            strings.add(br.readLine());
            subStringsHashes.add(new HashSet<>());
        }
//        System.out.println(checkLength(3));
        System.out.println(binarySearch(Math.min(string.length(), strings.get(0).length())));
    }

    private static int binarySearch(int r) {
        int l = 0;
        while (r-l != 1) {
            int m = (r + l) / 2;
            if (checkLength(m)) {
                l = m;
            } else {
                r = m;
            }
        }
        return l;
    }

    private static boolean checkLength(int length) {
        buildHashes(length);
        long hash = getStartHash(string, length);
        int cnt = 0;
        for (HashSet<Integer> set : subStringsHashes) {
            if (set.contains((int) hash)) {
                cnt++;
            }
        }
        if (cnt == subStringsHashes.size()) {
            return true;
        }

        for (int j = 1; j <= string.length() - length; j++) {
            cnt = 0;
            hash = nextHash(string, hash, j, length);
            for (HashSet<Integer> set : subStringsHashes) {
                if (set.contains((int) hash)) {
                    cnt++;
                }
            }
            if (cnt == subStringsHashes.size()) {
                return true;
            }
        }

        return false;
    }

    private static void buildHashes(int length) {
        for (int i = 0; i < strings.size(); i++) {
            long hash = getStartHash(strings.get(i), length);
            subStringsHashes.get(i).add((int) hash);
            for (int j = 1; j <= strings.get(i).length() - length; j++) {
                hash = nextHash(strings.get(i), hash, j, length);
                subStringsHashes.get(i).add((int) hash);
            }
        }
    }

    private static long getStartHash(String s, int length) {
        pDegrees.add(1L);
        long cur_p = 1;
        long hash = 0;
        for (int i = length - 1; i >= 0; i--) {
            hash = (hash + (s.charAt(i) * cur_p) % mod) % mod;
            cur_p = (cur_p * p) % mod;
            pDegrees.add(cur_p);
        }
        return hash;
    }

    private static long nextHash(String s, long previousHash, int startPosition, int length) {
        return ((((previousHash - s.charAt(startPosition - 1) * pDegrees.get(length - 1))) % mod * p) % mod + s.charAt(startPosition + length - 1)) % mod;
    }
}
