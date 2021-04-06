package Sem3Lab3;

import java.io.*;
import java.util.*;

public class H8_BorNumberOfStringEnterance {
    private static List<Node> topSort = new ArrayList<>();
    private static List<Node> terminalNodes = new ArrayList<>();

    private static Node root = new Node();
    private static int[] entranceNumber;
    private static int n;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        n = Integer.parseInt(br.readLine());
        entranceNumber = new int[n];
        root.termLink = root;

        for (int i = 0; i < n; i++) {
            String string = br.readLine();
            addString(string, i);
        }

        buildSufLinks();
        String string = br.readLine();
        processString(string);

        topSort();
        Collections.reverse(topSort);
        countEntrance();

        BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(System.out));
        for (int el : entranceNumber) {
            writer.write(el + "\n");
        }
        writer.flush();
    }

    private static void countEntrance(){
        for (Node node : topSort) {
            for (int number : node.stringNumbers) {
                entranceNumber[number] += node.visitsNumber;
            }
            node.termLink.visitsNumber += node.visitsNumber;
        }
    }

    private static void topSort(){
        for (Node node : terminalNodes) {
            if (node.color == 0) {
                dfs(node);
            }
        }
    }

    private static void dfs(Node u) {
        u.color = 1;

        Node v = u.termLink;
        if (v.color == 0) {
            dfs(v);
        }

        u.color = 2;
        topSort.add(u);
    }

    private static void addString(String string, int number) {
        Node cur = root;
        int i = 0;

        for (Character ch : string.toCharArray()) {
            int c = ch - 'a';
            if (cur.go[c] == null) {
                cur.go[c] = new Node(cur, ch);
            }
            if (i == string.length() - 1) {
                cur.go[c].isTerminal = true;
                cur.go[c].stringNumbers.add(number);
            }
            cur = cur.go[c];
            i++;
        }
    }

    private static void processString(String string) {
        Node cur = root;

        for (Character ch : string.toCharArray()) {
            int c = ch - 'a';
            if (cur.go[c] == null) {
                while (cur.sufLink != null && cur.go[c] == null) {
                    cur = cur.sufLink;
                }
            }
            if (cur.go[c] != null) {
                cur = cur.go[c];
            }

            buildTermLink(cur);
            if (cur.isTerminal) {
                cur.visitsNumber++;
            } else {
                cur.termLink.visitsNumber++;
            }
        }

    }

//    private static void goThroughTermLink(Node node) {
//        while (node != root) {
//            for (int stringNumber : node.stringNumbers) {
//                entranceNumber[stringNumber]++;
//            }
//            buildTermLink(node);
//            node = node.termLink;
//        }
//    }

    private static Node buildTermLink(Node node) {
        if (node.termLink == null) {
            if (node.isTerminal) {
                terminalNodes.add(node);
            }
            node.termLink = buildTermLink(node.sufLink);
        }
        return node.isTerminal ? node : node.termLink;
    }

    private static void buildSufLinks() {
        ArrayDeque<Node> queue = new ArrayDeque<>();
        queue.add(root);

        while (!queue.isEmpty()) {
            Node cur = queue.removeFirst();

            for (Node node : cur.go) {
                if (node != null && !node.visited) {
                    node.visited = true;
                    queue.add(node);
                    buildSufLink(node);
                }
            }
        }
    }

    private static void buildSufLink(Node start) {
        Character ch = start.parentChar;
        Node cur = start.parent.sufLink;
        int c = ch - 'a';

        if (cur == null) {
            start.sufLink = root;
            return;
        }

        while (cur.sufLink != null && cur.go[c] == null) {
            cur = cur.sufLink;
        }

        if (cur.go[c] != null) {
            start.sufLink = cur.go[c];
        } else {
            start.sufLink = root;
        }

    }

    private static class Node {
        private Node[] go = new Node[26];
        private boolean isTerminal = false;

        private Node parent;
        private Character parentChar;
        private Node sufLink;
        private Node termLink;
        private boolean visited = false;

        private int visitsNumber;
        private int color;

        private List<Integer> stringNumbers = new ArrayList<>();

        private Node() { }

        private Node(Node parent, Character parentChar) {
            this.parent = parent;
            this.parentChar = parentChar;
        }
    }

}
