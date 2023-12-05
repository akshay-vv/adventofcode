package com.avverma.aoc.day3.part1;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class MissingParts {

    static class EligibleCoordinates {
        // Uses long to store 2 ints (x and y)
        Set<Long> coordinates = new HashSet<>();

        public void add(int x, int y) {
            coordinates.add((long)x<<32 | y);
        }

        public boolean contains(int x, int y) {
            return coordinates.contains((long) x << 32 | y);
        }
    }
    private static int solve(Character[][] engineSchematic) {
        EligibleCoordinates eligibles = new EligibleCoordinates();
        markEligibles(eligibles, engineSchematic);

        int sum = 0;
        int m = engineSchematic.length, n = engineSchematic[0].length;

        for (int i = 0 ; i < m; i++) {
            for (int j = n-1; j >= 0; j--) {
                if (eligibles.contains(i, j)) {
                    int base = 1;
                    int num = 0;
                    do {
                        num += base * (engineSchematic[i][j] - '0');
                        base *= 10;
                        j--;
                    } while(j >=0 && isDigit((int) engineSchematic[i][j]));
                    sum += num;
                }
            }
        }

        return sum;
    }

    private static void markEligibles(EligibleCoordinates eligibles, Character[][] engineSchematic) {
        int m = engineSchematic.length, n = engineSchematic[0].length;
        for (int i = 0 ; i < m; i++) {
            for (int j = 0 ; j < n; j++) {
                if (isSpecial((int) engineSchematic[i][j])) {
                    eligibleNeighbour(eligibles, engineSchematic, i, j, m, n);
                }
            }
        }
    }

    private static void eligibleNeighbour(EligibleCoordinates eligibles, Character[][] engineSchematic, int i, int j,int m, int n) {
        for (int x = Math.max(0, i -1); x <= Math.min(m -1, i +1); x++) {
            for (int y = Math.max(0, j -1); y <= Math.min(n -1, j +1); y++) {
                if (x != i || y != j) { // ignore the special char
                    if ( isDigit((int) engineSchematic[x][y])) {
                        int stepRight = y;
                        do {
                            stepRight++;
                        } while(stepRight < n && isDigit((int) engineSchematic[x][stepRight]));
                        eligibles.add(x, stepRight-1);
                    }
                }
            }
        }
    }

    static boolean isDigit(int c) {
        return c >= 48 && c <=57;
    }

    static boolean isDot(int c) {
        return c == 46;
    }

    static boolean isSpecial(int c) {
        return !isDot(c) && !isDigit(c);
    }

    private static Character[][] readInput() {
        // try(BufferedReader br = new BufferedReader(new FileReader("src/main/java/com/avverma/aoc/day3/part1/example_input.txt"))) {
        try(BufferedReader br = new BufferedReader(new FileReader("src/main/java/com/avverma/aoc/day3/input.txt"))) {
            List<Character[]> resultList = new ArrayList<>();
            String line = br.readLine();
            while(line != null) {
                resultList.add(line.chars().mapToObj(c -> (char)c).toArray(Character[]::new));
                line = br.readLine();
            }
            return resultList.toArray(Character[][]::new);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void main(String[] args) {
        Character[][] input = readInput();
        System.out.println(solve(input));
    }
}
