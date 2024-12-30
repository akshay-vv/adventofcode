package com.avverma._2024.day11.part2;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.*;
import java.util.stream.Collectors;

public class PlutonianPebbles {

    List<Long> stones;

    record StoneVal(long stone, int blink) {}
    Map<StoneVal, Long> memo = new HashMap<>();
    long solve() {
        long numWays = 0;
        for (Long stone : stones) {
            numWays += numWays(stone, 75);
        }

        return numWays;
    }

    long numWays(long stone, int nBlinks) {
        StoneVal stoneVal = new StoneVal(stone, nBlinks);
        if (memo.containsKey(stoneVal))  return memo.get(stoneVal);

        if (nBlinks == 0) {
            return 1;
        }

        String sStone = Long.toString(stone);
        long res = 0;
        if (stone == 0) {
             res = numWays(1, nBlinks - 1);
        }  else if (sStone.length() % 2 == 0) {
             res = numWays(Long.parseLong(sStone.substring(0, sStone.length()/ 2)), nBlinks-1) +
                     numWays(Long.parseLong(sStone.substring(sStone.length()/ 2)), nBlinks-1);
        } else {
             res = numWays(stone * 2024, nBlinks -1);
        }

        memo.put(stoneVal, res);
        return res;
    }

    public static void main(String[] args) {
        PlutonianPebbles pp = new PlutonianPebbles();
        pp.parseInput();

        System.out.println("\n" + pp.solve());

    }

    private void parseInput() {
        try (BufferedReader br = new BufferedReader(new FileReader("aoc/src/main/java/com/avverma/_2024/day11/input.txt"))) {
            String line = br.readLine();
            stones = Arrays.stream(line.split(" ")).mapToLong(Long::parseLong).boxed().collect(Collectors.toList());
        } catch (Exception e) {
            System.err.println(e);
            throw new RuntimeException(e);
        }
    }
}