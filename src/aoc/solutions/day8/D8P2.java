package aoc.solutions.day8;

import aoc.Util;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;

public class D8P2 {

    private static class Tree {

        int height;
        boolean visible;
        int scenicScore;

        Tree(int height, boolean visible) {
            this.height = height;
            this.visible = visible;
            this.scenicScore = 1;
        }

    }

    public static void run() {

        Scanner sc = Util.readFile("src/aoc/solutions/day8/input.txt");

        List<List<Tree>> map = new ArrayList<>();
        int rowCount = 0;
        while(sc.hasNextLine()) {
            String line = sc.nextLine();
            List<Tree> row = new ArrayList<>();
            char[] chars = line.toCharArray();
            for(char c : chars) {
                row.add(new Tree(Integer.parseInt(String.valueOf(c)), false));
            }
            map.add(row);
        }

        sc.close();

        solve(map);

        int max = map.stream()
                .flatMap(List::stream)
                .max(Comparator.comparingInt(tree -> tree.scenicScore))
                .orElse(new Tree(-1, false))
                .scenicScore;
        System.out.println(max);
    }

    private static void solve(List<List<Tree>> map) {

        for(int i = 0; i < map.size(); i++) {
            List<Tree> row = map.get(i);
            for(int j = 0; j < row.size(); j++) {
                calculateViewingDistance(i, j, map);
            }
        }

    }

    private static void calculateViewingDistance(int i, int j, List<List<Tree>> map) {

        Tree curr = map.get(i).get(j);
        int ctr = 0;

        // Look left
        for(int kJ = j - 1; kJ >= 0; kJ--) {
            ctr++;
            if(map.get(i).get(kJ).height >= curr.height) break;
        }
        curr.scenicScore *= ctr;

        // Look right
        ctr = 0;
        for(int kJ = j + 1; kJ < map.get(i).size(); kJ++) {
            ctr++;
            if(map.get(i).get(kJ).height >= curr.height) break;
        }
        curr.scenicScore *= ctr;

        // Look up
        ctr = 0;
        for(int kI = i - 1; kI >= 0; kI--) {
            ctr++;
            if(map.get(kI).get(j).height >= curr.height) break;
        }
        curr.scenicScore *= ctr;

        // Look down
        ctr = 0;
        for(int kI = i + 1; kI < map.size(); kI++) {
            ctr++;
            if(map.get(kI).get(j).height >= curr.height) break;
        }
        curr.scenicScore *= ctr;

    }

}
