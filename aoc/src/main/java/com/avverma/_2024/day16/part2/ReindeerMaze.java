package com.avverma._2024.day16.part2;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.*;

public class ReindeerMaze {

    char[][] grid;


    record Point(int x, int y) {}
    Point[] directions = new Point[]{ new Point(0, 1), new Point(1, 0), new Point(0, -1), new Point(-1, 0)};
    record PointAndDir(Point p, int dir) {}

    record BestDistance(int cost, Set<PointAndDir> from){};

    record PointDistance(Point p, int dir, int dis) {}
    Map<PointAndDir, Integer> memo = new HashMap<>();

    long solve() {

        Set<Point> v = new HashSet<>();
        return dijkstra();
    }

    int dijkstra() {
        Point start = find('S');

        BestDistance[][][] bestdistance = new BestDistance[grid.length][grid[0].length][4];

        bestdistance[start.x][start.y][0] = new BestDistance(0, new HashSet<>(List.of(new PointAndDir(start, 0))));
        bestdistance[start.x][start.y][1] = new BestDistance(1000, new HashSet<>(List.of(new PointAndDir(start, 0))));
        bestdistance[start.x][start.y][2] = new BestDistance(1000, new HashSet<>(List.of(new PointAndDir(start, 0))));
        bestdistance[start.x][start.y][3] = new BestDistance(2000, new HashSet<>(List.of(new PointAndDir(start, 0))));
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
                if (grid[neigh.x][neigh.y] != '#') {
                    if (bestdistance[neigh.x][neigh.y][i] == null || cost <= bestdistance[neigh.x][neigh.y][i].cost) {
                        pq.offer(new PointDistance(neigh, i, cost));

                        if (bestdistance[neigh.x][neigh.y][i] == null) {
                            bestdistance[neigh.x][neigh.y][i] = new BestDistance(cost, new HashSet<>(List.of(new PointAndDir(pd.p, pd.dir))));
                        }else {
                            bestdistance[neigh.x][neigh.y][i].from.add(new PointAndDir(pd.p, pd.dir));
                        }
                    }
                }
            }
        }
        Point end = find('E');

        List<PointAndDir> bestPointAndDir = new ArrayList<>();
        int minCost = Arrays.stream(bestdistance[end.x][end.y])
                .filter(Objects::nonNull)
                .min(Comparator.comparingInt(BestDistance::cost)).get().cost;
        for (int i = 0 ; i < 4; i++) {
            if (bestdistance[end.x][end.y][i] != null && bestdistance[end.x][end.y][i].cost == minCost) {
                bestPointAndDir.add(new PointAndDir(end, i));
                minCost = bestdistance[end.x][end.y][i].cost;
            }
         }

        Set<Point> best = new HashSet<>();
        for (PointAndDir bestEnd: bestPointAndDir) {
            best.addAll(backTrack(bestEnd, bestdistance));
        }

        for (Point b : best) {
            grid[b.x][b.y] = 'O';
        }

        Arrays.stream(grid).forEach(row -> System.out.println(row));
        return best.size();
    }


    Set<Point> backTrack(PointAndDir pd, BestDistance[][][] bestdistance) {
        if (bestdistance[pd.p.x][pd.p.y][pd.dir].from.stream().anyMatch(f ->f.p.equals(pd.p))) {
            Set<Point> sol = new HashSet<>();
            sol.add(pd.p);
            return sol;
        }

        Set<Point> sol = new HashSet<>();
        for(PointAndDir prev : bestdistance[pd.p.x][pd.p.y][pd.dir].from) {
            sol.addAll(backTrack(prev, bestdistance));
            sol.add(pd.p);
        }

        return  sol;
    }


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
        try (BufferedReader br = new BufferedReader(new FileReader("aoc/src/main/java/com/avverma/_2024/day16/input.txt"))) {
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