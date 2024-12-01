package com.avverma._2023.day7.part1;

import java.io.*;
import java.util.*;

public class TotalWinnings {
    static String sCards = "AKQJT98765432";
    static Map<Character, Integer> cardStrength = new HashMap<>();

    enum Type {
        REGULAR,
        HIGH_CARD,
        ONE_PAIR,
        TWO_PAIR,
        THREE_OF_A_KIND,
        FULL_HOUSE,
        FOUR_OF_A_KIND,
        FIVE_OF_A_KIND
    }

    static class Hand implements Comparable<Hand>{
        String hand;
        Type type;

        int strength;

        int bid;

        Hand(String hand, int bid) {
            this.hand = hand;
            this.bid = bid;
            this.type = identifyType();
            this.strength = calculateStrength();
        }
        private Type identifyType() {
            Map<Character, Integer> count = new HashMap<>();
            hand.chars().mapToObj(i -> (char)i).forEach(c -> count.merge(c, 1, Integer::sum));
            List<Integer> orderedCounts = count.values().stream().sorted().toList();

            // FIVE_OF_A_KIND     5
            // FOUR_OF_A_KIND     4 1
            // FULL_HOUSE         3 2
            // THREE_OF_A_KIND    3 1 1
            // TWO_PAIR           2 2 1
            // ONE_PAIR           2 1 1 1
            // HIGH_CARD          1 1 1 1 1
            if (orderedCounts.size() == 1) {
                return Type.FIVE_OF_A_KIND;
            }
            if (orderedCounts.size() == 2) {
                return orderedCounts.get(0) == 1 ? Type.FOUR_OF_A_KIND : Type.FULL_HOUSE;
            }

            if (orderedCounts.size() == 3) {
                return orderedCounts.get(2) == 3 ? Type.THREE_OF_A_KIND : Type.TWO_PAIR;
            }

            if (orderedCounts.size() == 4 &&  orderedCounts.get(3) == 2) {
                return Type.ONE_PAIR;
            }

            if (orderedCounts.size() == 5) {
                return Type.HIGH_CARD;
            }

            return Type.REGULAR;
        }

        private int calculateStrength() {
            int strength = 0;
            int posMultiplier = 1;
            for (int i = hand.length()-1 ; i >= 0 ; i--) {
                strength += cardStrength.get(hand.charAt(i))*posMultiplier;
                posMultiplier *= sCards.length();
            }
            return strength;
        }


        @Override
        public int compareTo(Hand other) {
            return Integer.compare(strength, other.strength);
        }
    }
    static EnumMap<Type, List<Hand>> handsByType = new EnumMap<>(Type.class);

    private static long totalWinnings(List<String> input) {
        for (String line: input) {
            String[] card = line.split(" ");
            Hand h = new Hand(card[0], Integer.parseInt(card[1]));
            handsByType.computeIfAbsent(h.type, k -> new ArrayList<>()).add(h);
        }

        long totalWinnings = 0;
        int rank = 1;
        for (Type t : Type.values()) {
            List<Hand> hands = handsByType.getOrDefault(t, new ArrayList<>());
            Collections.sort(hands);
            for (Hand h: hands) {
                totalWinnings += (long)h.bid * rank;
                rank++;
            }
        }
        return totalWinnings;
    }

    private static void initCardStrength() {
        for (int i = sCards.length(); i > 0 ; i--) {
            cardStrength.put(sCards.charAt(sCards.length()-i), i);
        }
    }

    static List<String> readInput() {
        try(BufferedReader br = new BufferedReader(new FileReader("src/main/java/com/avverma/_2023/day7/input.txt"))) {
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
        initCardStrength();
        List<String> input = readInput();
        System.out.println(totalWinnings(input));
    }
}
