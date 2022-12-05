package aoc.solutions.day4;

import aoc.Util;

import java.util.Scanner;

public class D4P1 {

    public static void run() {

        Scanner sc = Util.readFile("src/aoc/solutions/day4/input.txt");

        int fullyContained = 0;
        while(sc.hasNextLine()) {
            String line = sc.nextLine();
            String[] split = line.split(",");
            fullyContained += isFullyContained(split[0], split[1]);
        }

        sc.close();

        System.out.println(fullyContained);

    }

    private static int isFullyContained(String r1, String r2) {

        String[] range1Split = r1.split("-");
        int range1Lower = Integer.parseInt(range1Split[0]);
        int range1Upper = Integer.parseInt(range1Split[1]);

        String[] range2Split = r2.split("-");
        int range2Lower = Integer.parseInt(range2Split[0]);
        int range2Upper = Integer.parseInt(range2Split[1]);

        return rFullyContained(range1Lower, range1Upper, range2Lower, range2Upper) ? 1 : 0;

    }

    private static boolean rFullyContained(int a, int b, int c, int d) {

        return (a >= c && b <= d) || (c >= a && d <= b);

    }

}
