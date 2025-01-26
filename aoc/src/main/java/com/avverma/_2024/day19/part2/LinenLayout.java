package com.avverma._2024.day19.part2;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.*;
import java.util.stream.Collectors;

public class LinenLayout {

    List<String> patterns;
    List<String> targets;

   public static void main(String[] args) {
        LinenLayout rr = new LinenLayout();
        rr.parseInput();

        System.out.println("\n" + rr.solve());
    }

    long solve() {

        long totalWays = 0;

        for (String target: targets) {
//            Map<String, Long> memo = new HashMap<>();
            totalWays += countConstruct(target);
        }
        return totalWays;
    }

    long countConstruct(String target) {
       return 0;
    }

//    long countConstruct(String target, Map<String, Long> memo) {
//       if(target.equals("")) {
//           return 1;
//       }
//       if (memo.containsKey(target)) {
//           return memo.get(target);
//       }
//
//       long contructWays = 0;
//       for(int i = 0 ; i < patterns.size(); i++) {
//           if (target.startsWith(patterns.get(i))) {
//               contructWays += contructWays(target.substring(patterns.get(i).length()), memo);
//           }
//       }
//
//       memo.put(target, contructWays);
//       return contructWays;
//    }

    private void parseInput() {
        try (BufferedReader br = new BufferedReader(new FileReader("aoc/src/main/java/com/avverma/_2024/day19/input.txt"))) {
            String line = br.readLine();

            patterns = new ArrayList<>();
            targets = new ArrayList<>();
            boolean isPattern = true;
            while(line != null) {
                if (line.equals("")) {
                    // break
                    isPattern = false;
                    line = br.readLine();
                    continue;
                }
                if (isPattern) {
                    String[] words = line.split(", ");
                    patterns = Arrays.stream(words).collect(Collectors.toList());
                } else {
                    targets.add(line);
                }

                line = br.readLine();
            }
        } catch (Exception e) {
            System.err.println(e);
            throw new RuntimeException(e);
        }
    }
}