package aoc.solutions.day5;

import aoc.Util;
import aoc.solutions.standard.Stack;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class D5P2 {

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

    public static void run() {

        Scanner sc = Util.readFile("src/aoc/solutions/day5/input.txt");

        int mode = 0;
        List<List<Character>> stacks = new ArrayList<>();
        List<Operation> operations = new ArrayList<>();
        while(sc.hasNextLine()) {

            String s = sc.nextLine();
            char[] line = s.toCharArray();

            if(mode == 0) {

                for(int i = 1; i < line.length; i += 4) {
                    char c = line[i];
                    if(c >= 'A' && c <= 'Z') {
                        List<Character> list = new ArrayList<>();
                        list.add(c);
                        stacks.add(list);
                    } else {
                        stacks.add(new ArrayList<>());
                    }
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
                            stacks.get(stackCounter).add(c);
                        }
                        stackCounter++;
                    }

                }

            } else if(mode == 2) {

                if(!s.startsWith("move")) continue;

                String[] split = s.split(" ");
                operations.add(new Operation(Integer.parseInt(split[1]), Integer.parseInt(split[3]) - 1, Integer.parseInt(split[5]) - 1));

            }

        }

        sc.close();

        List<Stack<Character>> input = new ArrayList<>();
        for(List<Character> list : stacks) {
            input.add(Stack.fromListReverse(list));
        }

        String code = solve(input, operations);

        System.out.println(code);

    }

    private static String solve(List<Stack<Character>> stacks, List<Operation> operations) {

        for(Operation operation : operations) {
            stacks.get(operation.to).pushAllReverse(stacks.get(operation.from).pop(operation.amount));
        }

        StringBuilder sb = new StringBuilder();
        for(Stack<Character> stack : stacks) {
            Character c = stack.pop();
            if(c == null) {
                sb.append(" ");
            } else {
                sb.append(c);
            }
        }

        return sb.toString();

    }

}
