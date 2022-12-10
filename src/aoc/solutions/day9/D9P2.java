package aoc.solutions.day9;

import aoc.Util;

import java.util.*;

public class D9P2 {

    private static final int TAIL_SECTION_AMOUNT = 9;

    private static class GridPosition {

        long x;
        long y;

        GridPosition(long x, long y) {
            this.x = x;
            this.y = y;
        }

        void moveX(long movX) {
            x += movX;
        }

        void moveY(long movY) {
            y += movY;
        }

        GridPosition copy() {
            return new GridPosition(this.x, this.y);
        }

        @Override
        public boolean equals(Object o) {

            if(this == o) return true;

            if(o instanceof GridPosition gp) {
                return this.x == gp.x && this.y == gp.y;
            }

            return false;

        }

        @Override
        public int hashCode() {
            return Objects.hash(x, y);
        }

    }

    public static void run() {

        Scanner sc = Util.readFile("src/aoc/solutions/day9/input.txt");

        List<String> lines = new ArrayList<>();
        while(sc.hasNextLine()) {
            String line = sc.nextLine();
            lines.add(line);
        }

        sc.close();

        System.out.println(solve(lines));

    }

    private static long solve(List<String> lines) {

        Set<GridPosition> distinctTailPos = new HashSet<>();

        GridPosition head = new GridPosition(0L, 0L);
        List<GridPosition> tailSections = new ArrayList<>();
        for(int i = 0; i < TAIL_SECTION_AMOUNT; i++) {
            tailSections.add(new GridPosition(0L, 0L));
        }
        distinctTailPos.add(tailSections.get(tailSections.size() - 1).copy());
        for(String op : lines) {

            String[] split = op.split(" ");
            String direction = split[0];
            long amount = Long.parseLong(split[1]);

            long movX = 0L;
            long movY = 0L;
            if(direction.equalsIgnoreCase("U")) {
                movY = 1L;
            } else if(direction.equalsIgnoreCase("D")) {
                movY = -1L;
            } else if(direction.equalsIgnoreCase("L")) {
                movX = -1L;
            } else if(direction.equalsIgnoreCase("R")) {
                movX = 1L;
            }

            for(; amount > 0; amount--) {
                head.moveX(movX);
                head.moveY(movY);
                setTailPos(tailSections, head);
                distinctTailPos.add(tailSections.get(tailSections.size() - 1).copy());
            }

        }

        return distinctTailPos.size();

    }

    private static void setTailPos(List<GridPosition> tailSections, GridPosition head) {

        for(int i = 0; i < tailSections.size(); i++) {

            GridPosition tail = tailSections.get(i);

            long deltaX = head.x - tail.x;
            long deltaY = head.y - tail.y;
            if(deltaX >= -1L && deltaX <= 1L && deltaY >= -1L && deltaY <= 1L) return;

            if(deltaY == 0) {
                tail.moveX(deltaX > 0L ? (1L) : (-1L));
            } else if(deltaX == 0) {
                tail.moveY(deltaY > 0L ? (1L) : (-1L));
            } else {
                if(deltaX > 0L && deltaY > 0L) {
                    tail.moveX(1L);
                    tail.moveY(1L);
                } else if(deltaX > 0L && deltaY < 0L) {
                    tail.moveX(1L);
                    tail.moveY(-1L);
                } else if(deltaX < 0L && deltaY > 0L) {
                    tail.moveX(-1L);
                    tail.moveY(1L);
                } else if(deltaX < 0L && deltaY < 0L) {
                    tail.moveX(-1L);
                    tail.moveY(-1L);
                }
            }

            deltaX = head.x - tail.x;
            deltaY = head.y - tail.y;
            head = tail;
            if(deltaX >= -1L && deltaX <= 1L && deltaY >= -1L && deltaY <= 1L) continue;

            throw new IllegalStateException();

        }

    }

}
