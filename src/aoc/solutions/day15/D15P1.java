package aoc.solutions.day15;

import aoc.Util;
import aoc.solutions.standard.Pair;

import java.util.*;

public class D15P1 {

    private static class SensorAndBeacon {

        long sensorX;
        long sensorY;
        long beaconX;
        long beaconY;

        SensorAndBeacon(long sensorX, long sensorY, long beaconX, long beaconY) {
            this.sensorX = sensorX;
            this.sensorY = sensorY;
            this.beaconX = beaconX;
            this.beaconY = beaconY;
        }

        long manhattanDelta() {
            return Math.abs(beaconX - sensorX) + Math.abs(beaconY - sensorY);
        }

    }

    public static void run() {

        List<String> lines = Util.inputLines("day15");

        List<SensorAndBeacon> snb = new ArrayList<>();
        for(String line : lines) {
            if(line.length() <= 0) continue;
            String[] splitOnEquals = line.split("=");
            long sensorX = Long.parseLong(splitOnEquals[1].split(",")[0]);
            long sensorY = Long.parseLong(splitOnEquals[2].split(":")[0]);
            long beaconX = Long.parseLong(splitOnEquals[3].split(",")[0]);
            long beaconY = Long.parseLong(splitOnEquals[4]);
            snb.add(new SensorAndBeacon(sensorX, sensorY, beaconX, beaconY));
        }

        System.out.println(solve(snb));

    }

    private static long solve(List<SensorAndBeacon> snb) {

        List<Pair<Long, Long>> ranges = getExcludedRanges(snb, 2000000L);
        ranges.sort(Comparator.comparingLong(Pair::left));

        List<Pair<Long, Long>> distinctRanges = new ArrayList<>();
        Pair<Long, Long> left = ranges.get(0);
        for(int i = 1; i < ranges.size(); i++) {

            Pair<Long, Long> right = ranges.get(i);
            if(right.left() > left.right()) {
                distinctRanges.add(left);
                left = right;
            } else {
                if(right.right() > left.right()) left.setRight(right.right());
            }

        }
        distinctRanges.add(left);

        long res = 0L;
        for(Pair<Long, Long> range : distinctRanges) {
            res += (range.right() - range.left() + 1);
        }

        res -= uniqueBeaconsOnLine(snb, 2000000L);

        return res;

    }

    private static List<Pair<Long, Long>> getExcludedRanges(List<SensorAndBeacon> snb, long y) {

        List<Pair<Long, Long>> res = new ArrayList<>();

        for(SensorAndBeacon sensorAndBeacon : snb) {

            long d = sensorAndBeacon.manhattanDelta();
            long distToY = Math.abs(y - sensorAndBeacon.sensorY);
            long extendTo = d - distToY;
            if(extendTo < 0L) continue;
            Pair<Long, Long> exRange = Pair.of(sensorAndBeacon.sensorX - extendTo, sensorAndBeacon.sensorX + extendTo);
            res.add(exRange);

        }

        return res;

    }

    private static long uniqueBeaconsOnLine(List<SensorAndBeacon> snb, long y) {

        long res = 0L;

        Set<Long> discovered = new HashSet<>();
        for(SensorAndBeacon sensorAndBeacon : snb) {

            if(sensorAndBeacon.beaconY == y) {
                if(!discovered.contains(sensorAndBeacon.beaconX)) {
                    discovered.add(sensorAndBeacon.beaconX);
                    res++;
                }
            }

        }

        return res;

    }

}
