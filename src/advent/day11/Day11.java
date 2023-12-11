package advent.day11;

import advent.InputReader;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.IntStream;

public class Day11 {

    public record Galaxy(long r, long c) { }

    public void firstPart(String filePath) {
        var map = InputReader.readInput2(filePath);
        var expandedMap = expandMap(map);
        var galaxies = getGalaxies(expandedMap);
        List<Long> paths = getPaths(galaxies);
        var sum = paths.stream().mapToLong(x -> x).sum();
        System.out.println(sum);
    }

    public void secondPart(String filePath, int expand) {
        var map = InputReader.readInput2(filePath);
        List<Integer> er = new ArrayList<>(IntStream.range(0, map.length).boxed().toList());
        List<Integer> ec = new ArrayList<>(IntStream.range(0, map[0].length).boxed().toList());
        getEmptyRC(map, er, ec);
        var galaxies = getGalaxies(map);
        var expandedGalaxies = expandUniverse(galaxies, er, ec, expand);
        List<Long> paths = getPaths(expandedGalaxies);
        var sum = paths.stream().mapToLong(x -> x).sum();
        System.out.println(sum);
    }

    private List<Long> getPaths(List<Galaxy> galaxies) {
        List<Long> paths = new ArrayList<>();
        for (int i = 0; i< galaxies.size(); i++) {
            var galaxy = galaxies.get(i);
            for (int j = i+1; j < galaxies.size(); j++) {
                var pathTo = galaxies.get(j);
                paths.add(Math.abs(galaxy.r - pathTo.r) + Math.abs(galaxy.c - pathTo.c));
            }
        }
        return paths;
    }

    private List<Galaxy> expandUniverse(List<Galaxy> galaxies, List<Integer> rows, List<Integer> columns, int expand) {
        List<Galaxy> expandedGalaxies = new ArrayList<>();
        for (var galaxy : galaxies) {
        var er = rows.stream().filter(x -> x < galaxy.r).count();
        var ec = columns.stream().filter(x -> x < galaxy.c).count();
        expandedGalaxies.add(new Galaxy(er * expand + (galaxy.r-er), ec * expand + (galaxy.c-ec)));
        }
        return expandedGalaxies;
    }

    private List<Galaxy> getGalaxies(char[][] map) {
        List<Galaxy> galaxies = new ArrayList<>();
        for (int r = 0; r < map.length; r++) {
            for (int c = 0; c < map[r].length; c++) {
                if (map[r][c] == '#') {
                    galaxies.add(new Galaxy(r, c));
                }
            }
        }
        return galaxies;
    }

    private void getEmptyRC(char[][] map, List<Integer> rows, List<Integer> columns) {
        for (int r = 0; r < map.length; r++) {
            for (int c = 0; c < map[r].length; c++) {
                if (map[r][c] == '#') {
                    rows.remove((Integer) r);
                    columns.remove((Integer) c);
                }
            }
        }
    }

    private char[][] expandMap(char[][] map) {
        List<Integer> rows = new ArrayList<>(IntStream.range(0, map.length).boxed().toList());
        List<Integer> columns = new ArrayList<>(IntStream.range(0, map[0].length).boxed().toList());
        getEmptyRC(map, rows, columns);

        var expendedMap = new char[map.length + rows.size()][map[0].length + columns.size()];
        var emr = 0;
        var emc = 0;

        for (int r = 0; r < map.length; r++) {
            if (rows.contains(r)) {
                char[] row = new char[expendedMap[0].length];
                Arrays.fill(row, '.');
                expendedMap[emr] = row;
                expendedMap[emr + 1] = row;
                emr += 2;
            } else {
                emc = 0;
                for (int c = 0; c < map[r].length; c++) {
                    if (columns.contains(c)) {
                        expendedMap[emr][emc] = '.';
                        expendedMap[emr][emc + 1] = '.';
                        emc += 2;
                    } else {
                        expendedMap[emr][emc] = map[r][c];
                        emc++;
                    }
                }
                emr++;
            }
        }
        return expendedMap;
    }
}
