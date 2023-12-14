package com.avverma.aoc.day14.part1;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ParabolicReflector {

    private static long solve(List<String> lines) {
        long sum = 0;
        int[] nextLoad = new int[lines.get(0).length()];
        Arrays.fill(nextLoad, lines.size());

        for (int i = 0 ; i < lines.size(); i++) {
            String line = lines.get(i);
            for (int j = 0 ; j < line.length(); j++) {
                char c = line.charAt(j);
                if (c == 'O') {
                    sum += nextLoad[j];
                    nextLoad[j]--;
                } else if (c == '#') {
                    nextLoad[j] = lines.size() - i - 1;
                }
            }
        }
        return sum;
    }


    public static void main(String[] args) {
        System.out.println(solve(readInput()));
    }

    private static List<String> readInput() {
        try(BufferedReader br = new BufferedReader(new FileReader("src/main/java/com/avverma/aoc/day14/input.txt"))) {
            List<String> lines = new ArrayList<>();
            String line = br.readLine();
            while(line != null) {
                lines.add(line);
                line = br.readLine();
            }
            return lines;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
