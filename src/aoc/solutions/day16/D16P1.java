package aoc.solutions.day16;

import aoc.Util;
import aoc.solutions.standard.Pair;

import java.util.*;

public class D16P1 {

    private static class Valve {

        String name;
        long flowRate;
        List<String> leadsTo;

        Valve(String name, long flowRate, List<String> leadsTo) {
            this.name = name;
            this.flowRate = flowRate;
            this.leadsTo = leadsTo;
        }

        @Override
        public String toString() {

            return "[Valve: \n\tname = \"" + this.name +
                    "\",\n\tflowRate = " + this.flowRate +
                    ",\n\tleadsTo = " + this.leadsTo +
                    "\n]";

        }

        @Override
        public boolean equals(Object o) {

            if(this == o) return true;

            if(o instanceof Valve v) {
                return this.name.equals(v.name);
            }

            return false;

        }

        @Override
        public int hashCode() {
            return Objects.hash(name);
        }

    }

    public static void run() {

        List<String> lines = Util.inputLines("day16");

        Map<String, Valve> valves = new HashMap<>();
        for(String line : lines) {
            if(line.length() <= 0) continue;
            String[] split = line.split(" ");
            String name = split[1];
            long flowRate = Long.parseLong(split[4].split("=")[1].split(";")[0]);
            List<String> leadsTo = new ArrayList<>();
            for(int i = 9; i < split.length; i++) {
                leadsTo.add(split[i].replaceAll(",", ""));
            }
            valves.put(name, new Valve(name, flowRate, leadsTo));
        }

        Map<String, Map<String, Integer>> distanceMatrix = new HashMap<>();
        for(Map.Entry<String, Valve> e : valves.entrySet()) {
            distanceMatrix.put(e.getKey(), distanceRow(e.getKey(), valves));
        }

        List<String> nonZero = new ArrayList<>();
        for(Map.Entry<String, Valve> e : valves.entrySet()) {
            if(e.getValue().flowRate > 0L) nonZero.add(e.getKey());
        }

        System.out.println(solve("AA", 0L, new ArrayList<>(), nonZero, distanceMatrix, valves));

    }

    private static long calc(List<String> currentPath, Map<String, Map<String, Integer>> distanceMatrix, Map<String, Valve> valves) {

        long res = 0L;

        long time = 1L;
        long c = 0L;
        String curr = currentPath.get(0);
        for(int i = 1; i < currentPath.size(); i++) {
            String next = currentPath.get(i);
            long dst = distanceMatrix.get(curr).get(next);
            if(time + dst >= 30) {
                res += (31 - time) * c;
                time = 31;
                break;
            } else {
                res += (dst + 1) * c;
                time += (dst + 1);
                c += valves.get(next).flowRate;
                curr = next;
            }
        }

        if(time <= 30) {
            res += (31 - time) * c;
        }

        return res;

    }

    private static long solve(String start, long baseDst, List<String> currentPath, List<String> options, Map<String, Map<String, Integer>> distanceMatrix, Map<String, Valve> valves) {

        currentPath.add(start);
        if(options.size() <= 0) return calc(currentPath, distanceMatrix, valves);

        long res = 0L;
        for(String option : options) {
            long currDst = baseDst + distanceMatrix.get(start).get(option);
            if(currDst >= 30) {
                long val = calc(currentPath, distanceMatrix, valves);
                if(val > res) res = val;
                continue;
            }
            long val = solve(option, currDst, new ArrayList<>(currentPath), listWithout(options, option), distanceMatrix, valves);
            if(val > res) res = val;
        }

        return res;

    }

    private static List<String> listWithout(List<String> list, String remove) {

        List<String> res = new ArrayList<>(list);
        res.remove(remove);

        return res;

    }

    private static long dst(List<String> path, Map<String, Map<String, Integer>> distanceMatrix) {

        long res = 0L;
        String prev = path.get(0);
        for(int i = 1; i < path.size(); i++) {
            String valve = path.get(i);
            res += distanceMatrix.get(prev).get(valve);
            prev = valve;
        }

        return res;

    }

    private static Map<String, Integer> distanceRow(String startName, Map<String, Valve> valves) {

        Map<String, Integer> map = new HashMap<>();
        Queue<Pair<Integer, String>> queue = new LinkedList<>();
        Set<String> visited = new HashSet<>();
        queue.add(Pair.of(0, startName));
        while(queue.size() > 0) {

            Pair<Integer, String> curr = queue.poll();
            if(visited.contains(curr.right())) continue;

            visited.add(curr.right());
            map.put(curr.right(), curr.left());

            Valve cValve = valves.get(curr.right());
            for(String adj : cValve.leadsTo) {
                queue.add(Pair.of(curr.left() + 1, adj));
            }

        }

        return map;

    }

}
