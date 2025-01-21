package com.avverma._2024.day19.part1;

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

    int solve() {

        int possible = 0;

        for (String target: targets) {
            Map<String, Boolean> memo = new HashMap<>();
            if (canConstruct(target, memo)) {
                possible++;
            }
        }
        return possible;
    }

    boolean canConstruct(String target, Map<String, Boolean> memo) {
       if(target.equals("")) {
           return true;
       }
       if (memo.containsKey(target)) {
           return memo.get(target);
       }

       for(int i = 0 ; i < patterns.size(); i++) {
           if (target.startsWith(patterns.get(i))) {
               if (canConstruct(target.substring(patterns.get(i).length()), memo)) {
                   memo.put(target, true);
                   return true;
               }
           }
       }

       memo.put(target, false);
       return false;
    }

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