package advent.day10;

import advent.InputReader;

import java.util.*;

public class Day10 {

    public void firstPart(String filePath) {
        var input = InputReader.readInput2(filePath);
        var map = InputReader.padInput(input, '0');
        var steps = processMap(input, new char[map.length][map[0].length]);
        System.out.println(steps/2);
    }

    public void secondPart(String filePath) {
        var input = InputReader.readInput2(filePath);
        var map = InputReader.padInput(input, '.');
        var loopMap = new char[map.length][map[0].length];
        processMap(map, loopMap);

        var enclosed = 0;
        for (char[] row : loopMap) {
            var inside = false;
            var around = "";

            for (char ch : row) {
                switch (ch) {
                    case '|':
                        inside = !inside;
                    case '-':
                        break;
                    case 'F':
                        around = "up";
                        break;
                    case 'L':
                        around = "down";
                        break;
                    case '7':
                        if (around.equals("down"))
                            inside = !inside;
                        around = "";
                        break;
                    case 'J':
                        if (around.equals("up"))
                            inside = !inside;
                        around = "";
                        break;
                    case '.':
                        if (inside)
                            enclosed++;
                        break;
                }
            }
        }

        System.out.println(enclosed);
    }

    private int processMap(char[][] map, char[][] loopMap) {
        for (int r = 0; r < map.length; r++) {
            Arrays.fill(loopMap[r], '.');
        }
        var sr = 1;
        var sc = 1;
        for (int r = 0; r < map.length; r++) {
            for (int c = 0; c < map[r].length; c++) {
                if (map[r][c] == 'S') {
                    sr = r;
                    sc = c;
                    r = Integer.MAX_VALUE - 1;
                    break;
                }
            }
        }

        int r = sr;
        int c = sc;
        loopMap[r][c] = 'S';
        List<Character> possibleS = new ArrayList<>();
        if (map[sr - 1][sc] == '|' || map[sr - 1][sc] == '7' || map[sr - 1][sc] == 'F') {
            possibleS.add('|');
            possibleS.add('L');
            possibleS.add('J');
            r = sr - 1;
        } else if (map[sr + 1][sc] == '|' || map[sr + 1][sc] == 'L' || map[sr + 1][sc] == 'J') {
            possibleS.add('|');
            possibleS.add('7');
            possibleS.add('F');
            r = sr + 1;
        } else if (map[sr][sc - 1] == '-' || map[sr][sc - 1] == 'L' || map[sr][sc - 1] == 'F') {
            possibleS.add('-');
            possibleS.add('7');
            possibleS.add('J');
            c = sc - 1;
        } else if (map[sr][sc + 1] == '-' || map[sr][sc + 1] == '7' || map[sr][sc + 1] == 'J') {
            possibleS.add('-');
            possibleS.add('L');
            possibleS.add('F');
            c = sc + 1;
        }

        var steps = 1;
        while (map[r][c] != 'S') {
            if ((map[r][c] == '|' || map[r][c] == 'J' || map[r][c] == 'L')
                    && (loopMap[r - 1][c] == '.' || (loopMap[r - 1][c] == 'S' && steps > 1))
                    && (map[r - 1][c] == '|' || map[r - 1][c] == '7' || map[r - 1][c] == 'F' || map[r - 1][c] == 'S')) {
                loopMap[r][c] = map[r][c];
                r = r - 1;
                steps++;
                if (map[r][c] == 'S') {
                    possibleS.removeAll(List.of('-', 'J', 'L'));
                    loopMap[r][c] = possibleS.get(0);
                }
            } else if ((map[r][c] == '|' || map[r][c] == 'F' || map[r][c] == '7')
                    && (loopMap[r + 1][c] == '.' || (loopMap[r + 1][c] == 'S' && steps > 1))
                    && (map[r + 1][c] == '|' || map[r + 1][c] == 'L' || map[r + 1][c] == 'J' || map[r + 1][c] == 'S')) {
                loopMap[r][c] = map[r][c];
                r = r + 1;
                steps++;
                if (map[r][c] == 'S') {
                    possibleS.removeAll(List.of('-', 'F', '7'));
                    loopMap[r][c] = possibleS.get(0);
                }
            } else if ((map[r][c] == '-' || map[r][c] == 'J' || map[r][c] == '7')
                    && (loopMap[r][c - 1] == '.' || (loopMap[r][c - 1] == 'S' && steps > 1))
                    && (map[r][c - 1] == '-' || map[r][c - 1] == 'L' || map[r][c - 1] == 'F' || map[r][c - 1] == 'S')) {
                loopMap[r][c] = map[r][c];
                c = c - 1;
                steps++;
                if (map[r][c] == 'S') {
                    possibleS.removeAll(List.of('|', 'J', '7'));
                    loopMap[r][c] = possibleS.get(0);
                }
            } else if ((map[r][c] == '-' || map[r][c] == 'F' || map[r][c] == 'L')
                    && (loopMap[r][c + 1] == '.' || (loopMap[r][c + 1] == 'S' && steps > 1))
                    && (map[r][c + 1] == '-' || map[r][c + 1] == '7' || map[r][c + 1] == 'J' || map[r][c + 1] == 'S')) {
                loopMap[r][c] = map[r][c];
                c = c + 1;
                steps++;
                if (map[r][c] == 'S') {
                    possibleS.removeAll(List.of('|', 'F', 'L'));
                    loopMap[r][c] = possibleS.get(0);
                }
            }
        }
        return steps;
    }
}
