package advent.day16;

import advent.InputReader;

import java.util.ArrayList;
import java.util.List;

public class Day16 {

    private record Beam(int r, int c, char direction) {
    }

    public void firstPart(String filePath) {
        var map = InputReader.readInput2(filePath);
        var sum = moveBeam(new Beam(0, 0, '>'), map, new ArrayList<>());
        System.out.println(sum);
    }

    public void secondPart(String filePath) {
        var map = InputReader.readInput2(filePath);
        var maxSum = 0;
        for (var r = 0; r < map.length; r++) {
            var sum = moveBeam(new Beam(r, 0, '>'), map, new ArrayList<>());
            maxSum = Math.max(maxSum, sum);
            sum = moveBeam(new Beam(r, map[0].length, '<'), map, new ArrayList<>());
            maxSum = Math.max(maxSum, sum);
        }
        for (var c = 0; c < map[0].length; c++) {
            var sum = moveBeam(new Beam(0, c, 'v'), map, new ArrayList<>());
            maxSum = Math.max(maxSum, sum);
            sum = moveBeam(new Beam(map.length, c, '^'), map, new ArrayList<>());
            maxSum = Math.max(maxSum, sum);
        }

        System.out.println(maxSum);
    }

    private int moveBeam(Beam beam, char[][] map, List<Beam> visited) {
        if (visited.contains(beam) || beam.r < 0 || beam.r >= map.length || beam.c < 0 || beam.c >= map[0].length) {
            return 0;
        }

        var result = 0;
        if (!visited.contains(new Beam(beam.r, beam.c, '>'))
                && !visited.contains(new Beam(beam.r, beam.c, 'v'))
                && !visited.contains(new Beam(beam.r, beam.c, '<'))
                && !visited.contains(new Beam(beam.r, beam.c, '^'))) {
            result++;
        }
        visited.add(beam);

        if (map[beam.r][beam.c] == '.') {
            if (beam.direction == '>') {
                result += moveBeam(new Beam(beam.r, beam.c + 1, beam.direction), map, visited);
            } else if (beam.direction == 'v') {
                result += moveBeam(new Beam(beam.r + 1, beam.c, beam.direction), map, visited);
            } else if (beam.direction == '<') {
                result += moveBeam(new Beam(beam.r, beam.c - 1, beam.direction), map, visited);
            } else if (beam.direction == '^') {
                result += moveBeam(new Beam(beam.r - 1, beam.c, beam.direction), map, visited);
            }
        } else if (map[beam.r][beam.c] == '|') {
            if (beam.direction == '>') {
                result += moveBeam(new Beam(beam.r - 1, beam.c, '^'), map, visited);
                result += moveBeam(new Beam(beam.r + 1, beam.c, 'v'), map, visited);
            } else if (beam.direction == 'v') {
                result += moveBeam(new Beam(beam.r + 1, beam.c, beam.direction), map, visited);
            } else if (beam.direction == '<') {
                result += moveBeam(new Beam(beam.r - 1, beam.c, '^'), map, visited);
                result += moveBeam(new Beam(beam.r + 1, beam.c, 'v'), map, visited);
            } else if (beam.direction == '^') {
                result += moveBeam(new Beam(beam.r - 1, beam.c, beam.direction), map, visited);
            }
        } else if (map[beam.r][beam.c] == '-') {
            if (beam.direction == '>') {
                result += moveBeam(new Beam(beam.r, beam.c + 1, beam.direction), map, visited);
            } else if (beam.direction == 'v') {
                result += moveBeam(new Beam(beam.r, beam.c + 1, '>'), map, visited);
                result += moveBeam(new Beam(beam.r, beam.c - 1, '<'), map, visited);
            } else if (beam.direction == '<') {
                result += moveBeam(new Beam(beam.r, beam.c - 1, beam.direction), map, visited);
            } else if (beam.direction == '^') {
                result += moveBeam(new Beam(beam.r, beam.c + 1, '>'), map, visited);
                result += moveBeam(new Beam(beam.r, beam.c - 1, '<'), map, visited);
            }
        } else if (map[beam.r][beam.c] == '/') {
            if (beam.direction == '>') {
                result += moveBeam(new Beam(beam.r - 1, beam.c, '^'), map, visited);
            } else if (beam.direction == 'v') {
                result += moveBeam(new Beam(beam.r, beam.c - 1, '<'), map, visited);
            } else if (beam.direction == '<') {
                result += moveBeam(new Beam(beam.r + 1, beam.c, 'v'), map, visited);
            } else if (beam.direction == '^') {
                result += moveBeam(new Beam(beam.r, beam.c + 1, '>'), map, visited);
            }
        } else if (map[beam.r][beam.c] == '\\') {
            if (beam.direction == '>') {
                result += moveBeam(new Beam(beam.r + 1, beam.c, 'v'), map, visited);
            } else if (beam.direction == 'v') {
                result += moveBeam(new Beam(beam.r, beam.c + 1, '>'), map, visited);
            } else if (beam.direction == '<') {
                result += moveBeam(new Beam(beam.r - 1, beam.c, '^'), map, visited);
            } else if (beam.direction == '^') {
                result += moveBeam(new Beam(beam.r, beam.c - 1, '<'), map, visited);
            }
        }

        return result;
    }
}
