package advent.day23;

import advent.InputReader;

import java.awt.*;
import java.util.*;
import java.util.List;
import java.util.stream.Collectors;

public class Day23 {
    public void firstPart(String filePath) {
        var map = InputReader.readInput2(filePath);
        var results = new int[map.length][map[0].length];
        for (var row : results) {
            Arrays.fill(row, 0);
        }
        Queue<Position> queue = new LinkedList<>();
        queue.add(new Position(0, 1, 0, 'v'));

        while (!queue.isEmpty()) {
            var p = queue.remove();
            var r = p.r;
            var c = p.c;
            var canMove = false;

            if (p.direction != 'v' && (map[r][c] == '.' || map[r][c] == '^') && r-1 >= 0 && map[r-1][c] != '#') {
                queue.add(new Position(r-1, c, p.distance+1, '^'));
                canMove = true;
            }
            if (p.direction != '^' && (map[r][c] == '.' || map[r][c] == 'v') && r+1 < map.length && map[r+1][c] != '#') {
                queue.add(new Position(r+1, c, p.distance+1, 'v'));
                canMove = true;
            }
            if (p.direction != '>' && (map[r][c] == '.' || map[r][c] == '<') && c-1 >= 0 && map[r][c-1] != '#') {
                queue.add(new Position(r, c-1, p.distance+1, '<'));
                canMove = true;
            }
            if (p.direction != '<' && (map[r][c] == '.' || map[r][c] == '>') && c+1 < map[0].length && map[r][c+1] != '#') {
                queue.add(new Position(r, c+1, p.distance+1, '>'));
                canMove = true;
            }

            if (canMove) {
                results[r][c] = Math.max(results[r][c], p.distance);
            } else if (r== map.length-1 && c== map[0].length-2) {
                results[r][c] = Math.max(results[r][c], p.distance);
            }
        }

        System.out.println(results[map.length-1][map[0].length-2]);
    }
    public void secondPart(String filePath) {
        var map = InputReader.readInput2(filePath);
        for (int r = 1; r < map.length; r++) {
            for (int c = 0; c < map[r].length; c++) {
                if (map[r][c] != '#') {
                    map[r][c] = '.';
                }
            }
        }
        var crossroads = getCrossroads(map);
        var start = new Crossroad(0, 1, new HashMap<>());
        var end = new Crossroad(map.length - 1, map[0].length - 2, new HashMap<>());
        crossroads.add(start);
        crossroads.add(end);
        Queue<Position> queue = new LinkedList<>();
        queue.add(new Position(1, 1, 'v', start));
        connectCrossroads(queue, crossroads, map, start, end);

        List<Integer> results = new ArrayList<>();
        go(start, end, 0, new HashSet<>(), results);
        var result = results.stream().mapToInt(x -> x).max().orElse(0);
        System.out.println(result);
    }

    private void go(Crossroad crossroad, Crossroad end, int steps, Set<Crossroad> path, List<Integer> results) {
        path.add(crossroad);
        if (crossroad == end) {
            results.add(steps);
            return;
        }

        for (var next : crossroad.connectedCrossroads.entrySet()) {
            if (!path.contains(next.getKey())) {
                go(next.getKey(), end, steps + next.getValue(), new HashSet<>(path), results);
            }
        }
    }

