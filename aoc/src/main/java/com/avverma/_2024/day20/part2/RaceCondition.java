package com.avverma._2024.day20.part2;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.*;
import java.util.stream.Collectors;

public class RaceCondition {

    char[][] grid;

    int[][] directions = new int[][]{{0, -1},{-1, 0}, {0, 1}, {1, 0} };

    record Point(int x, int y) {}

    int threshold = 100;

    public static void main(String[] args) {
        RaceCondition rr = new RaceCondition();
        rr.parseInput();

        System.out.println("\n" + rr.solve());
    }

    int solve() {
       int[][] allTimes = allTimes();

        // A cheat can start at any point `.` unlike part 1 where it starts at #
        // The cheat has to end at a `.` or `E` - basically a non -1 entry in allTimes
        // For every point in the grid we'll go through all the points that are at most 20ps apart
        //   For those sub points calculate the time it saves us if we could teleport to that point


        /** For every eligible('.'/ 'S') point identify all the points 20ps apart - diamond
         * ..............................
         * ..............................
         * ..............................
         * ..............................
         * ..............O...............
         * .............OOO..............
         * ............OOOOO.............
         * ...........OOOOOOO............
         * ..........OOOOOOOOO...........
         * .........OOOOOOOOOOO..........
         * ........OOOOOOOOOOOOO.........
         * .......OOOOOOOOOOOOOOO........
         * ......OOOOOOOOOOOOOOOOO.......
         * .....OOOOOOOOOOOOOOOOOOO......
         * ....OOOOOOOOOOXOOOOOOOOOO.....
         * .....OOOOOOOOOOOOOOOOOOO......
         * ......OOOOOOOOOOOOOOOOO.......
         * .......OOOOOOOOOOOOOOO........
         * ........OOOOOOOOOOOOO.........
         * .........OOOOOOOOOOO..........
         * ..........OOOOOOOOO...........
         * ...........OOOOOOO............
         * ............OOOOO.............
         * .............OOO..............
         * ..............O...............
         * ..............................
         * ..............................
         * ..............................
         * ..............................
         * ..............................
         */
        Map<Integer, Integer> timeSaved = new HashMap<>();
        int M = 20;
        for (int i = 0 ; i < grid.length; i++) {
            for (int j = 0 ; j < grid[0].length; j++) {
                if (grid[i][j] == '#') continue;    // Cannot start from a block

                for (int r2 = Math.max(0, i-M); r2 < Math.min(grid.length, i+ M + 1); r2++) {
                    for (int c2 = Math.max(0, j-M); c2 < Math.min(grid[0].length, j + M + 1); c2++) {
                        // This loop creates a square around the point i, j we need a diamond - trim out the point that have hamiltonian distance less than M
                        int dist = Math.abs(i-r2) + Math.abs(j-c2);
                        if (dist > M) continue;         // Not part of diamond

                        if (grid[r2][c2] == '#') continue; // Should end at a '.' or 'E'

                        int saved = allTimes[i][j] - allTimes[r2][c2] - dist;
                        if(saved < threshold) continue;

                        timeSaved.put(saved, timeSaved.getOrDefault(saved, 0) + 1);
                    }
                }
            }

        }

        return timeSaved.values().stream().mapToInt(i -> i).sum();
    }

    private int[][] allTimes() {
        // H x W grid where each cell represents the time it takes from that cell to the end

        int[][] allTimes = new int[grid.length][grid[0].length];
        Arrays.stream(allTimes).forEach(row -> Arrays.fill(row, -1));

        int time = 0;
        Point end = find('E');

        Queue<Point> q = new LinkedList<>();
        q.add(end);
        while(!q.isEmpty()) {
            int sz = q.size();
            while(sz-- > 0) {
                Point p = q.poll();
                allTimes[p.x][p.y] = time;
                for (int[] d : directions) {
                    Point n = new Point(p.x + d[0] , p.y + d[1]);
                    if (grid[n.x][n.y] == '#') continue;
                    if (allTimes[n.x][n.y] != -1) continue;
                    q.add(n);
                }
            }
            time++;
        }
        return allTimes;
    }


    Point find(char c) {
        for (int i =0 ; i < grid.length; i++) {
            for (int j = 0 ; j < grid[0].length; j++) {
                if (grid[i][j] == c) {
                    return new Point(i, j);
                }
            }
        }
        throw new RuntimeException(c + "Not found");
    }


    private void parseInput() {
        try (BufferedReader br = new BufferedReader(new FileReader("aoc/src/main/java/com/avverma/_2024/day20/input.txt"))) {
            String line = br.readLine();

            List<char[]> g = new ArrayList<>();
            while(line != null) {
                g.add(line.toCharArray());

                line = br.readLine();
            }

            this.grid = g.toArray(new char[g.size()][]);
        } catch (Exception e) {
            System.err.println(e);
            throw new RuntimeException(e);
        }
    }
}