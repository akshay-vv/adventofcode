package com.avverma.aoc.day18.part1;

import java.io.BufferedReader;
import java.io.FileReader;
import  java.util.List;
import  java.util.ArrayList;
import java.util.Stack;

public class LavaductLagoon {

    static class Point{
        int x;
        int y;

        public Point(int x, int y) {
            this.x = x;
            this.y = y;
        }
    }
    static class ExploreResult {
        int nRow;
        int nCol;

        int startX;
        int startY;

        public ExploreResult(int nRow, int nCol, int startX, int startY) {
            this.nRow = nRow;
            this.nCol = nCol;
            this.startX = startX;
            this.startY = startY;
        }
    }

    static int[][] directions = new int[][]{{0, 1}, {0, -1}, {1, 0}, {-1, 0}};
    private static int trenchVolume(List<String> lines) {
        ExploreResult result = dimensions(lines);


        char[][] grid = new char[result.nRow][result.nCol];
        dig(grid, lines, result);

        fillEmpty(grid);

        print(grid);
        return area(grid);
    }

    private static int area(char[][] grid) {
        int count=0;
        for (char[] row : grid) {
            for (char c : row) {
                if (c != '.') {
                    count++;
                }
            }
        }
        return count;
    }

    private static void fillEmpty(char[][] grid) {
        for (int i = 0 ; i < grid.length; i++) {
            floodFill(grid, i, 0);
            floodFill(grid, i, grid[0].length-1);
        }

        for (int i = 0 ; i < grid[0].length; i++) {
            floodFill(grid, 0, i);
            floodFill(grid, grid.length-1, i);
        }
    }

    private static void floodFill(char[][] grid, int i, int j) {
        Stack<Point> points = new Stack<>();
        if (grid[i][j] != 0) {
            return;
        }
        points.add(new Point(i, j));

        while(!points.isEmpty()) {
            Point p = points.pop();
            grid[p.x][p.y] = '.';
            for(int[] dir: directions) {
                Point next = new Point(p.x+dir[0], p.y + dir[1]);
                if (next.x >= 0 && next.y >= 0 && next.x < grid.length && next.y < grid[0].length && grid[next.x][next.y] == 0) {
                    points.add(next);
                }
            }
        }
    }

    private static void print(char[][] grid) {
        for(char[] row: grid) {
            for (char c: row) {
                System.out.printf("%c", c);
            }
            System.out.println();
        }
    }

    private static void dig(char[][] grid, List<String> lines, ExploreResult result) {
        grid[result.startX][result.startY] = '#';
        int rowIdx = result.startX;
        int colIdx = result.startY;
        for (String line: lines) {
            String[] l = line.split(" ");
            switch (l[0]) {
                case "R" -> {
                    int nCols = Integer.parseInt(l[1]);
                    while (nCols-- > 0) {
                        grid[rowIdx][++colIdx] = '#';
                    }
                }
                case "L" -> {
                    int nCols = Integer.parseInt(l[1]);
                    while (nCols-- > 0) {
                        grid[rowIdx][--colIdx] = '#';
                    }
                }
                case "D" -> {
                    int nRows = Integer.parseInt(l[1]);
                    while (nRows-- > 0) {
                        grid[++rowIdx][colIdx] = '#';
                    }
                }
                default -> {
                    // U
                    int nRows = Integer.parseInt(l[1]);
                    while (nRows-- > 0) {
                        grid[--rowIdx][colIdx] = '#';
                    }
                }
            }
        }
    }

    private static ExploreResult dimensions(List<String> lines) {
        int rowPos = 0;
        int colPos = 0;
        int rowNeg = 0;
        int colNeg = 0;

        int row = 0;
        int col = 0;
        for (String line: lines) {
            String[] l = line.split(" ");
            switch (l[0]) {
                case "R" -> {
                    col += Integer.parseInt(l[1]);
                    colPos = Math.max(colPos, col);
                }
                case "L" -> {
                    col -= Integer.parseInt(l[1]);
                    colNeg = Math.min(colNeg, col);
                }
                case "D" -> {
                    row += Integer.parseInt(l[1]);
                    rowPos = Math.max(rowPos, row);
                }
                default -> {
                    // U
                    row -= Integer.parseInt(l[1]);
                    rowNeg = Math.min(rowNeg, row);
                }
            }
        }
        return new ExploreResult(rowPos - rowNeg + 1, colPos - colNeg + 1, Math.abs(rowNeg), Math.abs(colNeg));
    }

    public static void main(String[] args) {
        System.out.println(trenchVolume(readInput()));
    }

    private static List<String> readInput() {
        try(BufferedReader br = new BufferedReader(new FileReader("src/main/java/com/avverma/aoc/day18/input.txt"))) {
            List<String> lines = new ArrayList<>();
            String line = br.readLine();
            while(line != null) {
                lines.add(line);
                line = br.readLine();
            }
            return lines;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
