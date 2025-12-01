package com.avverma._2025.day1.part1;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SecretEntrance {
    public static void main(String[] args) {
        System.out.println("Hello World");
        System.out.printf("\nAnswer %d", new SecretEntrance().solve());
    }

    private int solve() {
        int[] input = readInput();
        int start = 50;

        int counter = 0;
        for (int num : input) {
            start = Math.floorMod(start+num, 100);
            System.out.println(start);
            if (start == 0) {
                counter++;
            }
        }
        return counter;
    }

    private int[] readInput() {
        try(BufferedReader br = new BufferedReader(new FileReader("src/main/java/com/avverma/_2025/day1/part1/input.txt"))) {
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
