package com.avverma.aoc.day19.part1;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Aplenty {
    static class Input{
        List<String> workflows;
        List<String> parts;

        public Input(List<String> workflows, List<String> parts) {
            this.workflows = workflows;
            this.parts = parts;
        }
    }

    static class Condition {
        char variable;
        char operator;

        int value;

        String nextWorkflow;

        public Condition(String line) {
            String[] c = line.split(":");
            nextWorkflow = c[1];
            variable = c[0].charAt(0);
            operator = c[0].charAt(1);
            value = Integer.parseInt(c[0].substring(2));
        }

        boolean match(char variable, int value) {
            if (this.variable == variable) {
                switch (operator) {
                    case '<':
                        return value < this.value;
                    case '>':
                        return value > this.value;
                }
            }
            return false;
        }

        String next() {
            return nextWorkflow;
        }
    }
    static class Workflow{
        List<Condition> conditions = new ArrayList<>();
        String finalWorkflow;

        Workflow(String[] conditions) {
            for (int i = 0 ; i < conditions.length-1; i++) {
                this.conditions.add(new Condition(conditions[i]));
            }
            this.finalWorkflow = conditions[conditions.length-1];
        }

        String next(Part part) {
            for(Condition c : conditions) {
                for(Map.Entry<Character, Integer> entry : part.ratings.entrySet()) {
                    if (c.match(entry.getKey(), entry.getValue())) {
                        return c.next();
                    }
                }
            }
            return finalWorkflow;
        }
    }

    static class Part {
        Map<Character, Integer> ratings = new HashMap<>();
        Part(String line) {
            line = line.substring(1, line.length()-1);
            String[] ratings = line.split(",");
            for (String rating: ratings) {
                String[] r = rating.split("=");
                this.ratings.put(r[0].charAt(0), Integer.parseInt(r[1]));
            }
        }
    }

    public static void main(String[] args) {
        System.out.println(totalRatings(readInput()));

    }

    private static long totalRatings(Input input) {
        List<String> flowLines = input.workflows;
        Map<String, Workflow> workflows= new HashMap<>();

        for (String line: flowLines) {
            String[] l = line.split("\\{");
            String conditions = l[1].substring(0, l[1].length()-1);
            workflows.put(l[0], new Workflow(conditions.split(",")));
        }

        List<String> partLines = input.parts;
        List<Part> parts= new ArrayList<>();
        for (String line: partLines) {
            parts.add(new Part(line));
        }

        long sum = 0;
        for (Part p: parts) {
            String next = workflows.get("in").next(p);
            while(!next.equals("A") && !next.equals("R")) {
                next = workflows.get(next).next(p);
            }
            if (next.equals("A")) {
                sum += p.ratings.values().stream().mapToInt(Integer::intValue).sum();
            }
        }
        return sum;
    }

    static Input readInput() {
        try(BufferedReader br = new BufferedReader(new FileReader("src/main/java/com/avverma/aoc/day19/input.txt"))) {
            List<String> workflows = new ArrayList<>();
            List<String> parts = new ArrayList<>();
            boolean isPart = false;
            String line = br.readLine();
            while(line != null) {
                if (line.isEmpty()) {
                    isPart = true;
                } else if(!isPart) {
                    workflows.add(line);
                } else {
                    parts.add(line);
                }
                line = br.readLine();
            }
            return new Input(workflows, parts);
        } catch ( Exception e) {
            throw new RuntimeException(e);
        }
    }
}
