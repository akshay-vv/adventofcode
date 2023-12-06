package com.avverma.aoc.day4.part2.part1;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class ScratchCards {

    private static long calculatePoints(List<String> cards) {
        int[] nCards = new int[cards.size()];
        Arrays.fill(nCards, 1);
        for (int i = 0 ; i < cards.size(); i++) {
            String card = cards.get(i);
            //Card 1: 41 48 83 86 17 | 83 86  6 31 17  9 48 53
            String[] winnerAndHand = card.split(":")[1].split("\\|");

            Set<Integer> winners = Arrays.stream(winnerAndHand[0].split(" ")).map(String::trim).filter(s -> !s.isEmpty()).map(Integer::parseInt).collect(Collectors.toSet());
            Set<Integer> hand = Arrays.stream(winnerAndHand[1].split(" ")).map(String::trim).filter(s -> !s.isEmpty()).map(Integer::parseInt).collect(Collectors.toSet());

            winners.retainAll(hand);
            int nWins = winners.size();
            for (int j = 1 ; j <= nWins; j++) {
                nCards[i+j] += nCards[i];
            }
        }

        return Arrays.stream(nCards).reduce(0, Integer::sum);
    }

    static List<String> readInput() {
        try(BufferedReader br = new BufferedReader(new FileReader("src/main/java/com/avverma/aoc/day4/input.txt"))) {
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
        List<String> lines = readInput();
        System.out.println(calculatePoints(lines));
    }

}
