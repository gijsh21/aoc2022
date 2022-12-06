package aoc.solutions.day6;

import aoc.Util;

import java.util.*;

public class D6P2 {

    public static void run() {

        Scanner sc = Util.readFile("src/aoc/solutions/day6/input.txt");

        int res = solve(sc.nextLine());

        sc.close();

        System.out.println(res);

    }

    private static int solve(String s) {

        char[] input = s.toCharArray();
        List<Character> chars = new ArrayList<>();
        for(int i = 0; i < 14; i++) {
            chars.add(input[i]);
        }
        for(int i = 14; i < input.length; i++) {
            if(distinct(chars)) {
                return i;
            } else {
                chars.remove(0);
                chars.add(input[i]);
            }
        }

        if(distinct(chars)) return input.length;

        return -1;

    }

    private static boolean distinct(List<Character> chars) {

        Set<Character> charset = new HashSet<>(chars);

        return charset.size() == chars.size();

    }

}
