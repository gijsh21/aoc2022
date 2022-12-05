package aoc.solutions.day2;

import aoc.Util;

import java.util.Scanner;

public class D2P1 {

    public static void run() {

        Scanner sc = Util.readFile("src/aoc/solutions/day2/input.txt");

        int totalScore = 0;
        while(sc.hasNextLine()) {
            String line = sc.nextLine();

            String[] split = line.split(" ");

            int opponentChoice = -1;
            int playerChoice = -1;
            if(split[0].equals("A")) {
                opponentChoice = 1;
            } else if(split[0].equals("B")) {
                opponentChoice = 2;
            } else if(split[0].equals("C")) {
                opponentChoice = 3;
            }
            if(split[1].equals("X")) {
                playerChoice = 1;
            } else if(split[1].equals("Y")) {
                playerChoice = 2;
            } else if(split[1].equals("Z")) {
                playerChoice = 3;
            }

            totalScore += playerChoice;
            if(playerChoice == 1) {
                if(opponentChoice == 1) totalScore += 3;
                if(opponentChoice == 3) totalScore += 6;
            } else if(playerChoice == 2) {
                if(opponentChoice == 1) totalScore += 6;
                if(opponentChoice == 2) totalScore += 3;
            } else if(playerChoice == 3) {
                if(opponentChoice == 2) totalScore += 6;
                if(opponentChoice == 3) totalScore += 3;
            }

        }

        System.out.println(totalScore);

        sc.close();

    }

}
