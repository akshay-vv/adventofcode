package com.avverma._2024.day9.part1;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.*;

public class DiskFragmenter {

    String input;
    long solve() {
        int tot = 0;
        for (int i = 0 ; i < input.length(); i++) {
            tot += input.charAt(i) - '0';

        }

        // Allocate disk
        int[] disk = new int[tot];

        int i = 0;
        int diskStart = 0;
        while (i < input.length()) {
            int n = input.charAt(i)-'0';
            if(i%2 == 0) {
                // file
                allocate(disk, diskStart, diskStart+n, i/2);
            } else {
                // free
                allocate(disk, diskStart, diskStart+n, -1);
            }
            diskStart += n;
            i++;
        }

        // Defragment
        // Two pointer technique swap from the right
        int l=0, r=disk.length-1;
        while(l < r) {
            while(disk[r] == -1) {
                r--;
            }

            while(disk[l] != -1) {
                l++;
            }

            if (l >= r) {
                break;
            }

            int t = disk[l];
            disk[l] = disk[r];
            disk[r] = t;
            l++;
            r--;
        }

        long checksum = 0;
        for (i = 0; i < disk.length; i++) {
            if (disk[i] != -1) {
                checksum += disk[i]*i;
            }
        }
        return checksum;
    }

    void allocate(int[] disk,int start,int end,int val) {
        for (int i=start; i<end; i++) {
            disk[i] = val;
        }
    }


    public static void main(String[] args) {
        DiskFragmenter rc = new DiskFragmenter();
        rc.parseInput();

        System.out.println("\n" + rc.solve());

    }

    private void parseInput() {
        try (BufferedReader br = new BufferedReader(new FileReader("aoc/src/main/java/com/avverma/_2024/day9/input.txt"))) {
            this.input = br.readLine();
        } catch (Exception e) {
            System.err.println(e);
            throw new RuntimeException(e);
        }
    }
}