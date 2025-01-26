package com.avverma._2024.day20.part1;

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
        int originalTime = getTime();

        Map<Integer, Integer> savedTimes = new HashMap<>();

        for (int i = 1; i < grid.length -1; i++) {
            for (int j = 1; j < grid.length -1; j++) {
                if (grid[i][j] == '#') {
                    grid[i][j] = 'O';

                    int time = getTime();
                    if (time < originalTime) {
                        savedTimes.put(originalTime- time, savedTimes.getOrDefault(originalTime- time, 0) + 1);
                    }

                    grid[i][j] = '#';
                }
            }
        }

        return savedTimes.entrySet().stream().filter(e -> e.getKey() >= threshold).mapToInt(e -> e.getValue()).sum();
    }

    private Integer getTime() {
        int time = 0;
        Set<Point> v = new HashSet<>();
        Queue<Point> q = new LinkedList<>();
        Point s = find('S');
        q.add(s);
        v.add(s);

        List<Integer> endTimes = new ArrayList<>();

        while(!q.isEmpty()) {
            int sz =  q.size();
            time++;
            while (sz-- > 0) {
                Point p = q.poll();

                for (int[] d : directions) {
                    Point n = new Point(p.x + d[0], p.y + d[1]);
                    if (n.x >= 0 && n.y >=0 && n.x < grid.length && n.y < grid[0].length && !v.contains(n)) {
                        int nextX = n.x + d[0];
                        int nextY = n.y + d[1];
                        if (grid[n.x][n.y] == '.') {
                            q.offer(n);
                            v.add(n);
                        }else if (grid[n.x][n.y] == 'O' && nextX >= 0 && nextY >=0 && nextX < grid.length && nextY < grid[0].length && (grid[nextX][nextY] == '.' || grid[nextX][nextY] == 'E')) {
                            q.offer(n);
                            v.add(n);
                        } else if (grid[n.x][n.y] == 'E') {
                            return time;
                        }
                    }
                }
            }
        }
        throw new RuntimeException("E not found");
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