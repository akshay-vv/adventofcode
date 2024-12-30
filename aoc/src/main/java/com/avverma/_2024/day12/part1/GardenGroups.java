package com.avverma._2024.day12.part1;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.*;
import java.util.stream.Collectors;

public class GardenGroups {

    char[][] grid;

    int[][] dir = {{0, -1}, {-1, 0}, {0, 1}, {1, 0}};

    record Coordinates(int x, int y) {}

    record Solution(int area, int perimeter) {}
    long solve() {

        Set<Coordinates> visited = new HashSet<>();
        long ans = 0;
        for (int i = 0 ; i < grid.length; i++) {
            for (int j = 0 ; j < grid[0].length; j++) {
                if (visited.contains(new Coordinates(i, j))) {
                    continue;
                }
                char c = grid[i][j];
                Solution s = area(i , j, c, visited);
                ans += s.area*s.perimeter;
            }
        }
        return ans;
    }

    Solution area(int i, int j, char c, Set<Coordinates> visited) {
        Coordinates coord = new Coordinates(i, j);
        if (i < 0 || j < 0 || i >= grid.length || j >= grid[0].length || grid[i][j] != c || visited.contains(coord)) return new Solution(0, 0);

        visited.add(coord);

        int perimeter = perimeter(i, j, c);
        int totalArea = 0;
        int totalPerimeter = 0;
        for (int[] d : dir) {
            Solution solution = area(i+d[0], j+d[1], c, visited);
            totalArea += solution.area;
            totalPerimeter += solution.perimeter;
        }

        return new Solution(totalArea + 1, totalPerimeter + perimeter);
    }

    private int perimeter(int i, int j, char c) {
        int perimeter = 0;
        for (int[] d : dir) {
            int x = i + d[0];
            int y = j + d[1];
            if (x >= 0 && y >= 0 && x < grid.length && y < grid[0].length && grid[x][y] == c) {
                perimeter++;
            }
        }
        return 4 - perimeter;
    }

    public static void main(String[] args) {
        GardenGroups pp = new GardenGroups();
        pp.parseInput();

        System.out.println("\n" + pp.solve());
    }

    private void parseInput() {
        try (BufferedReader br = new BufferedReader(new FileReader("aoc/src/main/java/com/avverma/_2024/day12/input.txt"))) {
            String line = br.readLine();
            List<char[]> gr = new ArrayList<>();
            while (line != null) {
                gr.add(line.toCharArray());
                line = br.readLine();
            }
            grid = gr.toArray(new char[gr.size()][]);
        } catch (Exception e) {
            System.err.println(e);
            throw new RuntimeException(e);
        }
    }
}