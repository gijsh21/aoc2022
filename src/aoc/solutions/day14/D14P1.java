package aoc.solutions.day14;

import aoc.Util;

import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

public class D14P1 {

    private static class Point {

        int x;
        int y;

        Point(int x, int y) {
            this.x = x;
            this.y = y;
        }

        Point copy() {
            return new Point(x, y);
        }

        static Point of(String p) {
            String[] coord = p.split(",");
            if(coord.length != 2) throw new IllegalArgumentException();
            return new Point(Integer.parseInt(coord[0]), Integer.parseInt(coord[1]));
        }

        @Override
        public boolean equals(Object o) {

            if(this == o) return true;

            if(o instanceof Point p) {
                return this.x == p.x && this.y == p.y;
            }

            return false;

        }

        @Override
        public int hashCode() {
            return Objects.hash(x, y);
        }

    }

    public static void run() {

        List<String> lines = Util.inputLines("day14");

        Set<Point> blockedPoints = new HashSet<>();
        int maxY = -1;
        for(String line : lines) {
            String[] split = line.split(" -> ");
            Point prev = null;
            for(String point : split) {
                Point p = Point.of(point);
                if(p.y > maxY) maxY = p.y;
                if(prev == null) {
                    blockedPoints.add(p);
                } else {
                    if(p.x != prev.x) {
                        int max;
                        int min;
                        int y = p.y;
                        if(p.x > prev.x) {
                            max = p.x;
                            min = prev.x;
                        } else {
                            max = prev.x;
                            min = p.x;
                        }
                        while(max >= min) {
                            blockedPoints.add(new Point(max, y));
                            max--;
                        }
                    } else if(p.y != prev.y) {
                        int max;
                        int min;
                        int x = p.x;
                        if(p.y > prev.y) {
                            max = p.y;
                            min = prev.y;
                        } else {
                            max = prev.y;
                            min = p.y;
                        }
                        while(max >= min) {
                            blockedPoints.add(new Point(x, max));
                            max--;
                        }
                    }
                }
                prev = p;
            }
        }

        System.out.println(simulate(blockedPoints, maxY));

    }

    private static int simulate(Set<Point> blocked, int maxY) {

        Point currSandPos = new Point(500, 0);
        int sandCount = 0;
        while(true) {

            boolean abyssFlag = false;
            while(true) {

                if(!blocked.contains(new Point(currSandPos.x, currSandPos.y + 1))) {
                    currSandPos.y++;
                } else if(!blocked.contains(new Point(currSandPos.x - 1, currSandPos.y + 1))) {
                    currSandPos.x--;
                    currSandPos.y++;
                } else if(!blocked.contains(new Point(currSandPos.x + 1, currSandPos.y + 1))) {
                    currSandPos.x++;
                    currSandPos.y++;
                } else {
                    break;
                }

                if(currSandPos.y > maxY) {
                    abyssFlag = true;
                    break;
                }

            }

            if(abyssFlag) {
                break;
            }

            blocked.add(currSandPos);
            currSandPos = new Point(500, 0);
            sandCount++;

        }

        return sandCount;

    }

}
