package com.avverma._2023.day16.part2;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.*;
import java.util.concurrent.*;

public class FloorWillBeLava {

    static class Point{
        int x;
        int y;

        Point(int x, int y) {
            this.x = x;
            this.y = y;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Point point = (Point) o;
            return x == point.x && y == point.y;
        }

        @Override
        public int hashCode() {
            return Objects.hash(x, y);
        }
    }

    static class Beam {
        Point p;
        int directionX;
        int directionY;

        Beam(Point p, int dirX, int dirY) {
            this.p = p;
            this.directionX = dirX;
            this.directionY = dirY;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Beam beam = (Beam) o;
            return directionX == beam.directionX && directionY == beam.directionY && Objects.equals(p, beam.p);
        }

        @Override
        public int hashCode() {
            return Objects.hash(p, directionX, directionY);
        }
    }

    public static void main(String[] args) {
        System.out.println(maxEnergized(readInput()));
    }

    private static int maxEnergized(Character[][] characters) {
        ExecutorService es = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
        List<Energizer> tasks = new ArrayList<>();

        for(int i = 0 ; i < characters.length; i++) {
            tasks.add(new Energizer(characters, new Beam(new Point(i, 0), 0, 1)));
            tasks.add(new Energizer(characters, new Beam(new Point(i, characters[0].length-1), 0, -1)));
        }

        for(int i = 0 ; i < characters[0].length; i++) {
            tasks.add(new Energizer(characters, new Beam(new Point(0, i), 1, 0)));
            tasks.add(new Energizer(characters, new Beam(new Point(characters.length-1, i), -1, 0)));
        }

        try {
            List<Future<Integer>> res = es.invokeAll(tasks);
            es.shutdown();
            return res.stream().map(f -> {
                try {
                    return f.get();
                } catch (Exception e) {
                    System.out.println("Err");
                    return 0;
                }
            }).mapToInt(Integer::intValue).max().getAsInt();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    private static class Energizer implements Callable<Integer> {

        Character[][] grid;
        Beam start;

        Energizer(Character[][] grid, Beam start) {
            this.grid = grid;
            this.start = start;
        }

        @Override
        public Integer call() throws Exception {
            return countEnergized();
        }

        private int countEnergized() {
            Stack<Beam> beams = new Stack<>();
            Set<Beam> visited = new HashSet<>();
            Set<Point> points = new HashSet<>();

            beams.add(start);

            while(!beams.isEmpty()) {
                Beam b = beams.pop();

                if (withinBounds(grid, b.p) && !visited.contains(b)) {
                    visited.add(b);
                    points.add(b.p);

                    Point curr = b.p;
                    if (grid[curr.x][curr.y] == '.' || (b.directionX == 0 && grid[curr.x][curr.y] == '-') || (b.directionY == 0 && grid[curr.x][curr.y] == '|') ) {
                        // Continue in the same direction
                        beams.add(new Beam(new Point(curr.x+b.directionX, curr.y+b.directionY), b.directionX, b.directionY));
                    } else if (b.directionY == 0 && grid[curr.x][curr.y] == '-') {
                        // Split horizontal
                        beams.add(new Beam(new Point(curr.x, curr.y-1), 0, -1));
                        beams.add(new Beam(new Point(curr.x, curr.y+1), 0, 1));
                    } else if (b.directionX == 0 && grid[curr.x][curr.y] == '|') {
                        // Split horizontal
                        beams.add(new Beam(new Point(curr.x-1, curr.y), -1, 0));
                        beams.add(new Beam(new Point(curr.x+1, curr.y), +1, 0));
                    } else if ((b.directionX == 1 && grid[curr.x][curr.y] == '\\') || (b.directionX == -1 && grid[curr.x][curr.y] == '/')){
                        //  Move Right
                        beams.add(new Beam(new Point(curr.x, curr.y+1), 0, 1));
                    } else if ((b.directionX == -1 && grid[curr.x][curr.y] == '\\') || (b.directionX == 1 && grid[curr.x][curr.y] == '/')){
                        // Move to Left
                        beams.add(new Beam(new Point(curr.x, curr.y-1), 0, -1));
                    } else if ((b.directionY == 1 && grid[curr.x][curr.y] == '\\') || (b.directionY == -1 && grid[curr.x][curr.y] == '/')){
                        //  Move Down
                        beams.add(new Beam(new Point(curr.x+1, curr.y), 1, 0));
                    } else if ((b.directionY == -1 && grid[curr.x][curr.y] == '\\') || (b.directionY == 1 && grid[curr.x][curr.y] == '/')){
                        // Move to Up
                        beams.add(new Beam(new Point(curr.x-1, curr.y), -1, 0));
                    }
                }

            }
            return points.size();
        }

        private  boolean withinBounds(Character[][] grid, Point p) {
            return p.x >= 0 && p.x < grid.length && p.y >= 0 && p.y < grid[0].length;
        }
    }


    private static Character[][] readInput() {
        try(BufferedReader br = new BufferedReader(new FileReader("src/main/java/com/avverma/_2023/day16/input.txt"))) {
            List<Character[]> lines = new ArrayList<>();
            String line = br.readLine();
            while(line != null) {
                lines.add(line.chars().mapToObj(c ->(char)c).toArray(Character[]::new));
                line = br.readLine();
            }
            return lines.toArray(Character[][]::new);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
