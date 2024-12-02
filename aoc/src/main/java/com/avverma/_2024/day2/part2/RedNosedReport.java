package com.avverma._2024.day2.part2;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class RedNosedReport {

    private static int safe(List<List<Integer>> reports) {
        int safe = 0;

        for (List<Integer> levels: reports) {
            if(safe(levels, 1)) {
                safe++;
            }
        }
        return safe;
    }

    private static boolean safe(List<Integer> levels, int dampner) {
        if (dampner < 0 ) {
            return false;
        }
        boolean prevPositive = true;

        for (int i = 1; i < levels.size(); i++) {
            int d = Math.abs(levels.get(i) - levels.get(i-1));
            boolean positive = levels.get(i) > levels.get(i-1);
            if (d==0 || d > 3 || (i>1 && prevPositive != positive)) {
                return safe(listWithoutIndex(levels, i-1), dampner-1) || safe(listWithoutIndex(levels, i), dampner-1) || safe(listWithoutIndex(levels, i+1), dampner-1);
            }
            prevPositive = positive;
        }
        return true;
    }

    private static List<Integer> listWithoutIndex(List<Integer> levels, final int i) {
        return IntStream.range(0, levels.size()).filter(n -> n != i - 1).mapToObj(n -> levels.get(n)).collect(Collectors.toList());
    }

    public static void main(String[] args) {
        List<List<Integer>> reports = parseInput();
        System.out.println(safe(reports));
    }

    private static List<List<Integer>> parseInput() {
        try(BufferedReader br = new BufferedReader(new FileReader("aoc/src/main/java/com/avverma/_2024/day2/input.txt"))) {
            List<List<Integer>> reports = new ArrayList<>();
            String line = br.readLine();
            while(line != null) {
                List<Integer> levels = Arrays.stream(line.split(" ")).map(Integer::valueOf).collect(Collectors.toList());
                reports.add(levels);
                line = br.readLine();
            }
            return reports;
        } catch (Exception e) {
            System.err.println(e);
            throw new RuntimeException(e);
        }
    }
}
