package com.avverma._2024.day13.part2;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

public class ClawContraption {

    record Xy(long x, long y) {}

    record Puzzle(Xy a, Xy b, Xy answer) {}

    List<Puzzle> puzzles;

    long solve() {

        long ans = 0;
        for (Puzzle p : puzzles) {
            long ax = p.a.x, ay = p.a.y, bx = p.b.x, by = p.b.y, rx = p.answer.x, ry = p.answer.y;

            double nAbutton = (double)(bx*ry - by*rx) / (double)(ay*bx - ax*by);
            double nBbutton = (double)(ay*rx - ax*ry) / (double)(ay*bx - ax*by);

            if (nAbutton%1 == 0 && nBbutton%1 == 0) {
                ans += (long)nAbutton*3 + (long)nBbutton;
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
                Xy ans = new Xy(Long.parseLong(xys[0].split("=")[1].trim())+10000000000000L, Long.parseLong(xys[1].split("=")[1].trim()) + 10000000000000L);

                puzzles.add(new Puzzle(a, b, ans));
                line = br.readLine();
            }
        } catch (Exception e) {
            System.err.println(e);
            throw new RuntimeException(e);
        }
    }
}