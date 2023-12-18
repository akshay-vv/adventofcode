package com.avverma.aoc.day10.part2;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

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

    private static int enclosures(Character[][] grid) {
        Coordinate start = findS(grid);
        Set<Coordinate> path = new HashSet<>();

        path.add(start);

        computePath(start, grid, path);
        deriveS(start, grid);

        paintPath(grid, path);
        print(grid);

        return enclose(grid);
    }

    private static void deriveS(Coordinate start, Character[][] grid) {
        Set<Character> candidateS = Stream.of('L', 'J','7', 'F', '-', '|').collect(Collectors.toSet());
        if (start.y> 0 && "F-L".contains(grid[start.x][start.y-1].toString()) ) {
            candidateS.retainAll(Stream.of( 'J','-','7').collect(Collectors.toSet()));
        }

        if (start.y < grid[0].length-1 && "J-7".contains(grid[start.x][start.y+1].toString()) ) {
            candidateS.retainAll(Stream.of( 'L', 'F', '-').collect(Collectors.toSet()));
        }

        if (start.x>0 && "|7F".contains(grid[start.x-1][start.y].toString()) ) {
            candidateS.retainAll(Stream.of( 'J', '|', 'L').collect(Collectors.toSet()));
        }


        if (start.x< grid.length-1 && "J|L".contains(grid[start.x+1][start.y].toString()) ) {
            candidateS.retainAll(Stream.of( '|', '7', 'F').collect(Collectors.toSet()));
        }

        grid[start.x][start.y] = candidateS.stream().findAny().get();
    }

    private static int enclose(Character[][] grid) {
        int enclosedCount = 0;
        for (Character[] characters : grid) {
            boolean open = false;
            int count = 0;
            char lastIncoming = '.';
            for (int j = 0; j < grid[0].length; j++) {
                char c = characters[j];

                if (c == '|') {
                    open = !open;
                } else if (c == '.') {
                    if (open) count++;
                } else if (c == 'F' || c == 'L') {
                    lastIncoming = c;

                } else if (lastIncoming == 'F' && c == 'J') {
                    open = !open;

                } else if (lastIncoming == 'L' && c == '7') {
                    open = !open;
                }
                //      } else if (lastIncoming == 'L' && c == 'J') {  Squeeze - Does not change open
                //      } else if (lastIncoming == 'F' && c == '7') {  Squeeze
            }
            enclosedCount += count;
        }

       return enclosedCount;
    }

    private static void paintPath(Character[][] grid, Set<Coordinate> path) {
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[0].length; j++) {
                if (!path.contains(new Coordinate(i, j))) {
                    grid[i][j] = '.';
                }
            }
        }
    }

    private static void print(Character[][] grid) {
        System.out.println("\n");
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[0].length; j++) {
                System.out.printf("%c", grid[i][j]);
            }
            System.out.println();
        }
    }

    private static void computePath(Coordinate start,Character[][]  grid, Set<Coordinate> path) {
        Coordinate previous = new Coordinate(-1, -1);
        Coordinate curr = start;
        do {
            int x = curr.x;
            int y = curr.y;
            path.add(curr);

            Coordinate left = new Coordinate(x, y-1);
            Coordinate right = new Coordinate(x, y+1);
            Coordinate up = new Coordinate(x-1, y);
            Coordinate down = new Coordinate(x+1, y);
            if (!previous.equals(left) && "SJ-7".contains(grid[x][y].toString()) && y > 0 && "SF-L".contains(grid[x][y-1].toString())) {
                previous = curr;
                curr = left;
            } else if (!previous.equals(right) && "SF-L".contains(grid[x][y].toString()) && y < grid[0].length-1 && "SJ-7".contains(grid[x][y+1].toString())) {
                previous = curr;
                curr = right;
            } else if (!previous.equals(up) && "SJ|L".contains(grid[x][y].toString()) && x>0 && "S|7F".contains(grid[x-1][y].toString())) {
                previous = curr;
                curr = up;
            } else if (!previous.equals(down) && "S|7F".contains(grid[x][y].toString()) && x < grid.length-1 && "SJ|L".contains(grid[x+1][y].toString())) {
                previous = curr;
                curr = down;
            }
        } while(!curr.equals(start));
    }


    private static Coordinate findS(Character[][] grid) {
        for (int i = 0 ; i < grid.length; i++) {
            for (int j = 0; j < grid[0].length; j++) {
                if (grid[i][j] == 'S') {
                    return new Coordinate(i, j);
                }
            }
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
        System.out.println(enclosures(input));
    }
}
