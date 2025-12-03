package com.avverma._2025.day2.part2;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class GiftShop {
    Map<Integer, int[]> factors;
    public static void main(String[] args) {
        System.out.printf("\nAnswer %d", new com.avverma._2025.day2.part2.GiftShop().solve());
    }


    private long solve() {
        long[][] input = readInput();
        computeFactors();
        long sum = 0;
        for (long[] range : input) {
            for(long i = range[0]; i <= range[1]; i++) {
                if (isRepeat(i)) {
                    sum += i;
                }
            }
        }
        return sum;
    }

    private boolean isRepeat(long i) {
        List<Integer> digits = new ArrayList<>();
        while(i > 0) {
            digits.add((int)(i%10));
            i = i/10;
        }
        int[] fact = factors.get(digits.size());
        for (int f : fact) {
            if(isFactorRepeat(f, digits)) {
                return true;
            }
        }
        return false;
    }

    private static boolean isFactorRepeat(int f, List<Integer> digits) {
        for (int start = 0; start < f; start++) {
            for (int pos=start+f; pos<digits.size(); pos=pos+f) {
                if (!digits.get(start).equals(digits.get(pos))) {
                    return false;
                }
            }

        }
        System.out.println(digits.stream().map(String::valueOf).collect(Collectors.joining()));
        return true;
    }


    private long[][] readInput() {
        try(BufferedReader br = new BufferedReader(new FileReader("src/main/java/com/avverma/_2025/day2/part2/input.txt"))) {
            String line = br.readLine();
            String[] pairs = line.split(",");
            List<long[]> ranges = new ArrayList<>();
            for (String pair : pairs) {
                String[] r = pair.split("-");
                long[] range = {Long.parseLong(r[0]), Long.parseLong(r[1])};
                ranges.add(range);
            }
            return ranges.toArray(long[][]::new);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    private void computeFactors() {
        factors = new HashMap<>();

        for (int i = 1; i < 20; i++) {
            final int k = i;
            factors.put(i, IntStream.range(1, 19).filter(j -> k!=j && k%j==0).toArray());
        }
    }
}
