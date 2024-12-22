package com.avverma._2024.day8.part2;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.*;
import java.util.stream.Stream;

public class ResonantCollinearity {

    Map<Character, List<int[]>> antennas = new HashMap<>();
    int H;
    int W;

    record Coordinate(int x, int y) { }

    long solve() {
        Set<Coordinate> antinodes = new HashSet<>();

        for (Map.Entry<Character, List<int[]>> antenna : antennas.entrySet()) {
            Character label = antenna.getKey();
            List<int[]> loc = antenna.getValue();
            for (int a = 0 ; a < loc.size(); a++) {
                for (int b = a+1; b < loc.size(); b++) {
                    int[] near;
                    int[] farther;
                    if (loc.get(a)[0] + loc.get(a)[1] < loc.get(b)[0] + loc.get(b)[1]) {
                        near = loc.get(a);
                        farther = loc.get(b);
                    } else {
                        near = loc.get(b);
                        farther = loc.get(a);
                    }

                    int dNearX = near[0] - farther[0], dNearY = near[1]-farther[1];
                    int[] antinodeC  = new int[]{near[0] + dNearX, near[1] + dNearY};
                    while(withinBounds(antinodeC)) {
                        antinodes.add(new Coordinate(antinodeC[0], antinodeC[1]));
                        antinodeC = new int[]{antinodeC[0] + dNearX, antinodeC[1] + dNearY};
                    }

                    int dFartherX = farther[0] - near[0], dFartherY = farther[1] - near[1];
                    int[] antinodeD  = new int[]{farther[0] + dFartherX, farther[1] + dFartherY};
                    while(withinBounds(antinodeD)) {
                        antinodes.add(new Coordinate(antinodeD[0], antinodeD[1]));
                        antinodeD = new int[]{antinodeD[0] + dFartherX, antinodeD[1] + dFartherY};
                    }

                    antinodes.add(new Coordinate(near[0], near[1]));
                    antinodes.add(new Coordinate(farther[0], farther[1]));

                }
            }
        }
        return antinodes.size();
    }

    boolean withinBounds(int[] node) {
        return node[0] >= 0 && node[0] < H && node[1] >=0 && node[1] < W;
    }

    public static void main(String[] args) {
        ResonantCollinearity rc = new ResonantCollinearity();
        rc.parseInput();

        System.out.println("\n" + rc.solve());

    }

    private void parseInput() {
        try (BufferedReader br = new BufferedReader(new FileReader("aoc/src/main/java/com/avverma/_2024/day8/input.txt"))) {
            String line = br.readLine();
            antennas = new HashMap<>();
            int i = 0;
            while (line != null) {
                for (int j = 0 ; j < line.length(); j++) {
                    if (line.charAt(j) != '.') {
                        antennas.computeIfAbsent(line.charAt(j), k -> new ArrayList<>()).add(new int[]{i, j});
                    }
                }
                i++;
                W = line.length();
                line = br.readLine();
            }
            H = i;
        } catch (Exception e) {
            System.err.println(e);
            throw new RuntimeException(e);
        }
    }
}
