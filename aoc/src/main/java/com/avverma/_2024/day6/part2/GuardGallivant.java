package com.avverma._2024.day6.part2;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.*;

public class GuardGallivant {

    char[][] grid;
    int[][] directions = {{0, -1}, {-1, 0}, {0, 1}, {1, 0}};
    int solve() {
        int[] startPos = startPos();
        int loop = 0;
        for (int i=0; i < grid.length; i++) {
            for (int j=0; j < grid[0].length; j++) {
                if (grid[i][j] == '.') {
                    grid[i][j] = '#';
                    if(hasLoop(startPos[0],startPos[1], 1)) {
                        System.out.printf("%d %d\n", i, j);
                        loop++;
                    }
                    grid[i][j] = '.';
                }
            }
        }

        return loop;
    }

    boolean hasLoop(int i, int j, int dir) {
        int nIterations = 0;
        while(nIterations != (grid.length * grid[0].length *4) + 1) {
            while (i >= 0 && j >= 0 && i < grid.length && j < grid[0].length && (grid[i][j] == '.' || grid[i][j] == '^')) {
                i += directions[dir][0];
                j += directions[dir][1];
            }

            // Out of bounds - exit grid
            if (i<0 || j < 0 || i >= grid.length || j >= grid[0].length ) {
                return false;
            }

            // Take a step back - hit an obstacle
            i -= directions[dir][0];
            j -= directions[dir][1];


            // Take a right
            dir = ((dir + 1) % directions.length);
            i += directions[dir][0];
            j += directions[dir][1];
            nIterations++;
        }

        return true;
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
