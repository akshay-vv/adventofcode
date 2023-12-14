package com.avverma.aoc.day13.part1;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

public class PointOfIncidence {

    private static long solve(List<Character[][]> patterns) {
        long sum = 0;
        for (Character[][] pattern: patterns) {
            sum += sum(pattern);
        }
        return sum;
    }

    private static int sum(Character[][] pattern) {
        int bestSum = 0;

        int nRow = pattern.length-1;
        int nCol = pattern[0].length-1;
        int beginCol = 0;
        int endCol = nCol;
        int beginRow = 0;
        int endRow = nRow;

        if (endCol % 2 == 0) {
            endCol--;
            beginCol++;
        }

        if (endRow % 2 == 0) {
            endRow--;
            beginRow++;
        }

        // Left wall
        for (int j = endCol; j > 0; j=j-2) {
            if (isColMatch(pattern, 0, j) && isBlockWithinColValid(pattern, 0, j)) {
                return (j/2)+1;
            }
        }

        // Right wall
        for (int j = beginCol; j < nCol; j=j+2) {
            if (isColMatch(pattern, j, nCol) && isBlockWithinColValid(pattern, j, nCol)) {
                return ((nCol - j)/2) + j + 1;
            }
        }

        // Top wall
        for (int i = endRow; i > 0; i=i-2) {
            if (isRowMatch(pattern, 0, i) && isBlockWithinRowValid(pattern, 0, i)) {
                return ((i/2)+1) * 100;
            }
        }

        // Right wall
        for (int i = beginRow; i < nRow; i=i+2) {
            if (isRowMatch(pattern, i, nRow) && isBlockWithinRowValid(pattern, i, nRow)) {
                return (((nRow - i)/2) + i + 1) * 100;
            }
        }

        return bestSum;
    }
    private static boolean isBlockWithinRowValid(Character[][] pattern, int r1, int r2) {
        for (int i=r1+1, j = r2-1; i < j; i++,j--) {
            if (!isRowMatch(pattern, i, j)) {
                return false;
            }
        }
        return true;
    }

    private static boolean isBlockWithinColValid(Character[][] pattern, int c1, int c2) {
        for (int i=c1+1, j = c2-1; i < j; i++,j--) {
            if (!isColMatch(pattern, i, j)) {
                return false;
            }
        }
        return true;
    }


    private static boolean isColMatch(Character[][] pattern, int i, int j) {
        return IntStream.range(0, pattern.length).allMatch(row -> pattern[row][i] == pattern[row][j]);
    }

    private static boolean isRowMatch(Character[][] pattern, int i, int j) {
        return IntStream.range(0, pattern[0].length).allMatch(col -> pattern[i][col] == pattern[j][col]);
    }

    public static void main(String[] args) {
        System.out.println(solve(readInput()));
    }

    private static List<Character[][]> readInput() {
        try(BufferedReader br = new BufferedReader(new FileReader("src/main/java/com/avverma/aoc/day13/input.txt"))) {
            List<Character[][]> patterns = new ArrayList<>();
            List<Character[]> pattern = new ArrayList<>();
            String line = br.readLine();
            while(line != null) {
                if (line.isEmpty()) {
                    patterns.add(pattern.toArray(Character[][]::new));
                    pattern  = new ArrayList<>();
                } else {
                    pattern.add(line.chars().mapToObj(c -> (char)c).toArray(Character[]::new));
                }
                line = br.readLine();
            }
            patterns.add(pattern.toArray(Character[][]::new));
            return patterns;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
