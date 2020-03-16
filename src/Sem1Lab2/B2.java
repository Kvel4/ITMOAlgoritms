package Sem1Lab2;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class B2 {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        List<Integer> params = new ArrayList<>();
        for (String el : br.readLine().split(" ")) {
            params.add(Integer.parseInt(el));
        }
        params.add(-1);

        List<Integer[]> stack = new ArrayList<>();
        Integer[] pair;
        int deleted = 0;

        for (int i = 2; i <= params.get(0) + 1; i++) {
            pair = new Integer[]{params.get(i - 1), 1};
            while (i <= params.get(0) && params.get(i).equals(params.get(i - 1))) {
                pair[1]++;
                i++;
            }

            if (stack.size() == 0) {
                if (pair[1] >= 3) {
                    deleted += pair[1];
                } else {
                    stack.add(pair);
                }
            } else if (stack.get(stack.size() - 1)[0].equals(pair[0])) {
                Integer[] last = stack.remove(stack.size() - 1);
                if (pair[1] + last[1] >= 3) {
                    deleted += pair[1] + last[1];
                } else {
                    pair[1] += last[1];
                    stack.add(pair);
                }
            } else {
                if (pair[1] >= 3) {
                    deleted += pair[1];
                } else {
                    stack.add(pair);
                }
            }
        }
        System.out.print(deleted);
    }
}