    private void connectCrossroads(Queue<Position> queue, List<Crossroad> crossroads, char[][] map, Crossroad start, Crossroad end) {
        Set<Point> crossroadsPoints = crossroads.stream().map(x -> new Point(x.r, x.c)).collect(Collectors.toSet());
        while (!queue.isEmpty()) {
            var p = queue.remove();
            var r = p.r;
            var c = p.c;
            var direction = p.direction;
            var steps = 1;
            var canMove = true;

            while ((r != map.length - 1 || c != map[0].length - 2) && !crossroadsPoints.contains(new Point(r, c))) {
                if (direction != 'v' && (map[r][c] == '.' || map[r][c] == '^') && r - 1 >= 0 && map[r - 1][c] != '#') {
                    r = r - 1;
                    direction = '^';
                    steps++;
                } else if (direction != '^' && (map[r][c] == '.' || map[r][c] == 'v') && r + 1 < map.length && map[r + 1][c] != '#') {
                    r = r + 1;
                    direction = 'v';
                    steps++;
                } else if (direction != '>' && (map[r][c] == '.' || map[r][c] == '<') && c - 1 >= 0 && map[r][c - 1] != '#') {
                    c = c - 1;
                    direction = '<';
                    steps++;
                } else if (direction != '<' && (map[r][c] == '.' || map[r][c] == '>') && c + 1 < map[0].length && map[r][c + 1] != '#') {
                    c = c + 1;
                    direction = '>';
                    steps++;
                } else {
                    canMove = false;
                    break;
                }
            }

            if (canMove) {
                int finalR = r;
                int finalC = c;
                var crossroad = crossroads.stream().filter(x -> x.r == finalR && x.c == finalC).findFirst().orElse(new Crossroad(0, 0, new HashMap<>()));
                if (p.fromCrossroad.connectedCrossroads.containsKey(crossroad)) {
                    continue;
                }
                p.fromCrossroad.connectedCrossroads.put(crossroad, steps);

                if (crossroad != end && crossroad != start) {
                    if (direction != 'v' && (map[r][c] == '.' || map[r][c] == '^') && r - 1 >= 0 && map[r - 1][c] != '#') {
                        queue.add(new Position(r - 1, c, '^', crossroad));
                    }
                    if (direction != '^' && (map[r][c] == '.' || map[r][c] == 'v') && r + 1 < map.length && map[r + 1][c] != '#') {
                        queue.add(new Position(r + 1, c, 'v', crossroad));
                    }
                    if (direction != '>' && (map[r][c] == '.' || map[r][c] == '<') && c - 1 >= 0 && map[r][c - 1] != '#') {
                        queue.add(new Position(r, c - 1, '<', crossroad));
                    }
                    if (direction != '<' && (map[r][c] == '.' || map[r][c] == '>') && c + 1 < map[0].length && map[r][c + 1] != '#') {
                        queue.add(new Position(r, c + 1, '>', crossroad));
                    }
                }
            }
        }
    }

    private List<Crossroad> getCrossroads(char[][] map) {
        List<Crossroad> crossroads = new ArrayList<>();
        for (int r = 1; r < map.length - 1; r++) {
            for (int c = 1; c < map[0].length - 1; c++) {
                if (pointIsCrossroad(new Point(r, c), map))
                    crossroads.add(new Crossroad(r, c, new HashMap<>()));
            }
        }
        return crossroads;
    }

    private boolean pointIsCrossroad(Point point, char[][] map) {
        if (map[point.x][point.y] == '#') {
            return false;
        }
        int count = 0;
        if (map[point.x - 1][point.y] != '#') {
            count++;
        }
        if (map[point.x + 1][point.y] != '#') {
            count++;
        }
        if (map[point.x][point.y - 1] != '#') {
            count++;
        }
        if (map[point.x][point.y + 1] != '#') {
            count++;
        }
        return count > 2;
    }

    private static class Position {
        int r;
        int c;
        char direction;
        int distance;
        Crossroad fromCrossroad;

        public Position(int r, int c, int distance, char direction) {
            this.r = r;
            this.c = c;
            this.direction = direction;
            this.distance = distance;
        }

        public Position(int r, int c, char direction, Crossroad fromCrossroad) {
            this.r = r;
            this.c = c;
            this.direction = direction;
            this.fromCrossroad = fromCrossroad;
        }
    }
    private static class Crossroad {
        int r;
        int c;
        Map<Crossroad, Integer> connectedCrossroads = new HashMap<>();

        public Crossroad(int r, int c, Map<Crossroad, Integer> connectedCrossroads) {
            this.r = r;
            this.c = c;
            this.connectedCrossroads = connectedCrossroads;
        }
    }
}
