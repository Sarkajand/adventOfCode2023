package advent.day02;

import advent.InputReader;

import java.io.IOException;
import java.math.BigInteger;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Day02 {

    public void firstPart(String filePath) throws IOException {
        List<String> input = InputReader.readInput(filePath);
        int gameNumber = 1;
        int gameNumberSum = 0;
        Pattern redPattern = Pattern.compile("([0-9]+ red)");
        Pattern greenPattern = Pattern.compile("([0-9]+ green)");
        Pattern bluePattern = Pattern.compile("([0-9]+ blue)");
        for (String line : input) {
            int maxRed = 0;
            int maxGreen = 0;
            int maxBlue = 0;

            Matcher redMatcher = redPattern.matcher(line);
            while (redMatcher.find()) {
                maxRed = Math.max(maxRed, Integer.parseInt(redMatcher.group().split(" ")[0]));
            }

            Matcher greenMatcher = greenPattern.matcher(line);
            while (greenMatcher.find()) {
                maxGreen = Math.max(maxGreen, Integer.parseInt(greenMatcher.group().split(" ")[0]));
            }

            Matcher blueMatcher = bluePattern.matcher(line);
            while (blueMatcher.find()) {
                maxBlue = Math.max(maxBlue, Integer.parseInt(blueMatcher.group().split(" ")[0]));
            }

            if (maxRed <= 12 && maxGreen <= 13 && maxBlue <= 14) {
                gameNumberSum += gameNumber;
            }

            gameNumber++;
        }

        System.out.println(gameNumberSum);
    }

    public void secondPart(String filePath) throws IOException {
        List<String> input = InputReader.readInput(filePath);
        Pattern redPattern = Pattern.compile("([0-9]+ red)");
        Pattern greenPattern = Pattern.compile("([0-9]+ green)");
        Pattern bluePattern = Pattern.compile("([0-9]+ blue)");
        BigInteger sum = BigInteger.ZERO;

        for (String line : input) {
            int maxRed = 0;
            int maxGreen = 0;
            int maxBlue = 0;
            int gamePower;

            Matcher redMatcher = redPattern.matcher(line);
            while (redMatcher.find()) {
                maxRed = Math.max(maxRed, Integer.parseInt(redMatcher.group().split(" ")[0]));
            }

            Matcher greenMatcher = greenPattern.matcher(line);
            while (greenMatcher.find()) {
                maxGreen = Math.max(maxGreen, Integer.parseInt(greenMatcher.group().split(" ")[0]));
            }

            Matcher blueMatcher = bluePattern.matcher(line);
            while (blueMatcher.find()) {
                maxBlue = Math.max(maxBlue, Integer.parseInt(blueMatcher.group().split(" ")[0]));
            }

            gamePower = maxRed * maxGreen * maxBlue;
            sum = sum.add(BigInteger.valueOf(gamePower));
        }

        System.out.println(sum);
    }
}
