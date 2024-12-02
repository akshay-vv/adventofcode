package com.avverma._2024.day2.part1;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class RedNosedReport {

    private static int safe(int[][] reports) {
        int safe = 0;

        for (int[] levels : reports) {
            boolean prevPositive = true;
            safe++;
            for (int i = 1; i < levels.length; i++) {
                int d = Math.abs(levels[i] - levels[i-1]);
                boolean positive = levels[i] > levels[i-1];
                if (d==0 || d > 3 || (i>1 && prevPositive != positive)) {
                    safe--;
                    break;
                }
                prevPositive = positive;
            }
        }

        return safe;
    }
    public static void main(String[] args) {
        int[][] input = parseInput();
        System.out.println(safe(input));
    }

    private static int[][] parseInput() {
        try(BufferedReader br = new BufferedReader(new FileReader("aoc/src/main/java/com/avverma/_2024/day2/input.txt"))) {
            List<int[]> reports = new ArrayList<>();
            String line = br.readLine();
            while(line != null) {
                int[] levels = Arrays.stream(line.split(" "))
                                .mapToInt(Integer::valueOf).toArray();
                reports.add(levels);
                line = br.readLine();
            }
            int[][] res = new int[reports.size()][];
            return reports.toArray(res);
        } catch (Exception e) {
            System.err.println(e);
            throw new RuntimeException(e);
        }
    }
}
