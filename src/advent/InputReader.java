package advent;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

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
}
