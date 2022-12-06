package aoc.solutions.day6;

import aoc.Util;

import java.util.*;

public class D6LinTime {

    public static void run() {

        Scanner sc = Util.readFile("src/aoc/solutions/day6/input.txt");

        int res = solve(sc.nextLine(), 14);

        sc.close();

        System.out.println(res);

    }

    private static int solve(String s, int uniqueCharsRequired) {

        int[] alphabet = new int[26]; // -97
        char[] input = s.toCharArray();
        int firstIdx = 0;
        for(int i = 0; i < uniqueCharsRequired - 1; i++) {
            addToArr(alphabet, input[i]);
        }

        for(int i = uniqueCharsRequired - 1; i < input.length; i++) {
            addToArr(alphabet, input[i]);
            if(distinct(alphabet)) return i + 1;
            removeFromArr(alphabet, input[firstIdx]);
            firstIdx++;
        }

        return -1;

    }

    private static void addToArr(int[] arr, char c) {
        arr[c - 97] = arr[c - 97] + 1;
    }

    private static void removeFromArr(int[] arr, char c) {
        arr[c - 97] = arr[c - 97] - 1;
    }

    private static boolean distinct(int[] arr) {
        for(int n : arr) {
            if(n != 0 && n != 1) return false;
        }
        return true;
    }

}
