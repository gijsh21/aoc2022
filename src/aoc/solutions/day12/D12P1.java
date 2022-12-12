package aoc.solutions.day12;

import aoc.Util;

import java.util.*;

public class D12P1 {

    private static class Node {

        int id;
        int currMinPath;
        List<Integer> adj;

        Node(int id) {
            this.id = id;
            this.currMinPath = Integer.MAX_VALUE;
            adj = new ArrayList<>();
        }

    }

    public static void run() {

        Scanner sc = Util.readFile("src/aoc/solutions/day12/input.txt");

        List<String> lines = new ArrayList<>();
        while(sc.hasNextLine()) {
            String line = sc.nextLine();
            lines.add(line);
        }
        sc.close();

        List<Node> nodes = new ArrayList<>();
        int startIdx = -1;
        int endIdx = -1;

        char[][] map = new char[lines.size()][lines.get(0).length()];
        for(int i = 0; i < lines.size(); i++) {
            map[i] = lines.get(i).toCharArray();
        }

        int lineLength = lines.get(0).length();
        for(int i = 0; i < map.length; i++) {
            for(int j = 0; j < lineLength; j++) {

                Node curr = new Node(i * lineLength + j);
                char currChar = map[i][j];
                if(currChar == 'E') {
                    endIdx = curr.id;
                    currChar = 'z';
                } else if(currChar == 'S') {
                    startIdx = curr.id;
                    currChar = 'a';
                }

                // Check left node
                if(j > 0) {
                    char leftChar = map[i][j - 1];
                    if(canBeReached(currChar, leftChar)) curr.adj.add(i * lineLength + j - 1);
                }

                // Check right node
                if(j < lineLength - 1) {
                    char rightChar = map[i][j + 1];
                    if(canBeReached(currChar, rightChar)) curr.adj.add(i * lineLength + j + 1);
                }

                // Check up node
                if(i > 0) {
                    char upChar = map[i - 1][j];
                    if(canBeReached(currChar, upChar)) curr.adj.add((i - 1) * lineLength + j);
                }

                // Check down node
                if(i < map.length - 1) {
                    char downChar = map[i + 1][j];
                    if(canBeReached(currChar, downChar)) curr.adj.add((i + 1) * lineLength + j);
                }

                nodes.add(curr);

            }
        }

        System.out.println(shortestPath(nodes, startIdx, endIdx));

    }

    private static boolean canBeReached(char curr, char target) {

        if(target == 'S') target = 'a';
        if(target == 'E') target = 'z';

        return (curr + 1) >= target;

    }

    private static int shortestPath(List<Node> nodes, int startId, int endId) {

        if(nodes.size() <= 0 || startId < 0 || startId >= nodes.size() || endId < 0 || endId >= nodes.size()) throw new IllegalArgumentException();

        PriorityQueue<Node> pq = new PriorityQueue<>(nodes.size(), Comparator.comparingInt(n -> n.currMinPath));
        nodes.get(startId).currMinPath = 0;
        pq.add(nodes.get(startId));

        boolean[] visited = new boolean[nodes.size()];

        while(!pq.isEmpty()) {

            Node currNode = pq.poll();

            if(currNode.id == endId) return currNode.currMinPath;
            if(visited[currNode.id]) continue;
            visited[currNode.id] = true;

            for(int adjIdx : currNode.adj) {

                Node adj = nodes.get(adjIdx);

                if(currNode.currMinPath + 1 < adj.currMinPath) {
                    adj.currMinPath = currNode.currMinPath + 1;
                    pq.add(adj);
                }

            }

        }

        return -1;

    }

}
