package com.avverma._2024.day4.part1;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Stack;

public class Xmas {

    static int[][] directions = {{-1, -1}, {-1, 0}, {-1, 1}, {0, -1}, {0, 1}, {1, -1}, {1, 0}, {1, 1}};

    private static int solve(Character[][] input) {
        int sum = 0;
        Stack<int[]> path = new Stack<>();
        for (int i = 0; i < input.length; i++) {
            for (int j = 0; j < input[0].length; j++) {
                if (input[i][j] == 'X') {
                    for (int[] dir : directions) {
                        path.push(new int[]{i, j});
                        sum += find(i + dir[0], j + dir[1], input, "MAS", dir, path);
                        path.pop();
                    }
                }
            }
        }
        return sum;
    }

    private static int find(int i, int j, Character[][] input, String word, int[] dir, Stack<int[]> path) {
        if (word.length() == 0) {
            return 1;
        }

        if (i < 0 || j < 0 || i >= input.length || j >= input[0].length) {
            return 0;
        }


        if (input[i][j] == word.charAt(0)) {
            path.push(new int[]{i, j});
            int ret = find(i + dir[0], j + dir[1], input, word.substring(1), dir, path);
            path.pop();
            return ret;
        }
        return 0;
    }


    public static void main(String[] args) {
        Character[][] input = parseInput();

        System.out.println(solve(input));
    }

    private static Character[][] parseInput() {
        try (BufferedReader br = new BufferedReader(new FileReader("aoc/src/main/java/com/avverma/_2024/day4/input.txt"))) {
            List<Character[]> res = new ArrayList<>();
            String line = br.readLine();
            while (line != null) {
                Character[] chars = line.chars().mapToObj(c -> (char) c).toArray(Character[]::new);
                res.add(chars);
                line = br.readLine();
            }
            return res.toArray(Character[][]::new);
        } catch (Exception e) {
            System.err.println(e);
            throw new RuntimeException(e);
        }
    }
}
