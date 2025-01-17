package com.avverma._2024.day16.part1;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class ReindeerMaze {

    char[][] grid;


    record Point(int x, int y) {}
    Point[] directions = new Point[]{ new Point(0, 1), new Point(1, 0), new Point(0, -1), new Point(-1, 0)};
    record PointAndDir(Point p, int dir) {}

    record PointDistance(Point p, int dir, int dis) {}
    Map<PointAndDir, Integer> memo = new HashMap<>();

    long solve() {

        Set<Point> v = new HashSet<>();

        //        return dfs(start, v, 0);
        //        return minCost(start);

        return dijkstra();
    }

    int dijkstra() {
        Point start = find('S');

        int[][] bestdistance = new int[grid.length][grid[0].length];
        Arrays.stream(bestdistance)
                .forEach(row -> Arrays.fill(row, Integer.MAX_VALUE));

        bestdistance[start.x][start.y] = 0;
        PriorityQueue<PointDistance> pq = new PriorityQueue<>(Comparator.comparingInt(PointDistance::dis));

        pq.offer(new PointDistance(start, 0, 0));

        while(!pq.isEmpty()) {
            PointDistance pd = pq.poll();
            for (int i =0 ; i < 4 ; i++) {
                if (Math.abs(pd.dir - i) == 2) {
                    continue;
                }
                Point neigh = new Point(pd.p.x + directions[i].x, pd.p.y + directions[i].y);
                int costMultiplier = Math.abs(pd.dir - i) == 3 ? 1 : Math.abs(pd.dir - i);
                int cost = pd.dis + 1 + costMultiplier * 1000;
                if (grid[neigh.x][neigh.y] != '#' && cost < bestdistance[neigh.x][neigh.y]) {
                    bestdistance[neigh.x][neigh.y] = cost;
                    pq.offer(new PointDistance(neigh, i, cost));
                    if (grid[neigh.x][neigh.y] == 'E') {
                        return cost;
                    }
                }
            }
        }


        return 0;
    }

    /**
     * Shamelessly converted dfs code to tabulation through ChatGPT
     * @param start
     * @return
     */
//    public int minCost(Point start) {
//        int rows = grid.length;
//        int cols = grid[0].length;
//        int[][][] dp = new int[rows][cols][4];
//
//        // Initialize DP table with a large number
//        for (int[][] row : dp) {
//            for (int[] cell : row) {
//                Arrays.fill(cell, Integer.MAX_VALUE);
//            }
//        }
//
//        // Min-heap to process cells by their current minimum cost
//        PriorityQueue<PointAndDir> queue = new PriorityQueue<>(Comparator.comparingInt(p -> dp[p.p.x][p.p.y][p.dir]));
//
//
//        // Edge case - start position induces 1000 per 90 Deg rotation
//        dp[start.x][start.y][0] = 0;
//        queue.offer(new PointAndDir(start, 0));
//        dp[start.x][start.y][1] =1000;
//        queue.offer(new PointAndDir(start, 1));
//        dp[start.x][start.y][2] = 2000;
//        queue.offer(new PointAndDir(start, 2));
//        dp[start.x][start.y][3] = 1000;
//        queue.offer(new PointAndDir(start, 3));
//
//
//        // Process the grid
//        while (!queue.isEmpty()) {
//            PointAndDir current = queue.poll();
//            Point p = current.p;
//            int d = current.dir;
//            int currentCost = dp[p.x][p.y][d];
//
//            for (int i = 0; i < 4; i++) {
//                int costMultiplier = Math.abs(d - i) == 3 ? 1 : Math.abs(d - i);
//                Point dir = directions[i];
//                Point next = new Point(p.x + dir.x, p.y + dir.y);
//
//                if ( grid[next.x][next.y] != '#') {
//                    int newCost = currentCost + 1 + costMultiplier * 1000;
//
//                    if (newCost < dp[next.x][next.y][i]) {
//                        dp[next.x][next.y][i] = newCost;
//                        queue.offer(new PointAndDir(next, i));
//                    }
//                }
//            }
//        }
//
//        // Find the minimum cost to reach the end point from any direction
//        Point end = find('E');
//        int minCost = Integer.MAX_VALUE;
//        for (int d = 0; d < 4; d++) {
//            minCost = Math.min(minCost, dp[end.x][end.y][d]);
//        }
//
//        return minCost == Integer.MAX_VALUE ? -1 : minCost;
//    }


    /**
     * Works for example inputs but not the actual (result is way higher than expected)
     * Debugging on the input causes stack overflow. Rightly so - possible 141*141= 19881 stack frames
     * @return
     */
//    int dfs(Point p, Set<Point> v, int d) {
//        if (v.contains(p) || grid[p.x][p.y] == '#') {
//            return -1;
//        }
//
//        PointAndDir pd = new PointAndDir(p, d);
//        if (memo.containsKey(pd)) {
//            return memo.get(pd);
//        }
//
//        if (grid[p.x][p.y] == 'E') {
//            return 0;
//        }
//
//        v.add(p);
//        List<Integer> results = new ArrayList<>();
//        for (int i = 0 ; i < 4; i++) {
//            int costMultiplier = Math.abs(d-i) == 3 ? 1 : Math.abs(d-i);
//            Point dir = directions[i];
//            Point next = new Point(p.x + dir.x, p.y + dir.y);
//            int r = dfs(next, v, i);
//            if (r != -1) {
//                r += 1;
//                r += costMultiplier * 1000;
//                results.add(r);
//            }
//        }
//        v.remove(p);
//        OptionalInt min = results.stream().mapToInt(i -> i).min();
//        int m = min.orElse(-1);
//        memo.put(pd, m);
//        return m;
//    }

    Point find(char c) {
        for (int i = 0 ; i < grid.length; i++) {
            for (int j = 0 ; j < grid.length; j++) {
                if (grid[i][j] == c) return new Point(i, j);
            }
        }
        throw new RuntimeException("robot not found!");
    }

    public static void main(String[] args) {
        ReindeerMaze rr = new ReindeerMaze();
        rr.parseInput();

        System.out.println("\n" + rr.solve());
    }

    private void parseInput() {
        try (BufferedReader br = new BufferedReader(new FileReader("aoc/src/main/java/com/avverma/_2024/day16/example_input2.txt"))) {
            List<char[]> g = new ArrayList<>();
            String line = br.readLine();

            while(line != null) {
                g.add(line.toCharArray());
                line = br.readLine();
            }
            grid = g.toArray(new char[g.size()][]);
        } catch (Exception e) {
            System.err.println(e);
            throw new RuntimeException(e);
        }
    }
}