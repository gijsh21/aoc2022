package aoc.solutions.day15;

import aoc.Util;
import aoc.solutions.standard.Pair;

import java.util.*;

public class D15P2 {

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

        List<String> lines = Util.testLines("day15");

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

        System.out.println(solve(snb, 20L));

    }

    private static long solve(List<SensorAndBeacon> snb, long maxCoord) {

        long bx = -1L;
        long by = -1L;

        Set<Pair<Long, Long>> knownBeaconLocations = getKnownBeaconLocations(snb);

        for(long currLine = 0; currLine <= maxCoord; currLine++) {

            List<Pair<Long, Long>> ranges = getExcludedRanges(snb, currLine);
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

            distinctRanges = distinctRanges.stream()
                    .filter((r) -> r.right() >= 0L && r.left() <= maxCoord)
                    .toList();


            Pair<Long, Long> currRange = distinctRanges.get(0);
            int ctr = 0;
            long curr = 0L;
            while(curr <= maxCoord) {

                if(currRange.left() > curr) {

                } else if(currRange.left() == curr) {

                } else {

                }

            }

            List<Pair<Long, Long>> possibleRegions; //????
            for(Pair<Long, Long> possibleRegion : possibleRegions) {
                Long low = possibleRegion.left();
                while(low <= possibleRegion.right()) {

                    low++;
                }
            }

        }

        return bx * 4000000L + by;

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

    private static Set<Pair<Long, Long>> getKnownBeaconLocations(List<SensorAndBeacon> snb) {

        Set<Pair<Long, Long>> res = new HashSet<>();

        for(SensorAndBeacon sensorAndBeacon : snb) {

            res.add(Pair.of(sensorAndBeacon.beaconX, sensorAndBeacon.beaconY));

        }

        return res;

    }

}
