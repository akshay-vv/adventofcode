package com.avverma._2024.day14.part2;

import java.io.BufferedReader;
import java.io.FileReader;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

public class RestroomRedoubt {

    class Pair {
        int first;
        int second;

        Pair(int f, int s) {
            this.first = f;
            this.second = s;
        }
    }

    class Robot {
        Pair pos;
        Pair vel;

        Robot(Pair pos, Pair vel) {
            this.pos = pos;
            this.vel = vel;
        }
    }


    List<Robot> robots;

    int W = 101;
    int H = 103;


    long solve() {
        long ans = 0;
        int[] quad = new int[4];
        int[][] grid = new int[H][W];
        for (Robot r : robots) {
            grid[r.pos.second][r.pos.first]++;
        }

        for (int seconds = 1; seconds < 10000; seconds++) {
            for (Robot r : robots) {
                grid[r.pos.second][r.pos.first]--;

                int newX = Math.floorMod(r.pos.first + r.vel.first, W);
                int newY = Math.floorMod(r.pos.second + r.vel.second, H);
                r.pos.first = newX;
                r.pos.second = newY;
                grid[newY][newX]++;
            }

            if (noOverlap(grid)) {
                for (int[] row : grid) {
                    System.out.println(Arrays.toString(row));
                }
                System.out.println(seconds);
                return 0;
            }
        }

        for (int[] row : grid) {
            System.out.println(Arrays.toString(row));
        }
        return Arrays.stream(quad).reduce(1, (a, b) -> a * b);
    }

    boolean noOverlap(int[][] grid) {
        for (int i = 0; i < H; i++) {
            for (int j = 0; j < W; j++) {
                if (grid[i][j] > 1) {
                    return false;
                }
            }
        }
        return true;
    }

    int newPos(int p, int v, int seconds, int boundary) {
        return Math.floorMod(p + v * seconds, boundary);
    }

    public static void main(String[] args) {
        System.out.println(-5 % 10);
        RestroomRedoubt rr = new RestroomRedoubt();
        rr.parseInput();

        System.out.println("\n" + rr.solve());
    }

    private void parseInput() {
        try (BufferedReader br = new BufferedReader(new FileReader("aoc/src/main/java/com/avverma/_2024/day14/input.txt"))) {
            this.robots = new ArrayList<>();
            String line = br.readLine();
            while (line != null) {
                //p=0,4 v=3,-3
                String[] posAndVel = line.split(" ");
                String[] pos = posAndVel[0].split("=")[1].split(",");
                String[] vel = posAndVel[1].split("=")[1].split(",");

                robots.add(
                        new Robot(
                                new Pair(Integer.parseInt(pos[0]), Integer.parseInt(pos[1])),
                                new Pair(Integer.parseInt(vel[0]), Integer.parseInt(vel[1]))
                        ));
                line = br.readLine();
            }
        } catch (Exception e) {
            System.err.println(e);
            throw new RuntimeException(e);
        }
    }
}