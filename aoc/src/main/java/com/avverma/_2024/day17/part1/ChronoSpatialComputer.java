package com.avverma._2024.day17.part1;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.*;
import java.util.stream.Collectors;

public class ChronoSpatialComputer {

    long REGISTER_A; // 4
    long REGISTER_B; // 5
    long REGISTER_C; // 6

    int[] SOURCE;
    List<Long> solution = new ArrayList<>();

    int IP = 0;


    public static void main(String[] args) {
        ChronoSpatialComputer rr = new ChronoSpatialComputer();
        rr.parseInput();

        System.out.println("\n" + rr.solve());
    }

    String solve() {

        // 2,4,1,7,7,5,0,3,1,7,4,1,5,5,3,0
        // 2, 4:    B = A % 8  // Last 3 bits of A. n%8 == n&0b111 0..7             B=2 = 010
        // 1, 7     B = B ^ 7  // Flip the lower 3 bits                             B=5 = 101
        // 7, 5     C = A >> B                                                      C=1 =
        // 0, 3     A = A >> 3 // Divide by 2^3                                     A=7
        // 1, 7     B = B ^ 7  // Reset lower 3 bits of B to 0                      B=2
        // 4, 1     B = B ^ C  // Flip B with C's                                   B=C = 1
        // 5, 5     print(B%8) // Print remainder B/8                               0 1
        // 3, 0     Repeat from begin until a==0

        while(IP < SOURCE.length) {
            int op = SOURCE[IP++];
            int operand = SOURCE[IP++];
            switch (op) {
                case 0: REGISTER_A >>= combo(operand); break;
                case 1: REGISTER_B ^= operand; break;
                case 2: REGISTER_B = combo(operand) % 8; break;
                case 3: if (REGISTER_A != 0) { IP = operand;} break;
                case 4: REGISTER_B ^= REGISTER_C; break;
                case 5: solution.add(combo(operand) % 8); break;
                case 6: REGISTER_B = REGISTER_A >> combo(operand); break;
                case 7: REGISTER_C = REGISTER_A >> combo(operand); break;
            }
        }
        System.out.println();
        System.out.println("REGISTER_A: " + REGISTER_A);
        System.out.println("REGISTER_B: " + REGISTER_B);
        System.out.println("REGISTER_C: " + REGISTER_C);

        return String.join(",", solution.stream().map(i -> i.toString()).collect(Collectors.toList()));
    }

    long combo(int operand) {
        if (operand < 4) {
            return operand;
        }

        if (operand == 4) {
            return REGISTER_A;
        }
        if (operand == 5) {
            return REGISTER_B;
        }
        return REGISTER_C;
    }

    void adv() {
        /**
         * The adv instruction (opcode 0) performs division. The numerator is the value in the A register.
         * The denominator is found by raising 2 to the power of the instruction's combo operand.
         * (So, an operand of 2 would divide A by 4 (2^2); an operand of 5 would divide A by 2^B.)
         * The result of the division operation is truncated to an integer and then written to the A register.
         */

        REGISTER_A /= (0x1 << combo(SOURCE[IP++]));
    }

    void bxl() {
        /**
         * The bxl instruction (opcode 1) calculates the bitwise XOR of register B and the instruction's literal operand,
         * then stores the result in register B.
         */
        REGISTER_B ^= SOURCE[IP++];
    }

    void bst() {
        /**
         * The bst instruction (opcode 2) calculates the value of its combo operand modulo 8
         * (thereby keeping only its lowest 3 bits), then writes that value to the B register.
         */

        REGISTER_B = (combo(SOURCE[IP++]) % 8) & 0b111;

    }

    void jnz() {
        /**
         * The jnz instruction (opcode 3) does nothing if the A register is 0.
         * However, if the A register is not zero, it jumps by setting the instruction pointer to the value of its literal operand;
         * if this instruction jumps, the instruction pointer is not increased by 2 after this instruction.
         */
        if (REGISTER_A != 0) {
            IP = SOURCE[IP];
            return;
        }

        IP++;
    }


    void bxc() {
        /**
         *The bxc instruction (opcode 4) calculates the bitwise XOR of register B and register C,
         * then stores the result in register B. (For legacy reasons, this instruction reads an operand but ignores it.)
         */

        REGISTER_B ^= REGISTER_C;
        IP++;
    }

    void out() {
        /**
         *The out instruction (opcode 5) calculates the value of its combo operand modulo 8, then outputs that value.
         * (If a program outputs multiple values, they are separated by commas.)
         */
        long out = combo(SOURCE[IP++]) % 8;

        solution.add(out);
    }


    void bdv() {
        /**
         *The bdv instruction (opcode 6) works exactly like the adv instruction except that the result is stored in the B register.
         * (The numerator is still read from the A register.)
         */
        REGISTER_B = REGISTER_A / (0x1 << combo(SOURCE[IP++]));
    }


    void cdv() {
        /**
         *The cdv instruction (opcode 7) works exactly like the adv instruction except that
         * the result is stored in the C register. (The numerator is still read from the A register.)
         */
        REGISTER_C = REGISTER_A / (0x1 << combo(SOURCE[IP++]));
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