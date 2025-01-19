package com.avverma._2024.day17.part2;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.logging.LogManager;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class ChronoSpatialComputer {

    long REGISTER_A; // 4
    long REGISTER_B; // 5
    long REGISTER_C; // 6

    int[] SOURCE;
    List<Integer> solution = new ArrayList<>();

    int IP = 0;

    long combo(int operand) {
        if (operand < 4) return operand;
        if (operand == 4) return REGISTER_A;
        if (operand == 5) return REGISTER_B;
        if (operand == 6) return  REGISTER_C;

        throw new RuntimeException("Unexpected operand: " + operand);
    }


    public static void main(String[] args) {
        ChronoSpatialComputer rr = new ChronoSpatialComputer();
        rr.parseInput();

        System.out.println("\n" + rr.solve());
    }


    long solve() {
        List<Long> nextCandidateAs = new ArrayList<>(List.of(0L));
        for (int pos = SOURCE.length -1 ; pos >= 0 ; pos--) {
            List<Long> candidateAs = nextCandidateAs;
            System.out.printf("%s %s\n",candidateAs, SOURCE[pos]);
            nextCandidateAs = new ArrayList<>();
            for (long a :candidateAs) {
                long candidateA = a << 3;
                for (int i = 0; i < 8; i++) {
                    long nextA = candidateA + i;
                    int[] targetOutput = Arrays.copyOfRange(SOURCE, pos, SOURCE.length);
                    if (Arrays.equals(targetOutput, computer(nextA).stream().mapToInt(n -> n).toArray())) {
                        // Possible solution
                        nextCandidateAs.add(nextA);
                    }
                }
            }
        }
        return nextCandidateAs.stream().mapToLong(i -> i).min().getAsLong();
    }

    List<Integer> computer(long a) {
        IP=0;
        REGISTER_A = a;
        REGISTER_B = 0;
        REGISTER_C = 0;
        solution = new ArrayList<>();

        // 2,4,1,7,7,5,0,3,1,7,4,1,5,5,3,0
        // 2, 4:    B = A % 8  // Last 3 bits of A. n%8 == n&0b111 0..7             B=0
        // 1, 7     B = B ^ 7  // Reset lower 3 bits of B to 0 if 1                 B=7
        // 7, 5     C = A >> B                                                      C=0
        // 0, 3     A = A >> 3 // Divide by 2^3                                     A=0
        // 1, 7     B = B ^ 7  // Reset lower 3 bits of B to 0                      B=0
        // 4, 1     B = B ^ C  // Flip B with C's                                   B=C = 0
        // 5, 5     print(B%8) // Print remainder B/8                               0
        // 3, 0     Repeat from begin until a==0


        while(IP < SOURCE.length) {
            int op = SOURCE[IP++];
            int operand = SOURCE[IP++];
            switch (op) {
                case 0: REGISTER_A >>= combo(operand); break;                   // adv
                case 1: REGISTER_B ^= operand; break;                           // bxl
                case 2: REGISTER_B = combo(operand) % 8; break;                 // bst
                case 3: if (REGISTER_A != 0) { IP = operand;} break;            // jnz
                case 4: REGISTER_B ^= REGISTER_C; break;                        // bxc
                case 5: solution.add((int) (combo(operand) % 8)); break;          // out
                case 6: REGISTER_B = REGISTER_A >> combo(operand); break;       // bdv
                case 7: REGISTER_C = REGISTER_A >> combo(operand); break;       // cdv
            }
        }
        return solution;
    }
    private void parseInput() {
        try (BufferedReader br = new BufferedReader(new FileReader("aoc/src/main/java/com/avverma/_2024/day17/input.txt"))) {
            List<char[]> g = new ArrayList<>();
            String line = br.readLine();
            boolean registers = true;

            while(line != null) {
                if (line.equals("")) {
                    registers = false;
                    line = br.readLine();
                    continue;
                }

                if (registers) {
                    String[] reg = line.split(":");
                    switch (reg[0]) {
                        case "Register A": REGISTER_A = Long.valueOf(reg[1].trim()); break;
                        case "Register B": REGISTER_B = Long.valueOf(reg[1].trim()); break;
                        case "Register C": REGISTER_C = Long.valueOf(reg[1].trim()); break;
                    }
                } else {
                    SOURCE = Arrays.stream(line.split(":")[1].trim().split(",")).map(Integer::valueOf).mapToInt(i -> i).toArray();
                }

                line = br.readLine();
            }
        } catch (Exception e) {
            System.err.println(e);
            throw new RuntimeException(e);
        }
    }
}