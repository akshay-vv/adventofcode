package com.avverma._2024.day6.part1;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.*;
import java.util.stream.Collectors;

public class GuardGallivant {

    char[][] grid;
    int[][] directions = {{0, -1}, {-1, 0}, {0, 1}, {1, 0}};
    int steps = 0;

    int solve() {
       int[] startPos = startPos();
        calculateStepsRecusive(startPos[0],startPos[1], 1);
       return steps;
    }

    void calculateSteps(int i, int j, int dir) {
        while(!(i<0 || j < 0 || i >= grid.length || j >= grid[0].length || grid[i][j] == '#')) {
            do {
                if (grid[i][j] != '1') {
                    // Mark visited as 1
                    steps++;
                    grid[i][j] = '1';
                }
                i += directions[dir][0];
                j += directions[dir][1];
            } while (i >= 0 && j >= 0 && i < grid.length && j < grid[0].length && (grid[i][j] == '.' || grid[i][j] == '^' || grid[i][j] == '1'));

            // Out of bounds - exit grid
            if (i<0 || j < 0 || i >= grid.length || j >= grid[0].length ) {
                return;
            }

            // Take a step back - hit an obstacle
            i -= directions[dir][0];
            j -= directions[dir][1];


            // Take a right
            dir = ((dir + 1) % directions.length);
            i += directions[dir][0];
            j += directions[dir][1];
        }
    }

    void calculateStepsRecusive(int i, int j, int dir) {
        while((i<0 || j < 0 || i >= grid.length || j >= grid[0].length || grid[i][j] == '#')) {
            return;
        }

        do {
            if (grid[i][j] != '1') {
                // Mark visited as 1
                steps++;
                grid[i][j] = '1';
            }
            i += directions[dir][0];
            j += directions[dir][1];
        } while (i >= 0 && j >= 0 && i < grid.length && j < grid[0].length && (grid[i][j] == '.' || grid[i][j] == '^' || grid[i][j] == '1'));

        // Out of bounds - exit grid
        if (i<0 || j < 0 || i >= grid.length || j >= grid[0].length ) {
            return;
        }

        // Take a step back - hit an obstacle
        i -= directions[dir][0];
        j -= directions[dir][1];


        // Take a right
        dir = ((dir + 1) % directions.length);
        calculateStepsRecusive(i+directions[dir][0], j + directions[dir][1], dir);
    }

    int[] startPos() {
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[0].length; j++) {
                if (grid[i][j] == '^') {
                    return new int[]{i, j};
                }
            }
        }
        return new int[]{0, 0};
    }

    public static void main(String[] args) {
        GuardGallivant pq = new GuardGallivant();
        pq.parseInput();

        System.out.println("\n" + pq.solve());

    }

    private void parseInput() {
        try (BufferedReader br = new BufferedReader(new FileReader("aoc/src/main/java/com/avverma/_2024/day6/input.txt"))) {
            String line = br.readLine();
            List<char[]> grid = new ArrayList<>();
            while (line != null) {
                grid.add(line.toCharArray());
                line = br.readLine();
            }
            this.grid = grid.toArray(new char[grid.size()][]);

        } catch (Exception e) {
            System.err.println(e);
            throw new RuntimeException(e);
        }
    }
}