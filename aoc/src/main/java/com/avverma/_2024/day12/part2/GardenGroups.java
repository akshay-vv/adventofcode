package com.avverma._2024.day12.part2;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.*;
import java.util.stream.Collectors;

public class GardenGroups {

    char[][] grid;

    int[][] dir = {{0, -1}, {-1, 0}, {0, 1}, {1, 0}};

    record Coordinates(int x, int y) {}

    record AreaAndSides(long area, long sides) {}
    long solve() {

        Set<Coordinates> visited = new HashSet<>();
        long ans = 0;

        for (int i = 0; i < grid.length; i++) {
            for (int j = 0 ; j < grid[0].length; j++) {
                Coordinates point = new Coordinates(i, j);
                if (!visited.contains(point)) {
                    AreaAndSides ap = calculateAreaAndSides(i, j, grid[i][j], visited);
                    ans += ap.area * ap.sides;
                    System.out.printf("%c %d\n", grid[i][j], ap.sides);
                }
            }
        }

        return ans;
    }

    AreaAndSides calculateAreaAndSides(int i, int j, char c, Set<Coordinates> visited) {
        Coordinates point = new Coordinates(i, j);
        if (i <0 || j < 0 || i >= grid.length || j >= grid[0].length || c != grid[i][j] || visited.contains(point)) {
            return new AreaAndSides(0, 0);
        }

        long area = 1;
        long corners = 0;
        visited.add(point);
        for (int d[]: dir) {
            AreaAndSides as = calculateAreaAndSides(i+d[0], j+d[1], c, visited);
            area += as.area;
            corners+= as.sides;
        }

        // Lemma: Number of corners ina polygon is equal to the number of sides
        corners += calculateCorners(i, j, c);
        return new AreaAndSides(area, corners);
    }

    long calculateCorners(int i, int j, char c) {
        int corners = 0;
        for(int iter = 0 ; iter < 4; iter++) {
            int x1 = i+dir[iter][0];
            int y1 = j+dir[iter][1];
            int x2 = i+dir[(iter+1)%4][0];
            int y2 = j+dir[(iter+1)%4][1];
            int x3 = i + dir[iter][0] + dir[(iter+1)%4][0];
            int y3 = j + dir[iter][1] + dir[(iter+1)%4][1];

            if (noMatch(x1, y1, c) && noMatch(x2, y2, c) ) {
                corners++;
            }

            if (match(x1, y1, c) && match(x2, y2, c) && noMatch(x3, y3, c)) {
                corners++;
            }
        }

        return corners;
    }

    boolean match(int i, int j, char c) {
        return i >= 0 && j >= 0 && i < grid.length && j < grid[0].length && grid[i][j] == c;
    }

    boolean noMatch(int i, int j, char c) {
        return i < 0 || j <0 || i >= grid.length || j >= grid[0].length || grid[i][j] != c;
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