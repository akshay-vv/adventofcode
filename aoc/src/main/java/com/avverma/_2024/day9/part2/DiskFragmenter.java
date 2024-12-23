package com.avverma._2024.day9.part2;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.IntStream;
import java.util.stream.LongStream;

public class DiskFragmenter {

    record Block(int start, int size) {}
    Map<Integer, Block> files = new HashMap<>();
    List<Block> free = new ArrayList<>();
    String input;
    long solve() {
        int pos = 0;
        for(int i=0; i < input.length(); i++) {
            int size = input.charAt(i) - '0';
            if(i % 2 == 0) {
                files.put(i/2, new Block(pos, size));
            } else {
                if (size != 0) {
                    free.add(new Block(pos, size));
                }
            }
            pos += size;
        }
        int fid = files.size() - 1;

        while(fid >= 0) {
            for (int i = 0 ; i < free.size(); i++) {
                if (free.get(i).start >= files.get(fid).start) {
                    // Gone past the file
                    break;
                }
                if (free.get(i).size == files.get(fid).size) {
                    files.put(fid, new Block(free.get(i).start, free.get(i).size));
                    free.remove(i);
                    break;
                } else if (free.get(i).size > files.get(fid).size) {
                    int fileSize = files.get(fid).size;
                    files.put(fid, new Block(free.get(i).start, fileSize));
                    free.set(i, new Block(free.get(i).start + fileSize, free.get(i).size - fileSize));
                    break;
                }
            }
            fid--;
        }

        return files.entrySet().stream()
                .mapToLong(e -> LongStream.range(e.getValue().start, e.getValue().start + e.getValue().size)
                                .map(i -> i*e.getKey()).sum()).sum();
    }

    void allocate(int[] disk,int start,int end,int val) {
        for (int i=start; i<end; i++) {
            disk[i] = val;
        }
    }


    public static void main(String[] args) {
        DiskFragmenter rc = new DiskFragmenter();
        rc.parseInput();

        System.out.println("\n" + rc.solve());

    }

    private void parseInput() {
        try (BufferedReader br = new BufferedReader(new FileReader("aoc/src/main/java/com/avverma/_2024/day9/input.txt"))) {
            this.input = br.readLine();
        } catch (Exception e) {
            System.err.println(e);
            throw new RuntimeException(e);
        }
    }
}