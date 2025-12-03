package com.avverma._2025.day3.part1;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Lobby {

    public static void main(String[] args) {
        System.out.printf("\nAnswer %d", new Lobby().solve());
    }

    private long solve() {
        List<String> lines = readInput();
        return lines.stream().mapToInt(this::calculateJoltage).peek(System.out::println).sum();
    }

    private int calculateJoltage(String num) {
        int len = num.length();
        int maxLeft = len-2;
        for (int i = maxLeft-1; i >= 0; i--) {
            char c = num.charAt(i);
            if (c >= num.charAt(maxLeft)) {
                maxLeft = i;
            }
        }
        int maxRight = maxLeft+1;
        for (int i = maxRight+1; i < len; i++) {
            char c = num.charAt(i);
            if (c >= num.charAt(maxRight)) {
                maxRight = i;
            }
        }
        return ((num.charAt(maxLeft) - '0') *10) + (num.charAt(maxRight) - '0');
    }
    private List<String> readInput() {
        try(BufferedReader br = new BufferedReader(new FileReader("src/main/java/com/avverma/_2025/day3/part1/input.txt"))) {
            String line = br.readLine();
            List<String> lines = new ArrayList<>();
           while(line != null) {
               lines.add(line);
               line = br.readLine();
           }
           return lines;
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
