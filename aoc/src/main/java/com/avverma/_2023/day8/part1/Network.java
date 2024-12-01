package com.avverma._2023.day8.part1;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Network {
    private static int navigate(List<String> input) {
        Map<String, String[]> networkmap = new HashMap<>();
        for (int i = 2; i < input.size(); i++) {
            // AAA = (BBB, CCC)
            String line = input.get(i);
            String[] nodeMap = line.split("=");

            networkmap.put(nodeMap[0].trim(), nodeMap[1].replace("(", "").replace(")", "").replace(" ", "").split(","));
        }

        int[] directions = input.get(0).chars().map(c -> c-'L' == 0 ? 0 : 1).toArray();
        int nDirections = directions.length;

        int steps = 0;
        String curr = "AAA";
        while(!curr.equals("ZZZ")) {
            curr = networkmap.get(curr)[directions[steps++ % nDirections]];
        }
        return steps;
    }


    static List<String> readInput() {
        try(BufferedReader br = new BufferedReader(new FileReader("src/main/java/com/avverma/_2023/day8/input.txt"))) {
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
        System.out.println(navigate(input));
    }
}
