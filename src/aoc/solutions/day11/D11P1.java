package aoc.solutions.day11;

import aoc.Util;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class D11P1 {

    private static class Operation {

        enum OperationType {
            ADD,
            MUL,
            SQUARE
        }

        OperationType operationType;
        int k;

        Operation(OperationType operationType, int k) {
            this.operationType = operationType;
            this.k = k;
        }

        static Operation parseOperation(String line) {

            String op = line.split("= ")[1];

            if(op.equals("old * old")) {
                return new Operation(OperationType.SQUARE, 0);
            } else if(op.contains("*")) {
                int k = Integer.parseInt(op.split("\\* ")[1]);
                return new Operation(OperationType.MUL, k);
            } else if(op.contains("+")) {
                int k = Integer.parseInt(op.split("\\+ ")[1]);
                return new Operation(OperationType.ADD, k);
            }

            throw new IllegalArgumentException();

        }

        int execute(int old) {
            if(operationType == OperationType.ADD) {
                return old + k;
            } else if(operationType == OperationType.MUL) {
                return old * k;
            } else if(operationType == OperationType.SQUARE) {
                return old * old;
            }
            throw new IllegalStateException();
        }

    }

    private static class Monkey {

        List<Integer> items;
        Operation operation;
        int divTest;
        int trueMonkey;
        int falseMonkey;

        int throwCounter;

        Monkey() {
            items = new ArrayList<>();
            throwCounter = 0;
        }

        static Monkey parseMonkey(List<String> lines) {

            Monkey monkey = new Monkey();

            monkey.items.addAll(Util.getInts(lines.get(1)));
            monkey.operation = Operation.parseOperation(lines.get(2));
            monkey.divTest = Util.getInts(lines.get(3)).get(0);
            monkey.trueMonkey = Util.getInts(lines.get(4)).get(0);
            monkey.falseMonkey = Util.getInts(lines.get(5)).get(0);

            return monkey;

        }

    }

    public static void run() {

        Scanner sc = Util.readFile("src/aoc/solutions/day11/input.txt");

        List<List<String>> lines = new ArrayList<>();
        List<String> curr = new ArrayList<>();
        while(sc.hasNextLine()) {
            String line = sc.nextLine();
            if(line.equals("")) {
                lines.add(curr);
                curr = new ArrayList<>();
            } else {
                curr.add(line);
            }
        }

        if(curr.size() > 0) {
            lines.add(curr);
        }

        sc.close();

        List<Monkey> monkeys = new ArrayList<>();
        for(List<String> ls : lines) {
            monkeys.add(Monkey.parseMonkey(ls));
        }

        System.out.println(solve(monkeys));

    }

    private static int solve(List<Monkey> monkeys) {

        for(int round = 1; round <= 20; round++) {

            for(Monkey m : monkeys) {

                for(int item : m.items) {
                    int newItem = m.operation.execute(item) / 3;
                    if(newItem % m.divTest == 0) {
                        monkeys.get(m.trueMonkey).items.add(newItem);
                    } else {
                        monkeys.get(m.falseMonkey).items.add(newItem);
                    }
                    m.throwCounter++;
                }

                m.items = new ArrayList<>();

            }

        }

        monkeys.sort((m1, m2) -> Integer.compare(m2.throwCounter, m1.throwCounter));
        return monkeys.get(0).throwCounter * monkeys.get(1).throwCounter;

    }

}
