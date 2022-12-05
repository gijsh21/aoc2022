package aoc.solutions.day1;

import aoc.Util;

import java.util.PriorityQueue;
import java.util.Scanner;

public class D1P2 {

    public static void run() {

        Scanner sc = Util.readFile("src/aoc/solutions/day1/input.txt");

        PriorityQueue<Integer> topElves = new PriorityQueue<>(10, (a, b) -> Integer.compare(b, a));
        int currCalories = 0;
        while(sc.hasNextLine()) {

            String line = sc.nextLine();

            if(line.equals("")) {
                topElves.add(currCalories);
                currCalories = 0;
            } else {
                currCalories += Integer.parseInt(line);
            }

        }

        sc.close();

        int res = topElves.poll() + topElves.poll() + topElves.poll();
        System.out.println(res);

    }

}
