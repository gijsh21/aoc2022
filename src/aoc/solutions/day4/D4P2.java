package aoc.solutions.day4;

import aoc.Util;

import java.util.Scanner;

public class D4P2 {

    public static void run() {

        Scanner sc = Util.readFile("src/aoc/solutions/day4/input.txt");

        int overlaps = 0;
        while(sc.hasNextLine()) {
            String line = sc.nextLine();
            String[] split = line.split(",");
            overlaps += isOverlap(split[0], split[1]);
        }

        sc.close();

        System.out.println(overlaps);

    }

    private static int isOverlap(String r1, String r2) {

        String[] range1Split = r1.split("-");
        int range1Lower = Integer.parseInt(range1Split[0]);
        int range1Upper = Integer.parseInt(range1Split[1]);

        String[] range2Split = r2.split("-");
        int range2Lower = Integer.parseInt(range2Split[0]);
        int range2Upper = Integer.parseInt(range2Split[1]);

        return rOverlaps(range1Lower, range1Upper, range2Lower, range2Upper) ? 1 : 0;

    }

    private static boolean rOverlaps(int a, int b, int c, int d) {

        return (d >= a && c <= b);

    }

}
