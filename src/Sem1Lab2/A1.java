package Sem1Lab2;

import java.io.*;
import java.util.ArrayList;
import java.util.List;


public class A1 {
    private static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

    static int nextInt() throws IOException {
        int symbol;
        boolean Empty = true;
        StringBuilder stringBuilder = new StringBuilder();

        while ((symbol = br.read()) != -1 && (!Character.isWhitespace(symbol) || Empty)) {
            if (!Character.isSpaceChar(symbol)) {
                Empty = false;
            }
            stringBuilder.append((char) symbol);
        }
        return Integer.parseInt(stringBuilder.toString().stripLeading());
    }

    public static void main(String[] args) throws IOException {
        int n = nextInt();
        List<Integer> stack = new ArrayList<>();
        List<Integer> minStack = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            int x1 = nextInt();
            switch (x1) {
                case 1:
                    int x2 = nextInt();
                    stack.add(x2);
                    if (minStack.size() == 0) {
                        minStack.add(x2);
                    } else {
                        minStack.add(Math.min(minStack.get(minStack.size() - 1), x2));
                    }
                    break;
                case 2:
                    stack.remove(stack.size() - 1);
                    minStack.remove(minStack.size() - 1);
                    break;
                case 3:
                    System.out.println(minStack.get(minStack.size() - 1));
             }
        }
    }
}
