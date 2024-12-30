package com.avverma._2024.day11.part1;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.*;
import java.util.stream.Collectors;

public class PlutonianPebbles {

    List<Long> stones;
    Rule rule1 = new Rule1Number0();
    Rule rule2 = new Rule2EvenNumbers();
    Rule rule3 = new Rule3TwentyTwentyFour();

    long solve() {
        for (long blink = 0; blink < 25; blink++) {
            List<Long> stonesCopy = new ArrayList<>();
            for (int i=0; i < stones.size(); i++) {
                long stoneVal = stones.get(i);
                Rule rule = matchRule(stoneVal);
                rule.apply(stonesCopy, stoneVal);
            }
            stones = stonesCopy;
        }
        return stones.size();
    }

    Rule matchRule(long stoneVal) {
        if (stoneVal == 0) {
            return rule1;
        }
        long nDigits = 0;
        while(stoneVal > 0) {
            stoneVal /= 10;
            nDigits++;
        }

        if (nDigits % 2 == 0) {
            return rule2;
        }

        return rule3;
    }

    @FunctionalInterface
    public interface Rule {
        void apply(List<Long> stones, long val);
    }

    public class Rule1Number0 implements Rule {
        public void apply(List<Long> stones, long val) {
            stones.add(1L);
        }
    }

    public class Rule2EvenNumbers implements Rule {
        public void apply(List<Long> stones, long val) {
            String sVal = Long.toString(val);
            stones.add(Long.parseLong(sVal.substring(0, sVal.length() / 2)));
            stones.add(Long.parseLong(sVal.substring(sVal.length() / 2 )));
        }
    }

    public class Rule3TwentyTwentyFour implements Rule {
        public void apply(List<Long> stones, long val) {
            stones.add(val * 2024);
        }
    }

    public static void main(String[] args) {
        PlutonianPebbles pp = new PlutonianPebbles();
        pp.parseInput();

        System.out.println("\n" + pp.solve());

    }

    private void parseInput() {
        try (BufferedReader br = new BufferedReader(new FileReader("aoc/src/main/java/com/avverma/_2024/day11/example_input.txt"))) {
            String line = br.readLine();
            stones = Arrays.stream(line.split(" ")).mapToLong(Long::parseLong).boxed().collect(Collectors.toList());
        } catch (Exception e) {
            System.err.println(e);
            throw new RuntimeException(e);
        }
    }
}