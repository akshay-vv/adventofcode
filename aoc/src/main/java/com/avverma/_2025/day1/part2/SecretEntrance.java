package com.avverma._2025.day1.part1.part2;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class SecretEntrance {
    public static void main(String[] args) {
        System.out.printf("\nAnswer %d", new SecretEntrance().solve());
    }

    private int solve() {
        int[] input = readInput();
        int start = 50;

        int counter = 0;
        int signFlipCount = 0;
        int multiplierCount = 0;
        int returnTo0=0;

        for (int num : input) {
            int nextNum = start + num;
            int multiplier = Math.abs((nextNum) / 100);

            int isSignFlip = (nextNum == 0 || start == 0) ? 0 : ((nextNum >>> 31) ^ (start >>> 31));
            if (multiplier > 0) {
                multiplierCount += multiplier;
            } else if(nextNum == 0) {
                // Else if because if we land on 0, it is counted as multiplier, e.g 200
                // In that case we shouldn't count it as return to 0
                returnTo0++;
                counter++;
            }

            if (isSignFlip == 1) {
                signFlipCount++;
                counter++;
            }

            counter += multiplier;
            start = Math.floorMod(nextNum, 100);
        }
        System.out.printf("signFlips %d multiplier %d 0 %d", signFlipCount, multiplierCount, returnTo0);
        return counter;
    }

    private int[] readInput() {
        try(BufferedReader br = new BufferedReader(new FileReader("src/main/java/com/avverma/_2025/day1/part2/input.txt"))) {
            String line = br.readLine();
            List<Integer> in = new ArrayList<>();

            while (line != null) {
                if (line.startsWith("L")) {
                    in.add(-Integer.parseInt(line.substring(1)));
                } else {
                    in.add(Integer.parseInt(line.substring(1)));
                }
                line = br.readLine();
            }
            return in.stream().mapToInt(a -> a).toArray();
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
