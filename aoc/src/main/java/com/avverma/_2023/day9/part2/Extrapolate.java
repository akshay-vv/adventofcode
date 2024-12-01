package com.avverma._2023.day9.part2;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class Extrapolate {
    private static long extrapolateSum(List<String> lines) {
        long sum = 0;
        for (String line: lines) {
            sum += extrapolate(Arrays.stream(line.split(" ")).mapToLong(Long::parseLong).toArray());
        }
        return sum;
    }

    private static long extrapolate(long[] nums) {
        List<Long> extrapolations = new ArrayList<>();
        while(!allZeros(nums)) {
            extrapolations.add(nums[0]);
            long[] next = new long[nums.length-1];
            for (int i = 1 ; i < nums.length; i++) {
                next[i-1] = nums[i] - nums[i-1];
            }
            nums = next;
        }

        Collections.reverse(extrapolations);
        return extrapolations.stream().reduce(0L, (a, b) -> b-a);
    }

    private static boolean allZeros(long[] nums) {
        return Arrays.stream(nums).allMatch(n -> n==0);
    }

    static List<String> readInput() {
        try(BufferedReader br = new BufferedReader(new FileReader("src/main/java/com/avverma/_2023/day9/input.txt"))) {
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
        System.out.println(extrapolateSum(input));
    }
}
