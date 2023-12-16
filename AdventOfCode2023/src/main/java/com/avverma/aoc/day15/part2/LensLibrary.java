package com.avverma.aoc.day15.part2;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class LensLibrary {

    private static long solve(String input) {
        Map<String, Integer>[] boxes = boxes(input);

        long sum = 0;
        for (int i = 0 ; i < boxes.length; i++) {
            Map<String, Integer> box = boxes[i];
            if (box != null && !box.isEmpty()) {
                int slotNumber = 1;
                for(Map.Entry<String, Integer> entry : box.entrySet()) {
                    int focusingPower = i + 1;
                    focusingPower *= slotNumber++;
                    focusingPower *= entry.getValue();
                    sum += focusingPower;
                }
            }
        }
        return sum;
    }

    private static Map<String, Integer>[] boxes(String input) {
        Map<String, Integer>[] boxes = new Map[256];

        String[] sequences = input.split(",");
        for (String seq : sequences) {
            int boxId = 0;
            int focalLength = 0;
            boolean remove = false;

            if(seq.endsWith("-")) {
               seq = seq.substring(0, seq.length()-1);
               remove = true;
            } else {
                String[] sAndFocal = seq.split("=");
                seq = sAndFocal[0];
                focalLength = Integer.parseInt(sAndFocal[1]);
            }

            for (int c = 0 ; c < seq.length(); c++) {
                boxId += seq.charAt(c);
                boxId *= 17;
                boxId = boxId % 256;
            }

            if (remove) {
                remove(boxes[boxId], seq);
            } else {
                add(boxes, boxId, seq, focalLength);
            }
        }
        return boxes;
    }

    private static void remove(Map<String, Integer> box,String label) {
        if (box != null) {
            box.remove(label);
        }
    }

    private static void add(Map<String, Integer>[] boxes, int boxId, String label, Integer focalLength) {
        if (boxes[boxId] == null) {
            Map<String, Integer> labels = new LinkedHashMap<>();
            labels.put(label, focalLength);
            boxes[boxId] = labels;
        } else {
            Map<String, Integer> labels = boxes[boxId];
            if(labels.containsKey(label)) {
                labels.replace(label, focalLength);
            } else {
                labels.put(label, focalLength);
            }
        }
    }

    public static void main(String[] args) {
        System.out.println(solve(readInput()));
    }

    private static String readInput() {
        try(BufferedReader br = new BufferedReader(new FileReader("src/main/java/com/avverma/aoc/day15/input.txt"))) {
            return br.readLine();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
