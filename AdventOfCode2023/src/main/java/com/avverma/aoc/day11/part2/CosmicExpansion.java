package com.avverma.aoc.day11.part2;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class CosmicExpansion {

    final static int EXPANSION_FACTOR = 1000000;

    static class Point {
        int x;
        int y;

        Point(int x, int y) { this.x = x; this.y = y;}
    }

    public static void main(String[] args) {
        System.out.println(solve(readInput()));
    }

    private static long solve(Character[][] space) {
        Set<Integer> expandedRows = IntStream.range(0, space.length).boxed().collect(Collectors.toSet());
        Set<Integer> expandedCols = IntStream.range(0, space[0].length).boxed().collect(Collectors.toSet());

        List<Point> galaxies = new ArrayList<>();
        for (int i = 0 ; i < space.length; i++) {
            for (int j = 0 ; j < space[0].length; j++) {
                if (space[i][j] == '#') {
                    galaxies.add(new Point(i, j));

                    expandedRows.remove(i);
                    expandedCols.remove(j);
                }
            }
        }

        long sum = 0;
        for (int i = 0 ; i < galaxies.size(); i++) {
            for (int j = i+1; j < galaxies.size(); j++) {
                Point p1 = galaxies.get(i);
                Point p2 = galaxies.get(j);

                int distance = Math.abs(p1.x - p2.x) + Math.abs(p1.y - p2.y);
                long rowExpansions = expansion(expandedRows, p1.x, p2.x);
                long colExpansions = expansion(expandedCols, p1.y, p2.y);

                sum += distance + rowExpansions + colExpansions;
            }
        }
        return sum;
    }

    private static long expansion(Set<Integer> expandedRows, int a, int b) {
        if (a == b) {
            return 0;
        }

        int x = Math.min(a, b);
        int y = Math.max(a, b);

        long expansions = expandedRows.stream().filter(i -> i >= x && i <= y).count();
        return  (expansions * EXPANSION_FACTOR) - expansions;
    }

    private static Character[][] readInput() {
        try(BufferedReader br = new BufferedReader(new FileReader( "src/main/java/com/avverma/aoc/day11/input.txt"))) {
            String line = br.readLine();
            List<Character[]> lines = new ArrayList<>();
            while (line != null) {
                lines.add(line.chars().mapToObj(c -> (char)c).toArray(Character[]::new));
                line = br.readLine();
            }
            return lines.stream().toArray(Character[][]::new);
        } catch (IOException e) {
            throw  new RuntimeException(e);
        }
    }
}
