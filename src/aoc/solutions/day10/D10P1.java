package aoc.solutions.day10;

import aoc.Util;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class D10P1 {

    public static void run() {

        Scanner sc = Util.readFile("src/aoc/solutions/day10/input.txt");

        List<String> lines = new ArrayList<>();
        while(sc.hasNextLine()) {
            String line = sc.nextLine();
            lines.add(line);
        }

        sc.close();

        System.out.println(solve(lines));

    }

    private static long solve(List<String> lines) {

        long result = 0L;

        long clockCycle = 1L;
        long timer = 0L;
        boolean incSet = false;
        long incSetVal = 0L;
        long x = 1L;
        int opCounter = 0;
        while(true) {

            if(timer == 0L) {
                if(incSet) {
                    x += incSetVal;
                    incSet = false;
                    incSetVal = 0L;
                }
                if(opCounter >= lines.size()) break;
                String[] op = lines.get(opCounter).split(" ");
                opCounter++;
                if(op[0].equals("addx")) {
                    incSet = true;
                    incSetVal = Long.parseLong(op[1]);
                    timer = 2L;
                }
            }

            if(clockCycle == 20L || (clockCycle - 20L) % 40L == 0L) {
                result += signalStrength(x, clockCycle);
            }

            clockCycle++;

            if(timer > 0L) timer--;

        }

        return result;

    }

    private static long signalStrength(long x, long clockCycle) {

        return clockCycle * x;

    }

}
