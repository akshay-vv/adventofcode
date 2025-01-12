package com.avverma._2024.day15.part2;

import javax.swing.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class WarehouseWoes {

    char[][] grid;
    List<Character> movements;

    record Box(int[] left, int[] right) {
        @Override
        public boolean equals(Object obj) {
            if (this == obj) return true;
            if (obj == null || getClass() != obj.getClass()) return false;

            Box other = (Box) obj;
            return Arrays.equals(left, other.left) && Arrays.equals(right, other.right);
        }

        @Override
        public int hashCode() {
            return 31 * Arrays.hashCode(left) + Arrays.hashCode(right);
        }
    }

    Map<Character, int[]> dirMap = Map.of(
            '<', new int[]{0, -1},
            '^', new int[]{-1, 0},
            '>', new int[]{0, 1},
            'v', new int[]{1, 0}
    );

    long solve() {
        Stream.of(grid).forEach(row -> System.out.println(row));
        int[] currPos = robotPos();
        for (Character move: movements) {
            int[] dir = dirMap.get(move);
            int i = currPos[0] + dir[0], j = currPos[1] + dir[1];

            if (move == '<' || move == '>') {
                if ((grid[i][j] == '.') || (grid[i][j] == '[' || grid[i][j] == ']')  && shiftX(i, j, dir)) {
                    grid[currPos[0]][currPos[1]] = '.';
                    grid[i][j] = '@';
                    currPos[0] += dir[0];
                    currPos[1] += dir[1];
                }
            } else {
                // shiftY
                if ((grid[i][j] == '.') ||
                        (grid[i][j] == '[' && shiftY(new Box(new int[]{i, j}, new int[]{i, j+1}), dir)) ||
                        (grid[i][j] == ']' && shiftY(new Box(new int[]{i, j-1}, new int[]{i, j}), dir))) {
                    grid[currPos[0]][currPos[1]] = '.';
                    grid[i][j] = '@';
                    currPos[0] += dir[0];
                    currPos[1] += dir[1];
                }
            }
            System.out.println(move);
            Stream.of(grid).forEach(row -> System.out.println(row));
        }

        Stream.of(grid).forEach(row -> System.out.println(row));
        return calculateGps();
    }

    boolean shiftX(int i, int j, int[] dir) {
        int nextI = i + dir[0], nextJ = j+dir[1];
        if(grid[nextI][nextJ] == '#') {
            return false;
        }

        if (grid[nextI][nextJ] == '.' || shiftX(nextI, nextJ, dir)) {
            grid[nextI][nextJ] = grid[i][j];
            grid[i][j] = '.';
            return true;
        }

        return false;
    }

    boolean shiftY(Box box, int[] dir) {
        // bfs/level order traversal
        Queue<Box> q = new LinkedList<>();
        q.offer(box);
        Stack<Set<Box>> reversedBoxes = new Stack<>();
        reversedBoxes.push(Set.of(box));
        while(!q.isEmpty()) {
            int levelSz = q.size();
            Set<Box> level = new HashSet<>();
            while(levelSz != 0) {
                Box b = q.poll();
                int leftI = b.left[0] + dir[0], leftJ = b.left[1] + dir[1];
                int rightI = b.right[0] + dir[0], rightJ = b.right[1] + dir[1];
                if(grid[leftI][leftJ] == '#' || grid[rightI][rightJ] == '#') {
                    return false;
                }

                if (grid[leftI][leftJ] == '[') {
                    // Case: Exactly on top
                    //  ..[]..
                    //  ..[]..
                    Box childBox = new Box(new int[]{leftI, leftJ}, new int[]{rightI, rightJ});
                    q.offer(childBox);
                    level.add(childBox);
                }
                if (grid[leftI][leftJ] == ']') {
                    // Case: One on the left
                    //  .[]..
                    //  ..[]..
                    Box childBox =new Box(new int[]{leftI, leftJ - 1}, new int[]{leftI, leftJ});
                    q.offer(childBox);
                    level.add(childBox);
                }
                if (grid[rightI][rightJ] == '[') {
                    // Case: One on the right
                    //  ...[].
                    //  ..[]..
                    Box childBox = new Box(new int[]{rightI, rightJ}, new int[]{rightI, rightJ + 1});
                    q.offer(childBox);
                    level.add(childBox);
                }

                levelSz--;
            }
            if (!level.isEmpty()) {
                reversedBoxes.add(level);
            }
        }

        while(!reversedBoxes.isEmpty()) {
            Set<Box> boxes = reversedBoxes.pop();
            // If we can move all the boxes
            if(boxes.stream().allMatch(b -> canMove(b, dir))) {
                boxes.forEach(b -> move(b, dir));
            } else {
                return false;
            }
        }

        return true;
    }

    boolean canMove(Box box, int[]  dir) {
        int leftI = box.left[0] + dir[0], leftJ = box.left[1]+dir[1];
        int rightI = box.right[0] + dir[0], rightJ = box.right[1]+dir[1];

        if (grid[leftI][leftJ] == '#' || grid[rightI][rightJ] == '#') {
            return false;
        }

        return (grid[leftI][leftJ] == '.' && grid[rightI][rightJ] == '.');
    }

    void move(Box box, int[] dir) {
        int leftI = box.left[0] + dir[0], leftJ = box.left[1]+dir[1];
        int rightI = box.right[0] + dir[0], rightJ = box.right[1]+dir[1];
        grid[leftI][leftJ] = grid[box.left[0]][box.left[1]];
        grid[box.left[0]][box.left[1]] = '.';

        grid[rightI][rightJ] = grid[box.right[0]][box.right[1]];
        grid[box.right[0]][box.right[1]] = '.';
    }
    long calculateGps() {
        long sumGps = 0;
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[0].length; j  ++) {
                if (grid[i][j] == '[') {
                    sumGps += 100*i + j;
                }
            }
        }
        return sumGps;
    }

    int[] robotPos() {
        for (int i = 0 ; i < grid.length; i++) {
            for (int j = 0 ; j < grid[0].length; j++) {
                if (grid[i][j] == '@') return new int[]{i, j};
            }
        }
        throw new RuntimeException("robot not found!");
    }

    public static void main(String[] args) {
        WarehouseWoes rr = new WarehouseWoes();
        rr.parseInput();

        System.out.println("\n" + rr.solve());
    }

    private void parseInput() {
        try (BufferedReader br = new BufferedReader(new FileReader("aoc/src/main/java/com/avverma/_2024/day15/input.txt"))) {
            this.movements = new ArrayList<>();
            List<char[]> g = new ArrayList<>();
            String line = br.readLine();

            boolean isGrid = true;
            while(line != null) {
                if (line.equals("")) {
                    isGrid = false;
                    widenGrid(g);
                }

                if (isGrid) {
                    g.add(line.toCharArray());
                } else {
                    movements.addAll(line.chars().mapToObj(c -> (char)c).collect(Collectors.toList()));
                }
                line = br.readLine();
            }
        } catch (Exception e) {
            System.err.println(e);
            throw new RuntimeException(e);
        }
    }

    private void widenGrid(List<char[]> g) {
        grid = new char[g.size()][g.get(0).length * 2];
        for (int i = 0; i < g.size(); i++) {
            for (int j = 0; j < g.get(0).length; j++) {
                if (g.get(i)[j] == '.') {
                    grid[i][j*2] = '.';
                    grid[i][j*2+1] = '.';
                } else if (g.get(i)[j] == 'O') {
                    grid[i][j*2] = '[';
                    grid[i][j*2+1] = ']';
                } else if (g.get(i)[j] == '@'){
                    grid[i][j*2] = '@';
                    grid[i][j*2+1] = '.';
                } else {
                    grid[i][j*2] = '#';
                    grid[i][j*2+1] = '#';
                }
            }
        }
    }
}