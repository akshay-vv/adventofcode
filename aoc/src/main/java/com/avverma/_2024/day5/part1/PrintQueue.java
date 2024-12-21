package com.avverma._2024.day5.part1;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.*;
import java.util.stream.Collectors;

public class PrintQueue {

    Map<String, Set<String>> pairs = new HashMap<>();
    List<List<String>> pageUpdates = new ArrayList<>();

   int solve() {
       int sum = 0;
       for(List<String> pageUpdate: pageUpdates) {
            sum += valid(pageUpdate) ? median(pageUpdate) : 0;
       }
       return sum;
   }

   boolean valid(List<String> pageUpdate) {
       for (int i = 0 ; i< pageUpdate.size(); i++) {
           for(int j = i+1; j < pageUpdate.size(); j++) {
               if (!pairs.getOrDefault(pageUpdate.get(i), new HashSet<>()).contains(pageUpdate.get(j))) {
                   return false;
               }
           }
       }

       return true;
   }

   int median(List<String> pageUpdate) {
       int med = Integer.valueOf(pageUpdate.get(
               pageUpdate.size()/2
       ));
       return med;
   }

    public static void main(String[] args) {
        PrintQueue pq = new PrintQueue();
        pq.parseInput();

        System.out.println("\n" + pq.solve());

    }

    private void parseInput() {
        try (BufferedReader br = new BufferedReader(new FileReader("aoc/src/main/java/com/avverma/_2024/day5/input.txt"))) {
            List<Character[]> res = new ArrayList<>();
            String line = br.readLine();
            boolean firstSection = true;
            while (line != null) {
                if (line.equals("")) {
                    firstSection = false;
                }else if(firstSection) {
                    String nodes[] = line.split("\\|");
                    pairs.computeIfAbsent(nodes[0], k -> new HashSet<>()).add(nodes[1]);
                } else {
                    pageUpdates.add(Arrays.stream(line.split(",")).collect(Collectors.toList()));
                }
                line = br.readLine();
            }
        } catch (Exception e) {
            System.err.println(e);
            throw new RuntimeException(e);
        }
    }
}
