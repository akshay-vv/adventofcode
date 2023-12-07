package com.avverma.aoc.day6.part1;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Numways {
    private static int numWaysMultiplied(List<String> input) {
        //        Time:      7  15   30
        //        Distance:  9  40  200
        int[] times = Arrays.stream(input.get(0).split(":")[1].split(" ")).map(String::trim).filter(s -> !s.isEmpty()).mapToInt(Integer::parseInt).toArray();
        int[] distances = Arrays.stream(input.get(1).split(":")[1].split(" ")).map(String::trim).filter(s -> !s.isEmpty()).mapToInt(Integer::parseInt).toArray();

        int res = 1;
        for (int i = 0 ; i < times.length; i++) {
            int numWays = 0;
            for (int s=1; s < times[i]; s++) {
                int targetDistance = (times[i]-s) * s;
                if ( targetDistance > distances[i]) {
                    numWays++;
                }
            }
            res *= numWays;
        }

        return res;
    }

    static List<String> readInput() {
        try(BufferedReader br = new BufferedReader(new FileReader("src/main/java/com/avverma/aoc/day6/input.txt"))) {
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
        System.out.println(numWaysMultiplied(input));
    }
}
