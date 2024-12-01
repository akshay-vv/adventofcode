package com.avverma._2024.day1.part2;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

public class HistoricalHysteria {
    static class Pair<T, U> {
        T first;
        U second;

        Pair(T first, U second) {
            this.first = first;
            this.second = second;
        }
    }

    private static long similarityScore(Pair<int[], int[]> locations) {
        // Set of location1's for faster lookup
        Set<Integer> loc1 = Arrays.stream(locations.first)
                                  .boxed()
                                  .collect(Collectors.toSet());

        // Frequency of locations2's filtered by locations1's
        Map<Integer, Integer> loc2Frequency = new HashMap<>();
        Arrays.stream(locations.second)
                .filter(loc1::contains)
                .forEach(i -> loc2Frequency.put(i, loc2Frequency.getOrDefault(i, 0) + 1));

        // Increase similarity on every encounter of the number in location1
        return Arrays.stream(locations.first)
                .map(i -> i * loc2Frequency.getOrDefault(i, 0))
                .sum();
    }

    public static void main(String[] args) {
        Pair<int[], int[]> locations = readInput();
        System.out.println(similarityScore(locations));
    }

    private static Pair<int[], int[]> readInput(){
        try(BufferedReader br = new BufferedReader(new FileReader("src/main/java/com/avverma/_2024/day1/input.txt"))) {
            String line = br.readLine();
            List<Integer> loc1 = new ArrayList<>();
            List<Integer> loc2 = new ArrayList<>();
            while(line != null) {
                String[] nums = line.split("   ");
                loc1.add(Integer.valueOf(nums[0]));
                loc2.add(Integer.valueOf(nums[1]));

                line = br.readLine();
            }
            return new Pair<int[], int[]>(loc1.stream().mapToInt(i -> i).toArray(), loc2.stream().mapToInt(i -> i).toArray());
        } catch (IOException e) {
            System.err.println("Parse exception" + e);
        }

        return null;
    }
}
