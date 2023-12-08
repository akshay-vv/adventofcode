package com.avverma.aoc.day8.part2;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class Network {
    private static long navigate(List<String> input) {
        Set<String> starts = new HashSet<>();
        Set<String> finish = new HashSet<>();
        Map<String, String[]> networkmap = new HashMap<>();
        for (int i = 2; i < input.size(); i++) {
            // AAA = (BBB, CCC)
            String line = input.get(i);
            String[] nodeMap = line.split("=");

            String node = nodeMap[0].trim();
            networkmap.put(nodeMap[0].trim(), nodeMap[1].replace("(", "").replace(")", "").replace(" ", "").split(","));

            if (node.endsWith("A")) starts.add(node);
            if (node.endsWith("Z")) finish.add(node);
        }

        int[] directions = input.get(0).chars().map(c -> c-'L' == 0 ? 0 : 1).toArray();
        int nDirections = directions.length;


        Set<Long> allSteps = new HashSet<>();
        for (String s : starts) {
            String c = s;
            long steps = 0;
            while(!finish.contains(c)) {
                c = networkmap.get(c)[directions[(int)(steps++ % nDirections)]];
            }
            allSteps.add(steps);
        }
        return lcm(allSteps);
    }

    // https://stackoverflow.com/a/40531215
    public static long lcm(Set<Long> numbers) {
        return numbers.stream().reduce(1L, (x, y) -> x * (y / gcd(x, y)));
    }

    private static long gcd(long x, long y) {
        return (y == 0) ? x : gcd(y, x % y);
    }




    static List<String> readInput() {
//        try(BufferedReader br = new BufferedReader(new FileReader("src/main/java/com/avverma/aoc/day8/part1/example_input.txt"))) {
        try(BufferedReader br = new BufferedReader(new FileReader("src/main/java/com/avverma/aoc/day8/input.txt"))) {
            List<String> input = new ArrayList<>();
            String line = br.readLine();
            while(line != null) {
                input.add(line);
                line = br.readLine();
            }
            return input;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void main(String[] args) {
        List<String> input = readInput();
        System.out.println(navigate(input));
    }
}
