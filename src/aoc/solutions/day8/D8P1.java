package aoc.solutions.day8;

import aoc.Util;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class D8P1 {

    private static class Tree {

        int height;
        boolean visible;

        Tree(int height, boolean visible) {
            this.height = height;
            this.visible = visible;
        }

    }

    public static void run() {

        Scanner sc = Util.readFile("aoc2022/src/aoc/solutions/day8/input.txt");

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

        int res = solve(map);
        System.out.println(res);

    }

    private static int solve(List<List<Tree>> map) {

        int visCount = 0;

        for(List<Tree> row : map) {
            int currHeight = -1;
            for(Tree tree : row) {
                if(tree.height > currHeight) {
                    currHeight = tree.height;
                    if(!tree.visible) {
                        tree.visible = true;
                        visCount++;
                    }
                }
            }
        }

        for(List<Tree> row : map) {
            int currHeight = -1;
            for(int i = row.size() - 1; i >= 0; i--) {
                Tree tree = row.get(i);
                if(tree.height > currHeight) {
                    currHeight = tree.height;
                    if(!tree.visible) {
                        tree.visible = true;
                        visCount++;
                    }
                }
            }
        }

        int columnAmount = map.get(0).size();

        for(int i = 0; i < columnAmount; i++) {
            int currHeight = -1;
            for(int j = 0; j < map.size(); j++) {
                Tree tree = map.get(j).get(i);
                if(tree.height > currHeight) {
                    currHeight = tree.height;
                    if(!tree.visible) {
                        tree.visible = true;
                        visCount++;
                    }
                }
            }
        }

        for(int i = 0; i < columnAmount; i++) {
            int currHeight = -1;
            for(int j = map.size() - 1; j >= 0; j--) {
                Tree tree = map.get(j).get(i);
                if(tree.height > currHeight) {
                    currHeight = tree.height;
                    if(!tree.visible) {
                        tree.visible = true;
                        visCount++;
                    }
                }
            }
        }

        return visCount;

    }

}
