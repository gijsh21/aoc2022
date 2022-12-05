package aoc.solutions.day3;

import aoc.Util;

import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

public class D3P1 {

    public static void run() {

        Scanner sc = Util.readFile("src/aoc/solutions/day3/input.txt");

        int prioritySum = 0;
        while(sc.hasNextLine()) {
            String line = sc.nextLine();
            prioritySum += solve(line);
        }

        sc.close();

        System.out.println(prioritySum);

    }

    private static int solve(String s) {

        Set<Character> set = new HashSet<>();
        char[] input = s.toCharArray();

        for(int i = 0; i < input.length / 2; i++) {
            set.add(input[i]);
        }

        for(int i = input.length / 2; i < input.length; i++) {
            char c = input[i];
            if(set.contains(c)) {
                return val(c);
            }
        }

        return 0;

    }

    private static int val(char c) {

        if(c < 91) {
            return c - 38;
        } else {
            return c - 96;
        }

    }

}
