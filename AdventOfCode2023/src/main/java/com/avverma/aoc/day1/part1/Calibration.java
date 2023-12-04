package com.avverma.aoc.day1.part1;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class Calibration {
    private static int solve(List<String> input) {
        int sum = 0;

        for (String line : input) {
            int left = 0, right = line.length() - 1;
            boolean lFound = false, rFound = false;
            while(left < line.length() && right >= 0) {
                if (!lFound) {
                    if (Character.isDigit(line.charAt(left))) {
                        lFound = true;
                    } else {
                        left++;
                    }
                }

                if (!rFound) {
                    if (Character.isDigit(line.charAt(right))) {
                        rFound = true;
                    } else {
                        right--;
                    }
                }

                if (lFound && rFound) {
                    sum += (line.charAt(left) - '0')*10 + (line.charAt(right) - '0');
                    break;
                }
            }
        }
        return sum;
    }


    private static List<String> readInput() {
        List<String> input = new ArrayList<>();
        try(BufferedReader reader = new BufferedReader(new FileReader("src/main/java/com/avverma/aoc/day1/input.txt"))) {
            String line = reader.readLine();
            while(line != null) {
                input.add(line);
                line = reader.readLine();
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return input;
    }

    public static void main(String[] args) {
        List<String> input = readInput();
        System.out.println(solve(input));
    }

}
