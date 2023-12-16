package advent.day14;

import advent.InputReader;

import java.util.*;

public class Day14 {

    public void firstPart(String filePath) {
        var map = InputReader.readInput2(filePath);

        for (int r = 1; r < map.length; r++) {
            for (int c = 0; c < map[r].length; c++) {
                if (map[r][c] == 'O') {
                    var mr = r - 1;
                    while (mr > -1 && map[mr][c] != 'O' && map[mr][c] != '#') {
                        mr--;
                    }
                    map[r][c] = '.';
                    map[mr + 1][c] = 'O';
                }
            }
        }

        System.out.println(countLoad(map));
    }

    public void secondPart(String filePath) {
        var map = InputReader.readInput2(filePath);

        Queue<String> previous = new LinkedList<>();
        previous.add("0");
        previous.add("0");
        previous.add("0");
        previous.add("0");
        previous.add("0");
        Map<String, Long> results = new HashMap<>();
        List<Long> sequence = new ArrayList<>();
        var cycle = 1;
        var s = 0;
        var ss = "";
        var l = 0;
        while (true) {
            var key = String.join("_", previous);
            if (results.containsKey(key) && s == 0) {
                s = cycle;
                ss = key;
            } else if (results.containsKey(key) && ss.equals(key)) {
                break;
            }

            var sum = runCycle(map);
            results.put(String.join("_", previous), sum);
            previous.remove();
            previous.add(String.valueOf(sum));

            if (s != 0) {
                l++;
                sequence.add(sum);
            }

            cycle++;
        }

        var i = (1000000000 - s) % l;
        System.out.println(sequence.get(i));
    }

    private long runCycle(char[][] map) {
//        north
        for (int r = 1; r < map.length; r++) {
            for (int c = 0; c < map[r].length; c++) {
                if (map[r][c] == 'O') {
                    var mr = r - 1;
                    while (mr > -1 && map[mr][c] != 'O' && map[mr][c] != '#') {
                        mr--;
                    }
                    map[r][c] = '.';
                    map[mr + 1][c] = 'O';
                }
            }
        }

//            west
        for (int r = 0; r < map.length; r++) {
            for (int c = 1; c < map[r].length; c++) {
                if (map[r][c] == 'O') {
                    var mc = c - 1;
                    while (mc > -1 && map[r][mc] != 'O' && map[r][mc] != '#') {
                        mc--;
                    }
                    map[r][c] = '.';
                    map[r][mc + 1] = 'O';
                }
            }
        }

//            south
        for (int r = map.length - 2; r > -1; r--) {
            for (int c = map[r].length - 1; c > -1; c--) {
                if (map[r][c] == 'O') {
                    var mr = r + 1;
                    while (mr <= map.length - 1 && map[mr][c] != 'O' && map[mr][c] != '#') {
                        mr++;
                    }
                    map[r][c] = '.';
                    map[mr - 1][c] = 'O';
                }
            }
        }

//            east
        for (int r = map.length - 1; r > -1; r--) {
            for (int c = map[r].length - 2; c > -1; c--) {
                if (map[r][c] == 'O') {
                    var mc = c + 1;
                    while (mc <= map[0].length - 1 && map[r][mc] != 'O' && map[r][mc] != '#') {
                        mc++;
                    }
                    map[r][c] = '.';
                    map[r][mc - 1] = 'O';
                }
            }
        }

        return countLoad(map);
    }

    private long countLoad(char[][] map) {
        var load = 0L;
        for (int r = 0; r < map.length; r++) {
            for (int c = 0; c < map[r].length; c++) {
                if (map[r][c] == 'O') {
                    load += map.length - r;
                }
            }
        }
        return load;
    }
}
