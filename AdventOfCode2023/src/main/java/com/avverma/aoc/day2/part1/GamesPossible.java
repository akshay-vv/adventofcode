package com.avverma.aoc.day2.part1;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class GamesPossible {

    public static void main(String[] args) {
        List<String> lines = readInput();

        int red = 12, green = 13, blue =14;
        System.out.println(solve(lines, red, green, blue));
    }

    private static int solve(List<String> lines, int red,int green,int blue) {
        int result = 0;

        Map<String, Integer> color2index = Map.of("red", 0, "green", 1, "blue", 2);

        for (int gameNum = 0 ; gameNum < lines.size(); gameNum++) {
            // Game 1: 3 blue, 4 red; 1 red, 2 green, 6 blue; 2 green

            String[] sets = lines.get(gameNum).split(":")[1].split(";");  // [0] 3 blue, 4 red  [1] 1 red, 2 green, 6 blue [2] 2 green
            int[] maxColor = new int[3];
            for (String set: sets) {
                String[] cubes = set.split(","); // // [0] 3 blue [1] 4 red
                for (String cube : cubes) {
                    String[] numAndColor = cube.trim().split(" ");  // [0] 3  [1] blue
                    int colorIdx = color2index.get(numAndColor[1]);
                    maxColor[colorIdx] = Math.max(maxColor[colorIdx], Integer.parseInt(numAndColor[0]));
                }
            }
            if(maxColor[0] <= red && maxColor[1] <= green && maxColor[2] <= blue) {
                System.out.println(gameNum+1);
                result += gameNum+1;
            }
        }

        return result;
    }

    private static List<String> readInput() {
        List<String> result = new ArrayList<>();
//        try(BufferedReader reader = new BufferedReader(new FileReader("src/main/java/com/avverma/aoc/day2/part1/example_input.txt"))) {
        try(BufferedReader reader = new BufferedReader(new FileReader("src/main/java/com/avverma/aoc/day2/input.txt"))) {
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
