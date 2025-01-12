package com.avverma._2024.day1.day10.part2;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

public class HoofIt {

    int[][] map;

    int[][] dir = {{0, -1}, {-1, 0}, {0, 1}, {1, 0}};

    long solve() {
        int totScore = 0;
        for(int i = 0 ; i < map.length; i++) {
            for (int j = 0; j < map.length; j++) {
                if(map[i][j] == 0) {
                    // Trailhead
                    totScore += calculateScore(i, j, 0);
                }
            }
        }
        return totScore;
    }

    private int calculateScore(int i, int j, int val) {
        if (i < 0 || j < 0 || i >= map.length || j >= map[0].length || map[i][j] != val) {
            return 0;
        }

        if (map[i][j] == 9) {
            return 1;
        }

        int total = 0;
        for (int[] d : dir) {
            total += calculateScore(i + d[0], j + d[1], val + 1);
        }
        return total;
    }

    public static void main(String[] args) {
        HoofIt rc = new HoofIt();
        rc.parseInput();

        System.out.println("\n" + rc.solve());

    }

    private void parseInput() {
        try (BufferedReader br = new BufferedReader(new FileReader("aoc/src/main/java/com/avverma/_2024/day10/input.txt"))) {
            String line = br.readLine();
            List<int[]> rows = new ArrayList<>();
            while(line != null) {
                rows.add(line.chars().map(ascii -> ascii - '0').toArray());
                line = br.readLine();
            }

            map = rows.toArray(new int[rows.size()][]);
        } catch (Exception e) {
            System.err.println(e);
            throw new RuntimeException(e);
        }
    }
}