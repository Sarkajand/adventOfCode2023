package advent.day13;

import advent.InputReader;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Day13 {

    public void firstPart(String filePath) {
        List<char[][]> maps = processInput(filePath);
        var sum = 0L;
        for (var map : maps) {

            var i = 0;
            var rr = -1;
            while (i < map.length - 1) {
                if (Arrays.equals(map[i], map[i + 1])) {
                    var allEqual = true;
                    for (var j = 1; i + 1 + j < map.length && i - j > -1; j++) {
                        if (!Arrays.equals(map[i - j], map[i + 1 + j])) {
                            allEqual = false;
                            break;
                        }
                    }
                    if (allEqual) {
                        rr = i + 1;
                        break;
                    }
                }
                i++;
            }

            var rc = -1;
            if (rr == -1) {
                i = 0;
                while (i < map[0].length - 1) {
                    var columnEqual = true;
                    for (var k = 0; k < map.length; k++) {
                        if (map[k][i] != map[k][i + 1]) {
                            columnEqual = false;
                            break;
                        }
                    }
                    if (columnEqual) {
                        var allEqual = true;
                        for (var j = 1; i + 1 + j < map[0].length && i - j > -1; j++) {
                            for (var k = 0; k < map.length; k++) {
                                if (map[k][i - j] != map[k][i + 1 + j]) {
                                    allEqual = false;
                                    break;
                                }
                            }
                        }

                        if (allEqual) {
                            rc = i + 1;
                            break;
                        }
                    }
                    i++;
                }
            }

            if (rc != -1) {
                sum += rc;
            } else if (rr != -1) {
                sum += 100L * rr;
            }
        }
        System.out.println(sum);
    }

    public void secondPart(String filePath) {
        List<char[][]> maps = processInput(filePath);
        var sum = 0L;
        for (var map : maps) {
            var i = 0;
            var rr = -1;
            while (i < map.length - 1) {
                var diff = 0;
                var rowEqual = true;

                for (var k = 0; k < map[0].length; k++) {
                    if (map[i][k] != map[i + 1][k]) {
                        if (diff == 0) {
                            diff++;
                        } else {
                            rowEqual = false;
                            break;
                        }
                    }
                }

                if (rowEqual) {
                    var allEqual = true;
                    for (var j = 1; i + 1 + j < map.length && i - j > -1; j++) {
                        for (var k = 0; k < map[0].length; k++) {
                            if (map[i - j][k] != map[i + 1 + j][k]) {
                                if (diff == 0) {
                                    diff++;
                                } else {
                                    allEqual = false;
                                    break;
                                }
                            }
                        }
                    }

                    if (allEqual && diff == 1) {
                        rr = i + 1;
                        break;
                    }
                }

                i++;
            }

            var rc = -1;
            if (rr == -1) {
                i = 0;
                while (i < map[0].length - 1) {
                    var diff = 0;

                    var columnEqual = true;
                    for (var k = 0; k < map.length; k++) {
                        if (map[k][i] != map[k][i + 1]) {
                            if (diff == 0) {
                                diff++;
                            } else {
                                columnEqual = false;
                                break;
                            }
                        }
                    }
                    if (columnEqual) {
                        var allEqual = true;
                        for (var j = 1; i + 1 + j < map[0].length && i - j > -1; j++) {
                            for (var k = 0; k < map.length; k++) {
                                if (map[k][i - j] != map[k][i + 1 + j]) {
                                    if (diff == 0) {
                                        diff++;
                                    } else {
                                        allEqual = false;
                                        break;
                                    }
                                }
                            }
                        }

                        if (allEqual && diff == 1) {
                            rc = i + 1;
                            break;
                        }
                    }
                    i++;
                }
            }

            if (rc != -1) {
                sum += rc;
            } else if (rr != -1) {
                sum += 100L * rr;
            }
        }
        System.out.println(sum);
    }

    private List<char[][]> processInput(String filePath) {
        var input = InputReader.readInput(filePath);
        List<char[][]> maps = new ArrayList<>();

        int i = 0;
        while (i < input.size()) {
            int mapRows = 0;
            int mapColumns = 0;

            for (var j = i; j < input.size() && !input.get(j).isEmpty(); j++) {
                mapRows++;
                mapColumns = Math.max(input.get(j).length(), mapColumns);
            }

            var r = 0;
            char[][] map = new char[mapRows][mapColumns];
            for (var j = i; j < input.size() && !input.get(j).isEmpty(); j++) {
                map[r] = input.get(j).toCharArray();
                r++;
                i++;
            }
            i++;
            maps.add(map);
        }
        return maps;
    }
}
