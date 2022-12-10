package aoc.solutions.day10;

import aoc.Util;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class D10P2 {

    public static void run() {

        Scanner sc = Util.readFile("src/aoc/solutions/day10/input.txt");

        List<String> lines = new ArrayList<>();
        while(sc.hasNextLine()) {
            String line = sc.nextLine();
            lines.add(line);
        }

        sc.close();

        solve(lines);

    }

    private static void solve(List<String> lines) {

        StringBuilder currentLine = new StringBuilder();

        long clockCycle = 0L;
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

            long crtPos = clockCycle % 40L;

            if(clockCycle > 0L && crtPos == 0L) {
                System.out.println(currentLine);
                currentLine = new StringBuilder();
            }

            if((x - crtPos) >= -1L && (x - crtPos) <= 1L) {
                currentLine.append("#");
            } else {
                currentLine.append(".");
            }

            clockCycle++;
            if(timer > 0L) timer--;

        }

        System.out.println(currentLine);

    }

}
