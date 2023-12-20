package com.avverma.aoc.day20.part1;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class PulsePropagation {

    static class Pulse {
        String source;
        String destination;
        boolean val;

        public Pulse(String source, String destination, boolean val) {
            this.source = source;
            this.destination = destination;
            this.val = val;
        }
    }

    static class PulseQ {
        private Queue<Pulse> q = new LinkedList<>();
        long highPulseCount = 0;
        long lowPulseCount = 0;

        void offer(Pulse p) {
            if (p.val) {
                highPulseCount++;
            } else {
                lowPulseCount++;
            }
            q.offer(p);
        }

        Pulse poll() {
            return q.poll();
        }

        boolean isEmpty() {
            return q.isEmpty();
        }
    }

    interface Module {
        void process(Pulse p);
    }

    static class Broadcast implements  Module {
        PulseQ q;
        String id;
        List<String> destinations;

        Broadcast(PulseQ q, String id, List<String> destinations) {
            this.q = q;
            this.id = id;
            this.destinations = destinations;
        }

        @Override
        public void process(Pulse p) {
            destinations.forEach(d -> q.offer(new Pulse(id, d, p.val)));
        }
    }

    static class FlipFlop implements  Module {
        PulseQ q;
        String id;
        List<String> destinations;
        boolean state = false;

        FlipFlop(PulseQ q, String id, List<String> destinations) {
            this.q = q;
            this.id = id;
            this.destinations = destinations;
        }

        @Override
        public void process(Pulse p) {
            if (!p.val) {
                state = !state;
                destinations.forEach(d -> q.offer(new Pulse(id, d, state)));
            }
        }
    }

    static class Conjunction implements  Module {
        PulseQ q;
        String id;
        List<String> destinations;
        Map<String, Boolean> inputStates = new HashMap<>();

        Conjunction(PulseQ q, String id, List<String> destinations) {
            this.q = q;
            this.id = id;
            this.destinations = destinations;
        }

        @Override
        public void process(Pulse p) {
            inputStates.put(p.source, p.val);
            boolean nextPulse = !inputStates.values().stream().allMatch(v -> v);
            destinations.forEach(d -> q.offer(new Pulse(id, d, nextPulse)));
        }

        public void setInputState(List<String> inputs) {
            inputs.forEach(is -> inputStates.put(is, false));
        }
    }

    static class Untyped implements  Module {
        @Override
        public void process(Pulse p) {
        }
    }

    private static long countPulse(List<String> lines) {

        //        broadcaster -> a, b, c
        //        %a -> b
        //        %b -> c
        //        %c -> inv
        //        &inv -> a
        PulseQ q = new PulseQ();
        Map<String, List<String>> sources = new HashMap<>();
        Map<String, Module> modules = new HashMap<>();
        List<Conjunction> conjunctions = new ArrayList<>();
        for (String line: lines) {
            String[] ln = line.split("->");
            String modType = ln[0].trim();
            List<String> destinations = Arrays.stream(ln[1].split(",")).map(String::trim).toList();
            if (modType.equals("broadcaster")) {
                modules.put("broadcaster", new Broadcast(q, "broadcaster", destinations));
            } else  if(modType.charAt(0) == '%') {
                String modId = modType.substring(1);
                modules.put(modId, new FlipFlop(q,modId,destinations));
                destinations.forEach(d -> sources.computeIfAbsent(d, k -> new ArrayList<>()).add(modId));
            } else {
                String modId = modType.substring(1);
                Conjunction c = new Conjunction(q,modId,destinations);
                modules.put(modId, c);
                conjunctions.add(c);
                destinations.forEach(d -> sources.computeIfAbsent(d, k -> new ArrayList<>()).add(modId));
            }
        }
        conjunctions.forEach(c -> c.setInputState(sources.get(c.id)));
        Module untyped = new Untyped();
        for (int i = 0 ; i < 1000; i++) {
            Pulse start  = new Pulse("start","broadcaster", false);
            q.offer(start);

            while(!q.isEmpty()) {
                Pulse p = q.poll();
                modules.getOrDefault(p.destination, untyped).process(p);
            }
        }

        return q.lowPulseCount * q.highPulseCount;
    }
    static List<String> readInput() {
        try(BufferedReader br = new BufferedReader(new FileReader("src/main/java/com/avverma/aoc/day20/input.txt"))) {
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
        System.out.println(countPulse(readInput()));
    }


}
