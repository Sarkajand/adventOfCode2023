package advent.day01;

import advent.InputReader;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Day01 {

    public void firstPart(String filePath) {
        List<String> input = InputReader.readInput(filePath);
        List<Integer> numbers = new ArrayList<>();
        input.forEach(line -> {
            Character firstNumber = null;
            Character lastNumber = null;
            for (char c : line.toCharArray()) {
                if (Character.isDigit(c)) {
                    if (firstNumber == null) {
                        firstNumber = c;
                    }
                    lastNumber = c;
                }
            }
            numbers.add(Integer.parseInt("" + firstNumber + lastNumber));
        });
        System.out.println(numbers.stream().mapToInt(e -> e).sum());
    }

    public void secondPart(String filePath) {
        List<String> input = InputReader.readInput(filePath);
        List<Integer> numbers = new ArrayList<>();
        Pattern p = Pattern.compile("(?=([0-9]|zero|one|two|three|four|five|six|seven|eight|nine)).");
        input.forEach(line -> {
            String firstNumber = null;
            String lastNumber = null;

            Matcher m = p.matcher(line);
            if (m.find()) {
                firstNumber = m.group(1);
                lastNumber = m.group(1);
            }

            while (m.find()) {
                lastNumber = m.group(1);
            }

            numbers.add(Integer.parseInt(getDigit(firstNumber) + getDigit(lastNumber)));
        });
        System.out.println(numbers.stream().mapToInt(e -> e).sum());
    }

    private String getDigit(String input) {
        if (input.length() == 1) {
            return input;
        }
        return switch (input) {
            case "zero" -> "0";
            case "one" -> "1";
            case "two" -> "2";
            case "three" -> "3";
            case "four" -> "4";
            case "five" -> "5";
            case "six" -> "6";
            case "seven" -> "7";
            case "eight" -> "8";
            case "nine" -> "9";
            default -> "";
        };
    }
}
