package com.avverma.aoc.day10.part2;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class PipeMaze {
    int[][] directions = new int[][]{{0,-1}, {0, 1}, {-1, 0}, {1, 0}};
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
        Coordinate start = findFinish(grid);
        Coordinate searchStart = findStart(start, grid);
        Stack<Coordinate> path = new Stack<>();

        path.add(start);

        computePath2(searchStart, start, grid,path);
        return  path.size()/2;
//        return enclosures(grid, path);
    }

    private static long enclosures(Character[][] grid, Stack<Coordinate> path) {
        int result = 0;
        return 0;
    }

    private static void computePath(Coordinate curr, Coordinate end,Character[][]  grid, Stack<Coordinate> path) {
        do {
            int x = curr.x;
            int y = curr.y;
            Coordinate prev = path.peek();
            path.add(curr);

            Coordinate left = new Coordinate(x, y-1);
            Coordinate right = new Coordinate(x, y+1);
            Coordinate up = new Coordinate(x-1, y);
            Coordinate down = new Coordinate(x+1, y);
            if (!prev.equals(left) && "J-7".contains(grid[x][y].toString()) && y > 0 && "SF-L".contains(grid[x][y-1].toString())) {
                curr = left;
            } else if (!prev.equals(right) && "F-L".contains(grid[x][y].toString()) && y < grid[0].length-1 && "SJ-7".contains(grid[x][y+1].toString())) {
                curr = right;
            } else if (!prev.equals(up) && "J|L".contains(grid[x][y].toString()) && x>0 && "S|7F".contains(grid[x-1][y].toString())) {
                curr = up;
            } else if (!prev.equals(down) && "|7F".contains(grid[x][y].toString()) && x < grid.length-1 && "SJ|L".contains(grid[x+1][y].toString())) {
                curr = down;
            }
        } while(!curr.equals(end));
    }

    private static void computePath2(Coordinate curr, Coordinate end,Character[][]  grid, Stack<Coordinate> path) {
        Coordinate next;
        do {
            Coordinate prev = path.peek();
            int x = prev.x;
            int y = prev.y;

            Coordinate left = new Coordinate(x, y-1);
            Coordinate right = new Coordinate(x, y+1);
            Coordinate up = new Coordinate(x-1, y);
            Coordinate down = new Coordinate(x+1, y);
            if (!prev.equals(left) && "J-7".contains(grid[x][y].toString()) && y > 0 && "SF-L".contains(grid[x][y-1].toString())) {
                next = left;
            } else if (!prev.equals(right) && "F-L".contains(grid[x][y].toString()) && y < grid[0].length-1 && "SJ-7".contains(grid[x][y+1].toString())) {
                next = right;
            } else if (!prev.equals(up) && "J|L".contains(grid[x][y].toString()) && x>0 && "S|7F".contains(grid[x-1][y].toString())) {
                next = up;
            } else {
                next = down;
            }
            path.add(next);
        } while(!next.equals(end));
    }

    // Recursive dfs only works for len(grid) < 1k - otherwise stack overflow
    private static boolean dfs(Coordinate curr, Coordinate end,Character[][]  grid, Stack<Coordinate> path, int count) {
        System.out.printf("(%d, %d)\n", curr.x, curr.y);
        if (curr.equals(end)) {
            // Base case
            return true;
        }

        Coordinate top = path.peek();
        path.add(curr);

        int x = curr.x;
        int y = curr.y;

        // Can go left
        Coordinate left = new Coordinate(x, y-1);
        if (!top.equals(left) && "J-7".contains(grid[x][y].toString()) && y > 0 && "SF-L".contains(grid[x][y-1].toString())) {
            if (dfs(left, end, grid, path, count+1))  {
                return true;
            }
        }

        // Can go right
        Coordinate right = new Coordinate(x, y+1);
        if (!top.equals(right) && "F-L".contains(grid[x][y].toString()) && y < grid[0].length-1 && "SJ-7".contains(grid[x][y+1].toString())) {
            if (dfs(right, end, grid, path,count+1))  {
                return true;
            }
        }

        // Can go up
        Coordinate up = new Coordinate(x-1, y);
        if (!top.equals(up) && "J|L".contains(grid[x][y].toString()) && x>0 && "S|7F".contains(grid[x-1][y].toString())) {
            if (dfs(up, end, grid,path, count+1))  {
                return true;
            }
        }

        // Can go down
        Coordinate down = new Coordinate(x+1, y);
        if (!top.equals(down) && "|7F".contains(grid[x][y].toString()) && x < grid.length-1 && "SJ|L".contains(grid[x+1][y].toString())) {
            if (dfs(down, end, grid,path, count+1))  {
                return true;
            }
        }

        path.pop();
        return false;
    }

    private static Coordinate findFinish(Character[][] grid) {
        for (int i = 0 ; i < grid.length; i++) {
            for (int j = 0; j < grid[0].length; j++) {
                if (grid[i][j] == 'S') {
                    return new Coordinate(i, j);
                }
            }
        }
        return null;
    }

    private static Coordinate findStart(Coordinate start, Character[][] grid) {
        int x = start.x;
        int y = start.y;

        // Can go left
        if (y > 0 && "F-L".contains(grid[1][y-1].toString())) {
            return new Coordinate(x, y-1);
        }

        // Can go right
        if (y < grid[0].length-1 && "J-7".contains(grid[x][y+1].toString())) {
            return new Coordinate(x, y+1);
        }

        // Can go up
        if (x>0 && "|7F".contains(grid[x-1][y].toString())) {
            return new Coordinate(x-1, y);
        }

        // Can go down
        if (x < grid.length-1 && "J|L".contains(grid[x+1][y].toString())) {
            return new Coordinate(x+1, y);
        }
        return null;
    }


    static Character[][] readInput() {
        try(BufferedReader br = new BufferedReader(new FileReader("src/main/java/com/avverma/aoc/day10/input.txt"))) {
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
        Character[][] input = readInput();
        System.out.println(farthestDistance(input));
    }
}
