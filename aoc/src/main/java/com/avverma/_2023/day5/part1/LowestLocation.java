package com.avverma._2023.day5.part1;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class LowestLocation {

    static class Range {
        long start;
        long end; // exlusive
        long offset;
    }

    static class Mapping {
        List<Range> ranges = new ArrayList<>();
        String name; // For debugging

        Mapping(String name) {this.name = name;}

        long map(long num) {
            for (Range r: ranges) {
                if (num >= r.start && num < r.end) {
                    return num + r.offset;
                }
            }
            return num;
        }
    }

    private static long lowestLocation(List<String> input) {

        List<Mapping> mappings = new ArrayList<>();
        for (int i = 2 ; i < input.size(); i++) {
            String line = input.get(i);
            if (line.endsWith(":")) {
                Mapping m = new Mapping(line.split(" ")[0]);
                while(++i < input.size() && !input.get(i).isEmpty()) {
                    String[] values = input.get(i).split(" ");

                    Range r = new Range();
                    r.start = Long.parseLong(values[1].trim());
                    r.end = Long.parseLong(values[2].trim()) + r.start;
                    r.offset = Long.parseLong(values[0].trim()) - r.start;
                    m.ranges.add(r);
                }
                mappings.add(m);
            }
        }


        long lowestLocation = Long.MAX_VALUE;
        List<Long> seeds = Arrays.stream(input.get(0).split(":")[1].split(" ")).map(String::trim).filter(s -> !s.isEmpty()).map(Long::parseLong).toList();
        for (Long s: seeds) {
            for (Mapping m: mappings) {
                s = m.map(s);
            }
            lowestLocation = Math.min(s, lowestLocation);
        }
        return lowestLocation;
    }
    public static void main(String[] args) {
        List<String> input = readInput();
        System.out.println(lowestLocation(input));
    }


     static List<String> readInput() {
        try(BufferedReader br = new BufferedReader(new FileReader("src/main/java/com/avverma/_2023/day5/input.txt"))) {
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
}
