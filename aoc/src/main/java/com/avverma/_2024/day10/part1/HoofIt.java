package com.avverma._2024.day10.part1;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class HoofIt {

    int[][] map;

    int[][] dir = {{0, -1}, {-1, 0}, {0, 1}, {1, 0}};

    record Coordinates(int x, int y) {}

    long solve() {
        int totScore = 0;
        for(int i = 0 ; i < map.length; i++) {
            for (int j = 0; j < map.length; j++) {
                if(map[i][j] == 0) {
                    // Trailhead
                    Set<Coordinates> destinations = new HashSet<>();
                    calculateScore(i, j, 0, destinations);
                    totScore += destinations.size();
                }
            }
        }
        return totScore;
    }

    private void calculateScore(int i, int j, int val, Set<Coordinates> destinations) {
        if (i < 0 || j < 0 || i >= map.length || j >= map[0].length || map[i][j] != val) {
            return;
        }

        if (map[i][j] == 9) {
            destinations.add(new Coordinates(i, j));
            return;
        }

        for (int[] d : dir) {
            calculateScore(i + d[0], j + d[1], val + 1, destinations);
        }
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