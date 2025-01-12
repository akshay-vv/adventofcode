package com.avverma._2024.day15.part1;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class WarehouseWoes {

    char[][] grid;
    List<Character> movements;

    Map<Character, int[]> dirMap = Map.of(
            '<', new int[]{0, -1},
            '^', new int[]{-1, 0},
            '>', new int[]{0, 1},
            'v', new int[]{1, 0}
    );

    int seconds = 100;

    long solve() {
        int[] currPos = robotPos();
        for (Character move: movements) {
            int[] dir = dirMap.get(move);
            int[] nextPos = new int[] {currPos[0] + dir[0], currPos[1] + dir[1]};
            char next = grid[nextPos[0]][nextPos[1]];
            if (next == '.') {
                grid[currPos[0]][currPos[1]] = '.';
                grid[nextPos[0]][nextPos[1]] = '@';
                currPos[0] += dir[0];
                currPos[1] += dir[1];
            } else if (next == 'O' && shift(nextPos, dir)) {
                grid[currPos[0]][currPos[1]] = '.';
                grid[nextPos[0]][nextPos[1]] = '@';
                currPos[0] += dir[0];
                currPos[1] += dir[1];
            }
        }
        return calculateGps();
    }

    long calculateGps() {
        long sumGps = 0;
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid.length; j  ++) {
                if (grid[i][j] == 'O') {
                    sumGps += 100*i + j;
                }
            }
        }
        return sumGps;
    }
    boolean shift(int[] pos, int[] dir) {
        int[] next = new int[]{pos[0] + dir[0], pos[1] + dir[1]};

        if (grid[next[0]][next[1]] == '.') {
            grid[pos[0]][pos[1]] = '.';
            grid[next[0]][next[1]] = 'O';
            return true;
        } else if (grid[next[0]][next[1]] == '#') {
            return false;
        }

        if (shift(next, dir)) {
            grid[pos[0]][pos[1]] = '.';
            grid[next[0]][next[1]] = 'O';
            return true;
        }

        return false;
    }

    int[] robotPos() {
        for (int i = 0 ; i < grid.length; i++) {
            for (int j = 0 ; j < grid.length; j++) {
                if (grid[i][j] == '@') return new int[]{i, j};
            }
        }
        throw new RuntimeException("robot not found!");
    }

    public static void main(String[] args) {
        WarehouseWoes rr = new WarehouseWoes();
        rr.parseInput();

        System.out.println("\n" + rr.solve());
    }

    private void parseInput() {
        try (BufferedReader br = new BufferedReader(new FileReader("aoc/src/main/java/com/avverma/_2024/day15/input.txt"))) {
            this.movements = new ArrayList<>();
            List<char[]> g = new ArrayList<>();
            String line = br.readLine();

            boolean isGrid = true;
            while(line != null) {
                if (line.equals("")) {
                    isGrid = false;
                    grid = g.toArray(new char[g.size()][]);
                }

                if (isGrid) {
                    g.add(line.toCharArray());
                } else {
                    movements.addAll(line.chars().mapToObj(c -> (char)c).collect(Collectors.toList()));
                }
                line = br.readLine();
            }
        } catch (Exception e) {
            System.err.println(e);
            throw new RuntimeException(e);
        }
    }
}