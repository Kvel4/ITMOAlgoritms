package Sem3Lab3;

import java.io.*;
import java.util.*;

public class G7_BorTree {
    private static Node root = new Node();
    private static boolean[] contains;
    private static int cnt = 0;
    private static int n;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        n = Integer.parseInt(br.readLine());
        contains = new boolean[n];
        root.processed = true;

        for (int i = 0; i < n; i++) {
            String string = br.readLine();
            addString(string, i);
        }

        buildSufLinks();
        String string = br.readLine();
        processString(string);
        BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(System.out));
        for (boolean el : contains) {
            writer.write(el ? "YES\n" : "NO\n");
        }
        writer.flush();
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

            checkMatches(cur);
        }

    }

    private static void checkMatches(Node node) {
        if (!node.processed) {
            if (node.isTerminal) {
                for (Integer i : node.stringNumbers) {
                    contains[i] = true;
                }
            }
            node.processed = true;
            checkMatches(node.sufLink);
        }
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
        private boolean processed = false;
        private boolean visited = false;

        private List<Integer> stringNumbers = new ArrayList<>();

        private Node() { }

        private Node(Node parent, Character parentChar) {
            this.parent = parent;
            this.parentChar = parentChar;
        }
    }
}
