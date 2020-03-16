package Sem1Lab2;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

public class C3 {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int n = Integer.parseInt(br.readLine());
        Integer[] queue = new Integer[(int) 10e5];
        Map<Integer, Integer> dict = new HashMap<>();
        int start = 0, end = 0;

        for (int i = 0; i < n; i++){
            String[] x = br.readLine().split(" ");
            int x1 = Integer.parseInt(x[0]), x2;
            switch (x1){
                case 1:
                    x2 = Integer.parseInt(x[1]);
                    dict.put(x2, end);
                    queue[end++] = x2;
                    break;
                case 2:
                    dict.remove(queue[start]);
                    start++;
                    break;
                case 3:
                    dict.remove(queue[end]);
                    end--;
                    break;
                case 4:
                    x2 = Integer.parseInt(x[1]);
                    System.out.println(dict.get(x2) - start);
                    break;
                case 5:
                    System.out.println(queue[start]);
                    break;
            }
        }
    }
}
