package com.avverma._2024.day3.part2;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

public class MulItOver {
    String input;
    int current=0;

    long sum=0;

    boolean _do = true;

    MulItOver(String input, boolean _do) {
        this.input = input;
        this._do = _do;
    }
    private long result() {
        while(!isAtEnd()) {
            boolean anyMatch = false;
            if(_do) {
                anyMatch = anyMatch || matchMul();
                anyMatch = anyMatch || matchDont();
            } else {
                anyMatch = anyMatch || matchDo();
            }
            if (!anyMatch) current++;
        }
        return sum;
    }

    private boolean matchDont(){
       if(match("don't()")) {
           _do=false;
           return true;
       }
       return false;
    }

    private boolean matchDo(){
        if(match("do()")) {
            _do=true;
            return true;
        }
        return false;
    }

    private boolean matchMul() {
       if(match("mul(")) {
            long op1 = matchOp(',');
            if (op1 != -1) {
                long op2 = matchOp(')');
                if (op2 != -1) {
                    sum += op1*op2;
                    return true;
                }
            }
        }
        return false;
    }

    private boolean match(String text) {
        int len = text.length();
        String s = input.substring(current, Math.min(current+len, input.length()));
        if (s.equals(text)) {
            current += len;
            return true;
        }
        return false;
    }

    private int matchOp(char end) {
        String digits = "";
        while(!isAtEnd() && isDigit(peek())) {
            digits += advance();
        }
        if(peek() == end) {
            if (digits.length() > 0) {
                // consume the end char
                advance();
                return Integer.valueOf(digits);
            }
        }

        return -1;
    }

    private char peek() {
        return input.charAt(current);
    }


    private boolean isDigit(char c) {
        return c >= '0' && c <= '9';
    }

    private char advance() {
        return input.charAt(current++);
    }

    private boolean isAtEnd() {
        return current >= input.length();
    }
    public static void main(String[] args) {
        List<String> input = parseInput();
        long sum = 0;
        boolean _do = true;
        for (String line : input) {
            MulItOver m = new MulItOver(line, _do);
            sum += m.result();
            _do = m._do;
        }
        System.out.println(sum);
    }

    private static List<String> parseInput() {
        try(BufferedReader br = new BufferedReader(new FileReader("aoc/src/main/java/com/avverma/_2024/day3/input.txt"))) {
            List<String> res = new ArrayList<>();
            String line = br.readLine();
            while(line!=null) {
                res.add(line);
                line = br.readLine();
            }
            return res;
        } catch (Exception e) {
            System.err.println(e);
            throw new RuntimeException(e);
        }
    }
}
