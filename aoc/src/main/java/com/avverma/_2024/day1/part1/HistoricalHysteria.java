package com.avverma._2024.day1.part1;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class HistoricalHysteria {
    static class Pair<T, U> {
        T first;
        U second;

        Pair(T first, U second) {
            this.first = first;
            this.second = second;
        }
    }

    private static int distance(Pair<int[], int[]> locations) {
        int[] loc1 = locations.first;
        int[] loc2 = locations.second;

        Arrays.sort(loc1);
        Arrays.sort(loc2);

        int sum=0;
        for (int i = 0 ; i < loc1.length; i++) {
            int distance = Math.abs(loc2[i] - loc1[i]);
            sum += distance;
        }
        return sum;
    }

    public static void main(String[] args) {
        Pair<int[], int[]> locations = readInput();
        System.out.println(distance(locations));
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
