package com.avverma._2023.day6.part2;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Numways {
    private static long numWaysMultiplied(List<String> input) {
        //        Time:      7  15   30
        //        Distance:  9  40  200
        long time = Long.parseLong(input.get(0).split(":")[1].replace(" ", ""));
        long distance = Long.parseLong(input.get(1).split(":")[1].replace(" ", ""));

        long numWays = 0;
        for (int s=1; s < time; s++) {
            long targetDistance = (time-s) * s;
            if ( targetDistance > distance) {
                numWays++;
            }
        }


        return numWays;
    }

    static List<String> readInput() {
        try(BufferedReader br = new BufferedReader(new FileReader("src/main/java/com/avverma/_2023/day6/input.txt"))) {
            List<String> input = new ArrayList<>();
            String line = br.readLine();
            while(line != null) {
                input.add(line);
                line = br.readLine();
            }
            return input;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void main(String[] args) {
        List<String> input = readInput();
        System.out.println(numWaysMultiplied(input));
    }
}
