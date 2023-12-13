package com.avverma.aoc.day12;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class HotSprings {
    static List<String> readInput() {
        try(BufferedReader br = new BufferedReader(new FileReader("src/main/java/com/avverma/aoc/day12/input.txt"))) {
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
        System.out.println(numberOfWays(input));
    }

    private static long numberOfWays(List<String> input) {
        long nWays = 0;
        for (String line: input) {
            String[] ln = line.split(" ");
            String condition = ln[0];
            List<Integer> groups = Arrays.stream(ln[1].split(",")).map(Integer::parseInt).toList();
            nWays += computeWays(condition.toCharArray(),0, groups);
        }
        return nWays;
    }

    private static int computeWays(char[] condition,int i, List<Integer> groups) {

        if (i == condition.length) {
            return groups.isEmpty() ? 1 : 0;
        }

        if (groups.isEmpty()) {
            return new String(condition).substring(i).contains("#") ? 0 : 1;
        }

        char c = condition[i];

        if (c == '.') {
            // skip
            return computeWays(condition, i+1, groups);
        }

        int  ways = 0;
        if (c == '?') {
            condition[i] = '#';
            ways = computeWays(condition, i, groups);

            condition[i] = '.';
            ways += computeWays(condition, i, groups);

            condition[i] = '?';
        } else {
            // It's a '#'
            int group = groups.get(0);
            int blockCount = 0;
            while(i+blockCount < condition.length && condition[i+blockCount] != '.') {
                blockCount++;
            }

            if (group <= blockCount) {

                if (i+group == condition.length) {
                    return groups.size() == 1 ? 1 : 0;
                } else {
                    char nextChar = condition[i+group];
                    if (nextChar == '#') {
                        // Cannot contain this group
                        return 0;
                    } else if (nextChar == '.') {
                        return computeWays(condition,i+group, groups.subList(1, groups.size()));
                    } else {
                        // nextChar == '?'
                        condition[i+group] = '.';
                        ways = computeWays(condition, i+ group, groups.subList(1, groups.size()));
                        condition[i+group] = '?';
                    }
                }
            }
        }

        return ways;
    }
}
