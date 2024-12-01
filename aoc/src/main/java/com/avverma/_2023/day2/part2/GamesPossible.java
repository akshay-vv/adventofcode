package com.avverma._2023.day2.part2;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class GamesPossible {

    public static void main(String[] args) {
        List<String> lines = readInput();
        System.out.println(solve(lines));
    }

    private static int solve(List<String> lines) {
        int power = 0;

        Map<String, Integer> color2index = Map.of("red", 0, "green", 1, "blue", 2);

        for (String line: lines) {
            // Game 1: 3 blue, 4 red; 1 red, 2 green, 6 blue; 2 green

            String[] sets = line.split(":")[1].split(";");  // [0] 3 blue, 4 red  [1] 1 red, 2 green, 6 blue [2] 2 green
            int[] maxColor = new int[3];
            for (String set: sets) {
                String[] cubes = set.split(","); // // [0] 3 blue [1] 4 red
                for (String cube : cubes) {
                    String[] numAndColor = cube.trim().split(" ");  // [0] 3  [1] blue
                    int colorIdx = color2index.get(numAndColor[1]);
                    maxColor[colorIdx] = Math.max(maxColor[colorIdx], Integer.parseInt(numAndColor[0]));
                }
            }
            power += maxColor[0] *maxColor[1] * maxColor[2];

        }

        return power;
    }

    private static List<String> readInput() {
        List<String> result = new ArrayList<>();
//        try(BufferedReader reader = new BufferedReader(new FileReader("src/main/java/com/avverma/_2023/day2/part1/example_input.txt"))) {
        try(BufferedReader reader = new BufferedReader(new FileReader("src/main/java/com/avverma/_2023/day2/input.txt"))) {
            String line = reader.readLine();
            while(line != null) {
                result.add(line);
                line = reader.readLine();
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return result;
    }
}
