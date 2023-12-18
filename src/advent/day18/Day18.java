package advent.day18;

import advent.InputReader;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Day18 {

    private record Point(long r, long c) {
    }

    public void firstPart1(String filePath) {
        var input = InputReader.readInput(filePath);
        var map = new char[450][800];
        for (char[] chars : map) {
            Arrays.fill(chars, '.');
        }

        var r = 300;
        var c = 200;

        for (var line : input) {
            var steps = Integer.parseInt(line.split(" ")[1]);
            var direction = line.charAt(0);

            if (direction == 'R') {
                for (var i = 0; i < steps; i++) {
                    c++;
                    map[r][c] = '#';
                }
            } else if (direction == 'D') {
                for (var i = 0; i < steps; i++) {
                    r++;
                    map[r][c] = '#';
                }
            } else if (direction == 'L') {
                for (var i = 0; i < steps; i++) {
                    c--;
                    map[r][c] = '#';
                }
            } else if (direction == 'U') {
                for (var i = 0; i < steps; i++) {
                    r--;
                    map[r][c] = '#';
                }
            }
        }

        var count = 0;
        InputReader.printMap(map);
        for (r = 0; r < map.length; r++) {
            char[] chars = map[r];
            boolean inside = false;
            boolean line = false;
            for (var i = 0; i < chars.length; i++) {
                if (chars[i] == '#' && !line && chars[i + 1] == '.') {
                    inside = !inside;
                } else if (chars[i] == '#' && !line) {
                    line = true;
                } else if (chars[i] == '.' && line) {
                    line = false;
                    if (inside && map[r - 1][i] == '.') {
                        inside = false;
                    } else {
                        if (map[r - 1][i] == '#') {
                            inside = true;
                        }
                    }
                }
                if (inside || chars[i] == '#') {
                    chars[i] = '#';
                    count++;
                }
            }
        }

        InputReader.printMap(map);
        System.out.println(count);
    }

    public void firstPart(String filePath) {
        var input = InputReader.readInput(filePath);
        List<Point> points = new ArrayList<>();
        points.add(new Point(0, 0));
        var circumference = 0L;

        for (var line : input) {
            var steps = Long.parseLong(line.split(" ")[1]);
            var direction = line.charAt(0);
            var lr = points.getLast().r;
            var lc = points.getLast().c;
            circumference += steps;

            if (direction == 'R') {
                points.add(new Point(lr, lc + steps));
            } else if (direction == 'D') {
                points.add(new Point(lr + steps, lc));
            } else if (direction == 'L') {
                points.add(new Point(lr, lc - steps));
            } else if (direction == 'U') {
                points.add(new Point(lr - steps, lc));
            }
        }

        System.out.println(getArea(points, circumference));
    }

    public void secondPart(String filePath) {
        var input = InputReader.readInput(filePath);
        List<Point> points = new ArrayList<>();
        points.add(new Point(0, 0));
        var circumference = 0L;

        for (var line : input) {
            var instruction = line.split(" ")[2];
            var steps = Long.parseLong(instruction.substring(2, 7), 16);
            var direction = instruction.charAt(7);
            var lr = points.getLast().r;
            var lc = points.getLast().c;
            circumference += steps;

            if (direction == '0') {
                points.add(new Point(lr, lc + steps));
            } else if (direction == '1') {
                points.add(new Point(lr + steps, lc));
            } else if (direction == '2') {
                points.add(new Point(lr, lc - steps));
            } else if (direction == '3') {
                points.add(new Point(lr - steps, lc));
            }
        }

        System.out.println(getArea(points, circumference));
    }

    private long getArea(List<Point> points, long circumference) {
        var area = 0L;
        for (var i = 1; i < points.size(); i++) {
            area += points.get(i).r * (points.get(i - 1).c - points.get((i + 1) % points.size()).c);
        }
        area = Math.abs(area) / 2;
        area = area - circumference / 2 + 1;
        return area + circumference;
    }
}
