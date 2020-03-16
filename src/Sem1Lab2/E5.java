package Sem1Lab2;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class E5 {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        List<Integer> stack = new ArrayList<>();
        String[] s = br.readLine().split(" ");
        for (String el : s){
            try{
                stack.add(Integer.parseInt(el));
            } catch (NumberFormatException ex){
                int x1 = stack.remove(stack.size() - 1), x2 = stack.remove(stack.size() - 1);
                switch (el){
                    case "+":
                        stack.add(x2 + x1);
                        break;
                    case "-":
                        stack.add(x2 - x1);
                        break;
                    case "*":
                        stack.add(x2 * x1);
                        break;
                }
            }
        }
        System.out.println(stack.get(0));
    }
}
