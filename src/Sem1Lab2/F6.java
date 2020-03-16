package Sem1Lab2;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class F6 {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int n = Integer.parseInt(br.readLine());
        List<Integer> stack = new ArrayList<>();
        List<String> ans = new ArrayList<>();
        int curNumb = 1;

        for (String el : br.readLine().split(" ")) {
            int numb = Integer.parseInt(el);
            stack.add(numb);
            ans.add("push");
            while (stack.size() - 1 >= 0 && stack.get(stack.size() - 1) == curNumb) {
                stack.remove(stack.size() - 1);
                ans.add("pop");
                curNumb++;
            }
        }
        if (stack.size() != 0) {
            System.out.println("impossible");
        } else {
            for (String el : ans){
                System.out.println(el);
            }
        }
    }
}
