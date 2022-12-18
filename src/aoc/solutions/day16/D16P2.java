package aoc.solutions.day16;

import aoc.Util;
import aoc.solutions.standard.Pair;

import java.util.*;

public class D16P2 {

    private static final Map<Pair<List<String>, List<String>>, Long> memory = new HashMap<>(500);

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

        long res = 0L;
        Pair<List<String>, List<String>> currBest = null;
        List<Pair<List<String>, List<String>>> combinations = getCombinations(nonZero, nonZero.size() / 2);
        for(Pair<List<String>, List<String>> combination : combinations) {
            List<String> v1Start = new ArrayList<>();
            v1Start.add("AA");
            List<String> v2Start = new ArrayList<>();
            v2Start.add("AA");
            long v1 = solve(0L, v1Start, combination.left(), distanceMatrix, valves);
            long v2 = solve(0L, v2Start, combination.right(), distanceMatrix, valves);
            if(v1 + v2 > res) {
                res = v1 + v2;
                currBest = combination;
            }
        }

        System.out.println(res);
        System.out.println(currBest);

    }

    private static List<Pair<List<String>, List<String>>> getCombinations(List<String> list, int maxCombinationSize) {

        return getCombinationsInternal(list, new ArrayList<>(), 0, maxCombinationSize);

        /*List<Pair<List<String>, List<String>>> res = new ArrayList<>();

        // 1 Combinations
        for(int i = 0; i < list.size(); i++) {
            List<String> left = new ArrayList<>();
            left.add(list.get(i));
            List<String> right = new ArrayList<>(list);
            right.remove(i);
            res.add(Pair.of(left, right));
        }

        // 2 Combinations
        for(int i = 0; i < list.size(); i++) {
            for(int j = i + 1; j < list.size(); j++) {
                List<String> left = new ArrayList<>();
                left.add(list.get(i));
                left.add(list.get(j));
                List<String> right = new ArrayList<>(list);
                right.remove(i);
                right.remove(j - 1);
                res.add(Pair.of(left, right));
            }
        }

        // 3 Combinations
        for(int i = 0; i < list.size(); i++) {
            for(int j = i + 1; j < list.size(); j++) {
                for(int k = j + 1; k < list.size(); k++) {
                    List<String> left = new ArrayList<>();
                    left.add(list.get(i));
                    left.add(list.get(j));
                    left.add(list.get(k));
                    List<String> right = new ArrayList<>(list);
                    right.remove(i);
                    right.remove(j - 1);
                    right.remove(k - 2);
                    res.add(Pair.of(left, right));
                }
            }
        }

        return res;*/

    }

    private static List<Pair<List<String>, List<String>>> getCombinationsInternal(List<String> list, List<String> currList, int currLevel, int maxLevel) {

        if(maxLevel - currLevel < 1) throw new IllegalStateException();
        if(maxLevel - currLevel == 1) {
            List<Pair<List<String>, List<String>>> res = new ArrayList<>();
            List<String> right = new ArrayList<>(list);
            right.removeAll(currList);
            for(String s : right) {
                List<String> left = new ArrayList<>(currList);
                left.add(s);
                List<String> rightActual = new ArrayList<>(right);
                rightActual.remove(s);
                res.add(Pair.of(left, rightActual));
            }
            return res;
        }

        List<Pair<List<String>, List<String>>> res = new ArrayList<>();
        List<String> right = new ArrayList<>(list);
        right.removeAll(currList);
        for(String s : right) {
            List<String> left = new ArrayList<>(currList);
            left.add(s);
            res.addAll(getCombinationsInternal(list, left, currLevel + 1, maxLevel));
        }
        return res;

    }

    private static long calc(List<String> currentPath, Map<String, Map<String, Integer>> distanceMatrix, Map<String, Valve> valves) {

        long res = 0L;

        long time = 1L;
        long c = 0L;
        String curr = currentPath.get(0);
        for(int i = 1; i < currentPath.size(); i++) {
            String next = currentPath.get(i);
            long dst = distanceMatrix.get(curr).get(next);
            if(time + dst >= 26) {
                res += (27 - time) * c;
                time = 27;
                break;
            } else {
                res += (dst + 1) * c;
                time += (dst + 1);
                c += valves.get(next).flowRate;
                curr = next;
            }
        }

        if(time <= 26) {
            res += (27 - time) * c;
        }

        return res;

    }

    private static long solve(long baseDst, List<String> currentPath, List<String> options, Map<String, Map<String, Integer>> distanceMatrix, Map<String, Valve> valves) {

        if(options.size() <= 0) {
            Pair<List<String>, List<String>> memkey = Pair.of(currentPath, options);
            if(currentPath.size() < 3 && memory.containsKey(memkey)) {
                return memory.get(memkey);
            }
            long res = calc(currentPath, distanceMatrix, valves);
            if(currentPath.size() < 3) memory.put(memkey, res);
            return calc(currentPath, distanceMatrix, valves);
        }

        long res = 0L;
        for(String option : options) {

            currentPath.add(option);

            long currDst = baseDst + distanceMatrix.get(currentPath.get(currentPath.size() - 2)).get(option);

            List<String> newOptions = listWithout(options, option);
            Pair<List<String>, List<String>> memkey = Pair.of(currentPath, newOptions);

            if(currDst >= 26) {
                if(currentPath.size() < 3 && memory.containsKey(memkey)) {
                    long memo = memory.get(memkey);
                    if(memo > res) res = memo;
                    currentPath.remove(currentPath.size() - 1);
                    continue;
                }
                long val = calc(currentPath, distanceMatrix, valves);
                if(currentPath.size() < 3) memory.put(memkey, res);
                if(val > res) res = val;
                currentPath.remove(currentPath.size() - 1);
                continue;
            }
            if(currentPath.size() < 3 && memory.containsKey(memkey)) {
                long memo = memory.get(memkey);
                if(memo > res) res = memo;
                currentPath.remove(currentPath.size() - 1);
                continue;
            }
            long val = solve(currDst, new ArrayList<>(currentPath), listWithout(options, option), distanceMatrix, valves);
            if(currentPath.size() < 3) memory.put(memkey, res);
            if(val > res) res = val;
            currentPath.remove(currentPath.size() - 1);
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
