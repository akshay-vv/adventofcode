package com.avverma.aoc.day15.part1;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Map;
import java.util.Map;

public class LensLibrary {

    private static long solve(String input) {
        long sum = 0;
        String[] sequences = input.split(",");

        for (String seq : sequences) {
            int currentVal = 0;
            for (int c = 0 ; c < seq.length(); c++) {
                currentVal += (int)seq.charAt(c);
                currentVal *= 17;
                currentVal = currentVal % 256;
            }
            sum += currentVal;
        }
        return sum;
    }

    public static void main(String[] args) {
        System.out.println(solve(readInput()));
    }

    private static String readInput() {
        try(BufferedReader br = new BufferedReader(new FileReader("src/main/java/com/avverma/aoc/day15/examle_input.txt"))) {
            return br.readLine();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
