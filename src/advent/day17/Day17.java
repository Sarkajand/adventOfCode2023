package advent.day17;

import advent.InputReader;

import java.util.*;

public class Day17 {

    private record Position(int row, int column, int score, char direction, int steps) {
    }

    public void firstPart(String filePath) {
        var map = InputReader.readInput3(filePath);
        getShortestPath(map, 3, 0);
    }

    public void secondPart(String filePath) {
        var map = InputReader.readInput3(filePath);
        getShortestPath(map, 10, 4);
    }

    private void getShortestPath(int[][] map, int maxSteps, int minSteps) {
        Map<String, List<Integer>> results = new HashMap<>();
        Set<Integer> targetScores = new HashSet<>();

        Queue<Position> queue = new LinkedList<>();
        queue.add(new Position(0, 1, map[0][1], '>', 1));
        queue.add(new Position(1, 0, map[1][0], 'v', 1));
        while (!queue.isEmpty()) {
            var position = queue.remove();
            int distance = position.score;
            int r = position.row;
            int c = position.column;

            switch (position.direction) {
                case '>':
                    if (position.steps < maxSteps && c < map[0].length - 1) {
                        var newPosition = new Position(r, c + 1, distance + map[r][c + 1], '>', position.steps + 1);
                        addPosition(newPosition, results, queue, targetScores, map);
                    }
                    if (position.steps >= minSteps && r < map.length - 1 && map.length - r > minSteps) {
                        var newPosition = new Position(r + 1, c, distance + map[r + 1][c], 'v', 1);
                        addPosition(newPosition, results, queue, targetScores, map);
                    }
                    if (position.steps >= minSteps && r > 0) {
                        var newPosition = new Position(r - 1, c, distance + map[r - 1][c], '^', 1);
                        addPosition(newPosition, results, queue, targetScores, map);
                    }
                    break;
                case 'v':
                    if (position.steps < maxSteps && r < map.length - 1) {
                        var newPosition = new Position(r + 1, c, distance + map[r + 1][c], 'v', position.steps + 1);
                        addPosition(newPosition, results, queue, targetScores, map);
                    }
                    if (position.steps >= minSteps && c > 0) {
                        var newPosition = new Position(r, c - 1, distance + map[r][c - 1], '<', 1);
                        addPosition(newPosition, results, queue, targetScores, map);
                    }
                    if (position.steps >= minSteps && c < map[0].length - 1 && map[0].length - c > minSteps) {
                        var newPosition = new Position(r, c + 1, distance + map[r][c + 1], '>', 1);
                        addPosition(newPosition, results, queue, targetScores, map);
                    }
                    break;
                case '<':
                    if (position.steps < maxSteps && c > 0) {
                        var newPosition = new Position(r, c - 1, distance + map[r][c - 1], '<', position.steps + 1);
                        addPosition(newPosition, results, queue, targetScores, map);
                    }
                    if (position.steps >= minSteps && r > 0) {
                        var newPosition = new Position(r - 1, c, distance + map[r - 1][c], '^', 1);
                        addPosition(newPosition, results, queue, targetScores, map);
                    }
                    if (position.steps >= minSteps && r < map.length - 1 && map.length - r > minSteps) {
                        var newPosition = new Position(r + 1, c, distance + map[r + 1][c], 'v', 1);
                        addPosition(newPosition, results, queue, targetScores, map);
                    }
                    break;
                case '^':
                    if (position.steps < maxSteps && r > 0) {
                        var newPosition = new Position(r - 1, c, distance + map[r - 1][c], '^', position.steps + 1);
                        addPosition(newPosition, results, queue, targetScores, map);
                    }
                    if (position.steps >= minSteps && c < map[0].length - 1 && map[0].length - c > minSteps) {
                        var newPosition = new Position(r, c + 1, distance + map[r][c + 1], '>', 1);
                        addPosition(newPosition, results, queue, targetScores, map);
                    }
                    if (position.steps >= minSteps && c > 0) {
                        var newPosition = new Position(r, c - 1, distance + map[r][c - 1], '<', 1);
                        addPosition(newPosition, results, queue, targetScores, map);
                    }
                    break;
            }
        }

        var result = targetScores.stream().mapToInt(x -> x).min().orElse(-1);
        System.out.println(result);
    }

    private void addPosition(Position newPosition, Map<String, List<Integer>> results, Queue<Position> queue, Set<Integer> targetScores, int[][] map) {
        var key = String.format("%s_%s_%s_%s", newPosition.row, newPosition.column, newPosition.direction, newPosition.steps);
        if (results.containsKey(key)) {
            var minScore = results.get(key).stream().mapToInt(x -> (Integer) x).min().orElse(Integer.MAX_VALUE);
            if (minScore > newPosition.score) {
                results.get(key).add(newPosition.score);
                queue.add(newPosition);
            }
        } else {
            var list = new ArrayList<Integer>();
            list.add(newPosition.score);
            results.put(key, list);
            queue.add(newPosition);
        }

        if (newPosition.row == map.length - 1 && newPosition.column == map[0].length - 1) {
            targetScores.add(newPosition.score);
        }
    }
}
