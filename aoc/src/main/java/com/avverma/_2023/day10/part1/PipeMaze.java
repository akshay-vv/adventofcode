package com.avverma._2023.day10.part1;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class PipeMaze {
    static class Coordinate {
        int x;
        int y;

        Coordinate(int x, int y) {
            this.x = x;
            this.y = y;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Coordinate that = (Coordinate) o;
            return x == that.x && y == that.y;
        }

        @Override
        public int hashCode() {
            return Objects.hash(x, y);
        }
    }

    private static long farthestDistance(Character[][] grid) {
        int nRow = grid.length;
        int nCol = grid[0].length;

        // Initialize maze
        Coordinate start = getStart(grid);
        Queue<Coordinate> q = new LinkedList<>();
        Set<Coordinate> visited = new HashSet<>();
        q.add(start);
        visited.add(start);

        // bfs
        while(!q.isEmpty()) {
            Coordinate pos = q.poll();

            int r = pos.x;
            int c = pos.y;
            Character ch = grid[r][c];


            // Left
            Coordinate left = new Coordinate(r, c-1);
            if (c > 0 &&  "S-7J".contains(ch.toString()) && "S-LF".contains(grid[r][c-1].toString()) && !visited.contains(left)) {
                q.add(left);
                visited.add(left);
            }

            // Right
            Coordinate right = new Coordinate(r, c+1);
            if (c < nCol-1 &&  "S-LF".contains(ch.toString()) && "S-7J".contains(grid[r][c+1].toString()) && !visited.contains(right)) {
                q.add(right);
                visited.add(right);
            }

            // Up
            Coordinate up = new Coordinate(r-1, c);
            if (r > 0 &&  "S|LJ".contains(ch.toString()) && "S|F7".contains(grid[r-1][c].toString()) && !visited.contains(up)) {
                q.add(up);
                visited.add(up);
            }

            // down
            Coordinate down = new Coordinate(r+1, c);
            if (r < nRow-1 &&  "S|F7".contains(ch.toString()) && "S|LJ".contains(grid[r+1][c].toString()) && !visited.contains(down)) {
                q.add(down);
                visited.add(down);
            }
        }

        return visited.size() / 2;
    }

    static Coordinate getStart(Character[][] grid) {
        for (int i = 0 ; i < grid.length; i++) {
            for (int j = 0; j < grid[0].length; j++) {
                if (grid[i][j] == 'S') {
                    return new Coordinate(i, j);
                }
            }
        }
        return  new Coordinate(0,0);
    }

    static Character[][] readInput() {
        try(BufferedReader br = new BufferedReader(new FileReader("src/main/java/com/avverma/_2023/day10/example_input.txt"))) {
            List<Character[]> input = new ArrayList<>();
            String line = br.readLine();
            while(line != null) {
                input.add(line.chars().mapToObj(c -> (char)c).toArray(Character[]::new));
                line = br.readLine();
            }
            return input.toArray(Character[][]::new);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void main(String[] args) {
        System.out.println(System.getProperty("user.dir"));
        Character[][] input = readInput();
        System.out.println(farthestDistance(input));
    }
}
