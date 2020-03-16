package Sem1Lab2;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class D4 {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        LinkedList list = new LinkedList();
        int n = Integer.parseInt(br.readLine());

        for (int i = 0; i < n; i++){
            String[] s = br.readLine().split(" ");
            switch (s[0]){
                case "+":
                    list.add(new Element(Integer.parseInt(s[1])));
                    break;
                case "-":
                    System.out.println(list.delete());
                    break;
                case "*":
                    list.insertMiddle(new Element(Integer.parseInt(s[1])));
                    break;
            }
        }
    }

    private static class Element {
        Integer value;
        Element prev, next;

        public Element(){
            this.value = null;
            this.prev = this;
            this.next = this;
        }

        public Element(Integer value){
            super();
            this.value = value;
        }

    }

    private static class LinkedList {
        int size = 0;
        Element root, middle;

        public LinkedList(){
            root = new Element();
            middle = root;
        }

        public void add(Element el){
            el.next = root;
            el.prev = root.prev;
            el.next.prev = el;
            el.prev.next = el;
            size++;

            if (size % 2 == 1){
                middle = middle.next;
            }
        }

        public void insertMiddle(Element el){
            el.prev = middle;
            el.next = middle.next;
            middle.next.prev = el;
            middle.next = el;
            size++;

            if (size % 2 == 1) {
                middle = middle.next;
            }
        }

        public int delete() {
            int toReturn = root.next.value;
            root.next = root.next.next;
            root.next.prev = root;
            size--;

            if (size == 0){
                middle = root;
            } else if (size % 2 == 1){
                middle = middle.next;
            }
            return toReturn;
        }
    }
}
