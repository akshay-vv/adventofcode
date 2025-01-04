package com.avverma._2024.day13.part1;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.*;

public class ClawContraption {

    record Xy(int x, int y) {}

    record Puzzle(Xy a, Xy b, Xy answer) {}

    List<Puzzle> puzzles;

    long solve() {

        long ans = 0;
        for (Puzzle p : puzzles) {

            for (int i = 0; i < 101; i++) {
                for (int j = 0; j < 101; j++) {
                    int xSum = p.a.x*i + p.b.x*j;
                    int ySum = p.a.y*i + p.b.y*j;
                    if (xSum == p.answer.x && ySum == p.answer.y) {
                        ans += (i*3 + j*1);
                    }
                }
            }
        }
        return ans;
    }


    public static void main(String[] args) {
        ClawContraption pp = new ClawContraption();
        pp.parseInput();

        System.out.println("\n" + pp.solve());
    }

    private void parseInput() {
        try (BufferedReader br = new BufferedReader(new FileReader("aoc/src/main/java/com/avverma/_2024/day13/input.txt"))) {
            this.puzzles = new ArrayList<>();
            String line = br.readLine();
            while(line != null) {
                if (line.equals("")) {
                    line = br.readLine();
                }
                Xy a = new Xy(Integer.parseInt(line.substring(12, 14)), Integer.parseInt(line.substring(18)));

                line = br.readLine();
                Xy b = new Xy(Integer.parseInt(line.substring(12, 14)), Integer.parseInt(line.substring(18)));

                line = br.readLine();
                String[] xys = line.split(":")[1].trim().split(",");
                Xy ans = new Xy(Integer.parseInt(xys[0].split("=")[1].trim()), Integer.parseInt(xys[1].split("=")[1].trim()));

                puzzles.add(new Puzzle(a, b, ans));
                line = br.readLine();
            }
        } catch (Exception e) {
            System.err.println(e);
            throw new RuntimeException(e);
        }
    }
}