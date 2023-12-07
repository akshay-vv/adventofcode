package com.avverma.aoc.day5.part2;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class LowestLocation {

    static class Interval {
        long start;
        long end;

        Interval(long s, long e) {this.start = s; this.end = e;}
    }

    static class Range {
        long start;
        long end; // exclusive
        long offset;

        public List<Interval> split(List<Interval> intervals) {
            List<Interval> res = new ArrayList<>();
            for (Interval i : intervals) {
                if (isOverlap(i)) {
                    Interval first = new Interval(Math.min(i.start, start), Math.max(i.start, start));
                    Interval middle = new Interval(Math.max(i.start, start), Math.min(i.end, end));
                    Interval last = new Interval(Math.min(i.end, end), Math.max(i.end, end));

                    if (first.start != first.end && i.start < first.end) {
                        res.add(first);
                    }

                    if (last.start != last.end && i.end > last.start) {
                        res.add(last);
                    }

                    res.add(middle);
                } else {
                    res.add(i);
                }
            }
            return res;
        }

        boolean isOverlap(Interval i) {
            return Math.max(i.start, start) < Math.min(i.end, end);
        }

        public void applyOffset(Interval i) {
            i.start += offset; i.end += offset;
        }
    }

    static class Mapping {
        List<Range> ranges = new ArrayList<>();
        String name; // For debugging

        Mapping(String name) {this.name = name;}

        List<Interval> map(List<Interval> intervals) {
            intervals =  split(intervals);
            applyOffsets(intervals);
            return intervals;
        }

        private void applyOffsets(List<Interval> intervals) {
            for (Interval i : intervals) {
                for (Range r: ranges) {
                    if (r.isOverlap(i)) {
                        r.applyOffset(i);
                        break;
                    }
                }
            }
        }

        private List<Interval> split(List<Interval> intervals) {
            for (Range r: ranges) {
                intervals = r.split(intervals);
            }
            return intervals;
        }
    }

    private static long lowestLocation(List<String> input) {

        // Initialize the chain of mappings
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


        // Parse input and store as intervals
        List<Interval> intervals = new ArrayList<>();
        List<Long> seedRanges = Arrays.stream(input.get(0).split(":")[1].split(" ")).map(String::trim).filter(s -> !s.isEmpty()).map(Long::parseLong).toList();
        for (int i=0 ; i+1 < seedRanges.size(); i+=2) {
            long rangeStart = seedRanges.get(i);
            long rangeEnd = rangeStart + seedRanges.get(i+1);
            intervals.add(new Interval(rangeStart, rangeEnd));
        }

        // Apply mappings to intervals
        for (Mapping m : mappings) {
            intervals = m.map(intervals);
        }

        return intervals.stream().mapToLong(i -> i.start).min().orElse(0);
    }

     static List<String> readInput() {
        try(BufferedReader br = new BufferedReader(new FileReader("src/main/java/com/avverma/aoc/day5/input.txt"))) {
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
        System.out.println(lowestLocation(input));
    }
}