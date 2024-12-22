package com.avverma._2024.day7.part2;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

public class BridgeRepair {

    record Pair<T, U> (T first, U second){}

    List<Pair<Long, long[]>> equations;

    long solve() {
        long total = 0;
        for(Pair<Long, long[]> equation: equations) {
            if (isValid(equation.first, equation.second)) {
                total += equation.first;
            }
        }
        return total;
    }

    boolean isValid(long testValue, long[] numbers) {
        return helper(testValue, numbers, 1, numbers[0]);
    }

    boolean helper(long testValue, long[] numbers, int curr, long total) {
        if (curr == numbers.length) {
            // base case
            return testValue == total;
        }

        return helper(testValue, numbers, curr+1, total+numbers[curr]) ||
                helper(testValue, numbers, curr+1, total*numbers[curr]) ||
                helper(testValue, numbers, curr+1, Long.parseLong(String.format("%d%d", total, numbers[curr])));
    }

    public static void main(String[] args) {
        BridgeRepair pq = new BridgeRepair();
        pq.parseInput();

        System.out.println("\n" + pq.solve());

    }

    private void parseInput() {
        try (BufferedReader br = new BufferedReader(new FileReader("aoc/src/main/java/com/avverma/_2024/day7/input.txt"))) {
            String line = br.readLine();
            equations = new ArrayList<>();
            while (line != null) {
                String[] caliberation = line.split(":");
                equations.add(new Pair<Long, long[]>(
                        Long.parseLong(caliberation[0]),
                        Stream.of(caliberation[1].trim().split(" "))
                                .mapToLong(Long::parseLong)
                                .toArray())
                );
                line = br.readLine();
            }
        } catch (Exception e) {
            System.err.println(e);
            throw new RuntimeException(e);
        }
    }
}
