package com.avverma._2024.day12.part2;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.*;
import java.util.stream.Collectors;

public class GardenGroups {

    char[][] grid;

    int[][] dir = {{0, -1}, {-1, 0}, {0, 1}, {1, 0}};

    record Coordinates(int x, int y) {}

    long solve() {

        Set<Coordinates> visited = new HashSet<>();
        long ans = 0;

        return ans;
    }

    public static void main(String[] args) {
        GardenGroups pp = new GardenGroups();
        pp.parseInput();

        System.out.println("\n" + pp.solve());
    }

    private void parseInput() {
        try (BufferedReader br = new BufferedReader(new FileReader("aoc/src/main/java/com/avverma/_2024/day12/input.txt"))) {
            String line = br.readLine();
            List<char[]> gr = new ArrayList<>();
            while (line != null) {
                gr.add(line.toCharArray());
                line = br.readLine();
            }
            grid = gr.toArray(new char[gr.size()][]);
        } catch (Exception e) {
            System.err.println(e);
            throw new RuntimeException(e);
        }
    }
}