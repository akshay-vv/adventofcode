package com.avverma._2024.day4.part2;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Stack;

public class Xmas {
    private static int solve(Character[][] input) {
        int sum = 0;
        for (int i = 0; i < input.length; i++) {
            for (int j = 0; j < input[0].length; j++) {
                if (input[i][j] == 'A') {
                     sum += find(i, j, input) ? 1 :0;
                }
            }
        }
        return sum;
    }

    private static boolean find(int i, int j, Character[][] input) {
        // down
        int x1 = i-1, y1 = j-1, x2 = i+1, y2=j+1;
        // up
        int a1 = i+1, b1 = j-1, a2 = i-1, b2=j+1;


        if (x1 < 0 || y1 < 0|| a2 < 0 || b1 < 0 ||  a1 >= input.length  ||  a2 >= input.length  || y2 >= input[0].length || b2 >= input[0].length) {
            return false;
        }


        return ((input[x1][y1] == 'M' && input[x2][y2] == 'S') || (input[x1][y1] == 'S' && input[x2][y2] == 'M')) &&
                ((input[a1][b1] == 'M' && input[a2][b2] == 'S') || (input[a1][b1] == 'S' && input[a2][b2] == 'M'));

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
