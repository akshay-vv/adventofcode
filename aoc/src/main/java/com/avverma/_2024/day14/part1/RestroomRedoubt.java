package com.avverma._2024.day14.part1;

import java.io.BufferedReader;
import java.io.FileReader;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

public class RestroomRedoubt {

    record Pair(int first, int second) {}

    record Robot(Pair pos, Pair vel) {}

    List<Robot> robots;

    int W = 101;
    int H = 103;

    int seconds = 100;

    long solve() {
        long ans = 0;
        int[] quad = new int[4];
        int[][] grid = new int[H][W];
        for (Robot r: robots) {
            int newX = newPos(r.pos.first, r.vel.first, W);
            int newY = newPos(r.pos.second, r.vel.second, H);

            int vm = H/2, hm= W/2;

            if (newX < hm && newY < vm) {
                quad[0]++;
            } else if(newX > hm && newY < vm) {
                quad[1]++;
            } else if (newX < hm && newY > vm) {
                quad[2]++;
            } else if(newX > hm && newY > vm) {
                quad[3]++;
            }
            System.out.printf(r + "%d %d\n", newX, newY);
            grid[newY][newX]++;
        }

        for (int[] row: grid) {
            System.out.println(Arrays.toString(row));
        }
        return Arrays.stream(quad).reduce(1, (a, b) -> a*b);
    }

    int newPos(int p, int v, int boundary) {
//        return ((p+(v*seconds)) % boundary);
        return Math.floorMod(p+v*seconds, boundary);
    }

    public static void main(String[] args) {
        System.out.println(-5%10);
        RestroomRedoubt rr = new RestroomRedoubt();
        rr.parseInput();

        System.out.println("\n" + rr.solve());
    }

    private void parseInput() {
        try (BufferedReader br = new BufferedReader(new FileReader("aoc/src/main/java/com/avverma/_2024/day14/input.txt"))) {
            this.robots = new ArrayList<>();
            String line = br.readLine();
            while(line != null) {
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