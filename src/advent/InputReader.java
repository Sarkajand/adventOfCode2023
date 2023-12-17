package advent;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class InputReader {

    public static List<String> readInput(String filePath) {
        String file = "src/resources/" + filePath;
        List<String> input = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String currentLine = reader.readLine();
            while (currentLine != null) {
                input.add(currentLine);
                currentLine = reader.readLine();
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return input;
    }

    public static char[][] readInput2(String filePath) {
        String file = "src/resources/" + filePath;
        int mapRows = 0;
        int mapColumns = 0;

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String currentLine = reader.readLine();
            while (currentLine != null && !currentLine.isEmpty()) {
                mapRows++;
                mapColumns = Math.max(currentLine.length(), mapColumns);
                currentLine = reader.readLine();
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        char[][] map = new char[mapRows][mapColumns];
        int i = 0;

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String currentLine = reader.readLine();
            while (currentLine != null && !currentLine.isEmpty()) {
                map[i] = currentLine.toCharArray();
                i++;
                currentLine = reader.readLine();
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return map;
    }

    public static int[][] readInput3(String filePath) {
        String file = "src/resources/" + filePath;
        int mapRows = 0;
        int mapColumns = 0;

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String currentLine = reader.readLine();
            while (currentLine != null && !currentLine.isEmpty()) {
                mapRows++;
                mapColumns = Math.max(currentLine.length(), mapColumns);
                currentLine = reader.readLine();
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        int[][] map = new int[mapRows][mapColumns];
        int i = 0;

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String currentLine = reader.readLine();
            while (currentLine != null && !currentLine.isEmpty()) {
                map[i] = currentLine.chars().map(Character::getNumericValue).toArray();
                i++;
                currentLine = reader.readLine();
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return map;
    }
    public static char[][] padInput(char[][] map, char c) {
        char[][] paddedMap = new char[map.length + 2][map[0].length + 2];
        char[] firstRow = new char[map[0].length + 2];
        Arrays.fill(firstRow, c);
        paddedMap[0] = firstRow;
        char[] lastRow = new char[map[0].length + 2];
        Arrays.fill(lastRow, c);
        paddedMap[map.length + 1] = lastRow;


        for (int i = 1; i < map.length + 1; i++) {
            char[] newRow = new char[map[0].length + 2];
            Arrays.fill(newRow, c);
            char[] row = map[i - 1];
            System.arraycopy(row, 0, newRow, 1, map[0].length);
            paddedMap[i] = newRow;
        }

        return paddedMap;
    }

    public static void printMap(char[][] map) {
        for (char[] row : map) {
            System.out.println(row);
        }
        System.out.println();
    }

    public static void printMap2(int[][] map, String str) {
        for (int[] row : map) {
            System.out.println(Arrays.stream(row).mapToObj(Integer::toString).collect(Collectors.joining(str)));
        }
        System.out.println();
    }
}
