package aoc.solutions.day3;

import aoc.Util;

import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

public class D3P2 {

    public static void run() {

        Scanner sc = Util.readFile("src/aoc/solutions/day3/input.txt");

        int prioritySum = 0;
        while(sc.hasNextLine()) {
            String line1 = sc.nextLine();
            String line2 = sc.nextLine();
            String line3 = sc.nextLine();
            prioritySum += solve(line1, line2, line3);
        }

        sc.close();

        System.out.println(prioritySum);

    }

    private static int solve(String s1, String s2, String s3) {

        Set<Character> set1 = new HashSet<>();
        Set<Character> set2 = new HashSet<>();

        char[] input1 = s1.toCharArray();
        char[] input2 = s2.toCharArray();
        char[] input3 = s3.toCharArray();

        for(int i = 0; i < input1.length; i++) {
            set1.add(input1[i]);
        }
        for(int i = 0; i < input2.length; i++) {
            set2.add(input2[i]);
        }

        for(int i = 0; i < input3.length; i++) {
            char c = input3[i];
            if(set1.contains(c) && set2.contains(c)) {
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
