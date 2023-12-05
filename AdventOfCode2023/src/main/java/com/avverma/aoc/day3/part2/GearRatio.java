package com.avverma.aoc.day3.part2;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class GearRatio {

    private static long solve(Character[][] engineSchematic) {
        int m = engineSchematic.length, n = engineSchematic[0].length;
        long gearRationSum = 0;
        for (int i = 0 ; i < m; i++) {
            for (int j = 0 ; j < n; j++) {
                if (isGear((int) engineSchematic[i][j])) {
                    gearRationSum += gearRatio( engineSchematic, i, j, m, n);
                }
            }
        }
        return gearRationSum;
    }

    private static long gearRatio(Character[][] engineSchematic, int i, int j, int m, int n) {
        Set<Long> candidates = new HashSet<>();
        for (int x = Math.max(0, i -1); x <= Math.min(m -1, i +1); x++) {
            for (int y = Math.max(0, j -1); y <= Math.min(n -1, j +1); y++) {
                if (x != i || y != j) { // ignore the special char
                    if ( isDigit((int) engineSchematic[x][y])) {
                        int stepRight = y;
                        do {
                            stepRight++;
                        } while(stepRight < n && isDigit((int) engineSchematic[x][stepRight]));
                        candidates.add((long) x << 32 | (stepRight -1));
                    }
                }
            }
        }

        return candidates.size() == 2 ? candidates.stream().map(e -> (long) numberAtPos(engineSchematic, e)).reduce(1L, (a, b) -> a*b) : 0;
    }

    private static int numberAtPos(Character[][] engineSchematic, long pos) {
        int x = (int) ((pos >> 32) & 0x00000000ffffffffL);
        int y = (int)(pos & 0x00000000ffffffffL);
        int base = 1;
        int num = 0;
        do {
            num += base * (engineSchematic[x][y]- '0');
            base *= 10;
            y--;
        } while(y >= 0  && isDigit((int) engineSchematic[x][y]));

        return num;
    }

    static boolean isDigit(int c) {
        return c >= 48 && c <=57;
    }

    static boolean isGear(int c) {
        return c == 42;
    }

    private static Character[][] readInput() {
//         try(BufferedReader br = new BufferedReader(new FileReader("src/main/java/com/avverma/aoc/day3/example_input.txt"))) {
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
