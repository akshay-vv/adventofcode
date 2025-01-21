package com.avverma._2024.day18.part2;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.*;

public class RamRun {

    char[][] grid = new char[71][71];

    int limit = 1024;

    record Point(int x, int y) {}

    List<Point> corrupted;

    int[][] directions = new int[][] {{0, -1}, {-1, 0}, {0, 1}, {1, 0}  };


    public static void main(String[] args) {
        RamRun rr = new RamRun();
        rr.parseInput();

        System.out.println("\n" + rr.solve());
    }

    String solve() {

        Arrays.stream(grid).forEach(row -> Arrays.fill(row, '.'));

        corrupted.stream().limit(limit).filter(p -> p.x < grid.length && p.y < grid[0].length).forEach(p -> grid[p.y][p.x] = '#');

        Point c = null;
        for (int i = limit; i < corrupted.size(); i++) {
             c = corrupted.get(i);
            grid[c.y][c.x] = '#';
            if (steps() == -1) {
                return c.x + "," + c.y;
            }
        }

        System.out.println(c.x + " " + c.y);
        return "no";
    }

    private int steps() {
        Set<Point> visited = new HashSet<>();
        Queue<Point> q = new LinkedList<>();
        q.offer(new Point(0, 0));
        int steps = 0;
        visited.add(new Point(0, 0));

        while(!q.isEmpty()) {
            int sz = q.size();
            while(sz > 0) {

                Point p = q.poll();
                if (p.x == grid.length -1 && p.y == grid.length -1) {
                    Arrays.stream(grid).forEach(row -> System.out.println(row));
                    return steps;
                }
                for (int[] dir : directions) {
                    Point ne = new Point(p.x + dir[0], p.y + dir[1]);

                    if (ne.x >= 0 && ne.y >= 0 && ne.x < grid.length && ne.y < grid[0].length && !visited.contains(ne) && grid[ne.x][ne.y] == '.') {
                        q.offer(ne);
                        visited.add(ne);
                    }
                }

                sz--;
            }
            steps++;
        }

        return -1;
    }


    private void parseInput() {
        try (BufferedReader br = new BufferedReader(new FileReader("aoc/src/main/java/com/avverma/_2024/day18/input.txt"))) {
            String line = br.readLine();

            corrupted = new ArrayList<>();
            while(line != null) {
                String[] p = line.split(",");
                corrupted.add(new Point(Integer.parseInt(p[0]), Integer.parseInt(p[1])));
                line = br.readLine();
            }
        } catch (Exception e) {
            System.err.println(e);
            throw new RuntimeException(e);
        }
    }
}