package com.avverma._2025.day2.part1;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class GiftShop {
    public static void main(String[] args) {
        System.out.printf("\nAnswer %d", new GiftShop().solve());
    }


    private long solve() {
        long[][] input = readInput();
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
        if (digits.size() % 2 == 1) {
            // Has to be even number of digits
            return false;
        }


        int half = digits.size() /2;
        for (int j = 0; j < half; j++) {
            if(digits.get(j) != digits.get(j+half)) {
                return false;
            }
        }
        return true;
    }


    private long[][] readInput() {
        try(BufferedReader br = new BufferedReader(new FileReader("src/main/java/com/avverma/_2025/day2/part1/input.txt"))) {
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
}
