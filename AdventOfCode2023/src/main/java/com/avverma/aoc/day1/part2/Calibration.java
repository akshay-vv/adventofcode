package com.avverma.aoc.day1.part2;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static java.util.Map.entry;

public class Calibration {
    final private static Map<String, Integer> numbers = Map.ofEntries(
            entry("one", 1),
            entry("two", 2),
            entry("three", 3),
            entry("four", 4),
            entry("five", 5),
            entry("six", 6),
            entry("seven", 7),
            entry("eight", 8),
            entry("nine", 9)
    );

    private static int solve(List<String> input) {
        int sum = 0;

        for (String line : input) {
            int left = 0, right = line.length() - 1;
            int lVal = 0, rVal = 0;
            boolean lFound = false, rFound = false;
            while(left < line.length() && right >= 0) {
                if (!lFound) {
                    if (Character.isDigit(line.charAt(left))) {
                        lFound = true;
                        lVal = (line.charAt(left) - '0');
                    } else if ((lVal = spelledOut(line, left)) != -1) {
                        lFound = true;
                    } else {
                        left++;
                    }
                }

                if (!rFound) {
                    if (Character.isDigit(line.charAt(right))) {
                        rFound = true;
                        rVal = (line.charAt(right) - '0');
                    } else if ((rVal = spelledOut(line, right)) != -1) {
                        rFound = true;
                    } else {
                        right--;
                    }
                }

                if (lFound && rFound) {
                    sum += lVal*10 + rVal;
                    break;
                }
            }
        }
        return sum;
    }

    private static int spelledOut(String line, int offset) {
        for (Map.Entry<String, Integer> entry : numbers.entrySet()) {
            if (line.startsWith(entry.getKey(), offset)) {
                return entry.getValue();
            }
         }
        return -1;
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
