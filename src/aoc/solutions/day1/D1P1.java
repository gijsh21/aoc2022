package aoc.solutions.day1;

import aoc.Util;

import java.util.Scanner;

public class D1P1 {

    public static void run() {

        Scanner sc = Util.readFile("src/aoc/solutions/day1/input.txt");

        int maxCalories = 0;
        int currCalories = 0;
        while(sc.hasNextLine()) {

            String line = sc.nextLine();

            if(line.equals("")) {
                if(currCalories > maxCalories) maxCalories = currCalories;
                currCalories = 0;
            } else {
                currCalories += Integer.parseInt(line);
            }

        }

        sc.close();

        System.out.println(maxCalories);

    }

}
