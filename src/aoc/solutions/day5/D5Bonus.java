package aoc.solutions.day5;

import aoc.Util;
import aoc.solutions.standard.Stack;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class D5Bonus {

    private static class Operation {

        int amount;
        int from;
        int to;

        Operation(int amount, int from, int to) {
            this.amount = amount;
            this.from = from;
            this.to = to;
        }

    }

    private static class Crate {

        char type;
        int idx;

        Crate(char type, int idx) {
            this.type = type;
            this.idx = idx;
        }

    }

    public static void run(String desiredText) {

        Scanner sc = Util.readFile("src/aoc/solutions/day5/input.txt");

        int mode = 0;
        int lCtr = 200;
        List<List<Crate>> stacks = new ArrayList<>();
        List<Operation> operations = new ArrayList<>();
        while(sc.hasNextLine()) {

            String s = sc.nextLine();
            char[] line = s.toCharArray();

            if(mode == 0) {

                int ctr = 1;
                for(int i = 1; i < line.length; i += 4) {
                    char c = line[i];
                    if(c >= 'A' && c <= 'Z') {
                        List<Crate> list = new ArrayList<>();
                        list.add(new Crate(c, 100 + ctr));
                        stacks.add(list);
                    } else {
                        stacks.add(new ArrayList<>());
                    }
                    ctr++;
                }

                mode = 1;

            } else if(mode == 1) {

                if(s.equals("") || s.charAt(1) == '1') {
                    mode = 2;
                } else {

                    int stackCounter = 0;
                    for(int i = 1; i < line.length; i += 4) {
                        char c = line[i];
                        if(c >= 'A' && c <= 'Z') {
                            stacks.get(stackCounter).add(new Crate(c, lCtr + stackCounter + 1));
                        }
                        stackCounter++;
                    }

                    lCtr += 100;

                }

            } else if(mode == 2) {

                if(!s.startsWith("move")) continue;

                String[] split = s.split(" ");
                operations.add(new Operation(Integer.parseInt(split[1]), Integer.parseInt(split[3]) - 1, Integer.parseInt(split[5]) - 1));

            }

        }

        sc.close();

        List<Stack<Crate>> input = new ArrayList<>();
        for(List<Crate> list : stacks) {
            input.add(Stack.fromListReverse(list));
        }

        List<Crate> topCrates = solve(input, operations);

        if(topCrates.size() != desiredText.length()) throw new IllegalArgumentException();

        createFile(topCrates, desiredText);

    }

    private static List<Crate> solve(List<Stack<Crate>> stacks, List<Operation> operations) {

        for(Operation operation : operations) {
            stacks.get(operation.to).pushAllReverse(stacks.get(operation.from).pop(operation.amount));
        }

        List<Crate> result = new ArrayList<>();
        for(Stack<Crate> stack : stacks) {
            result.add(stack.pop());
        }

        return result;

    }

    private static void createFile(List<Crate> topCrates, String desiredText) {

        Path src = Paths.get("src/aoc/solutions/day5/input.txt");
        Path target = Paths.get("src/aoc/solutions/day5/output.txt");
        try {
            Files.copy(src, target);
        } catch(IOException e) {
            e.printStackTrace();
        }

        try {
            List<String> lines = Files.readAllLines(target);
            int charCtr = 0;
            for(Crate crate : topCrates) {
                int lineNo = crate.idx / 100;
                int stackNo = crate.idx % 100;
                String line = lines.get(lineNo - 1);
                StringBuilder sb = new StringBuilder(line);
                sb.setCharAt((stackNo - 1) * 4 + 1, desiredText.charAt(charCtr));
                lines.set(lineNo - 1, sb.toString());
                charCtr++;
            }
            Files.write(target, lines);
        } catch(IOException e) {
            e.printStackTrace();
        }

    }

}
