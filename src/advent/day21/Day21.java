package advent.day21;

import advent.InputReader;

import java.awt.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Day21 {

    private record Position(int r, int c) {
    }

    public void firstPart(String filePath, int steps) {
        var map = InputReader.readInput2(filePath);
        Set<Position> positions = new HashSet<>();
        positions.add(getStart(map));

        for (var step = 1; step <= steps; step++) {
            Set<Position> newPositions = new HashSet<>();
            for (var p : positions) {
                if (p.r + 1 < map.length && map[p.r + 1][p.c] == '.') {
                    newPositions.add(new Position(p.r + 1, p.c));
                }
                if (p.r - 1 >= 0 && map[p.r - 1][p.c] == '.') {
                    newPositions.add(new Position(p.r - 1, p.c));
                }
                if (p.c + 1 < map[0].length && map[p.r][p.c + 1] == '.') {
                    newPositions.add(new Position(p.r, p.c + 1));
                }
                if (p.c - 1 >= 0 && map[p.r][p.c - 1] == '.') {
                    newPositions.add(new Position(p.r, p.c - 1));
                }
            }
            positions = newPositions;
        }

        System.out.println(positions.size());
    }

    public void secondPart(String filePath, int steps) {
        var map = InputReader.readInput2(filePath);
        Set<Position> positions = new HashSet<>();
        positions.add(getStart(map));

        var width = map.length;
        long cycles = steps / width;
        long reminder = steps % width;

        List<Point> solved = new ArrayList<>();
        steps = 0;
        for (int i = 0; i < 3; i++) {
            while (steps < i * width + reminder) {
                positions = positions.parallelStream()
                        .flatMap(p -> Stream.of(new Position(p.r + 1, p.c), new Position(p.r - 1, p.c), new Position(p.r, p.c + 1), new Position(p.r, p.c - 1)))
                        .filter(p -> isValid(map, p)).collect(Collectors.toSet());
                steps++;
            }
            solved.add(new Point(i, positions.size()));
        }

        Function<Long, Long> g = x -> {
            double x1 = solved.get(0).x;
            double y1 = solved.get(0).y;
            double x2 = solved.get(1).x;
            double y2 = solved.get(1).y;
            double x3 = solved.get(2).x;
            double y3 = solved.get(2).y;
            return (long) (((x - x2) * (x - x3)) / ((x1 - x2) * (x1 - x3)) * y1 +
                    ((x - x1) * (x - x3)) / ((x2 - x1) * (x2 - x3)) * y2 +
                    ((x - x1) * (x - x2)) / ((x3 - x1) * (x3 - x2)) * y3);
        };

        System.out.println(g.apply(cycles));
    }

    private static boolean isValid(char[][] map, Position p) {
        var mapSize = map.length;
        int r = ((p.r % mapSize) + mapSize) % mapSize;
        int c = ((p.c % mapSize) + mapSize) % mapSize;
        return map[r][c] != '#';
    }

    private Position getStart(char[][] map) {
        for (int r = 0; r < map.length; r++) {
            for (int c = 0; c < map[r].length; c++) {
                if (map[r][c] == 'S') {
                    map[r][c] = '.';
                    return new Position(r, c);
                }
            }
        }
        return null;
    }
}
