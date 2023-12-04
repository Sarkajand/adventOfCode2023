package advent.day04;

import advent.InputReader;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Day04 {

    public void firstPart(String filePath) {
        List<String> input = InputReader.readInput(filePath);
        var result = 0;
        for (String row : input) {
            List<Integer> winingNumbers = new ArrayList<>();
            List<Integer> numbers = new ArrayList<>();
            processCard(row, winingNumbers, numbers);

            var cardPoints = 0;
            for (var number : numbers) {
                if (winingNumbers.contains(number)) {
                    cardPoints = cardPoints == 0 ? 1 : cardPoints * 2;
                }
            }
            result += cardPoints;
        }
        System.out.println(result);
    }

    public void secondPart(String filePath) {
        List<String> input = InputReader.readInput(filePath);
        var cardCounts = new int[input.size()];
        Arrays.fill(cardCounts, 1);
        int cardNumber = 0;
        for (String row : input) {
            List<Integer> winingNumbers = new ArrayList<>();
            List<Integer> numbers = new ArrayList<>();
            processCard(row, winingNumbers, numbers);

            var cardPoints = 0;
            for (var number : numbers) {
                if (winingNumbers.contains(number)) {
                    cardPoints++;
                }
            }

            for (int j = 0; j < cardCounts[cardNumber]; j++) {
                for (int i = cardNumber + 1; i <= cardNumber + cardPoints && i < cardCounts.length; i++) {
                    cardCounts[i] = cardCounts[i] + 1;
                }
            }
            cardNumber++;
        }

        System.out.println(Arrays.stream(cardCounts).sum());
    }

    private void processCard(String card, List<Integer> winingNumbers, List<Integer> numbers) {
        card = card.replaceAll("\\s+", " ");
        card = card.replaceFirst("Card \\d+:", "");
        card = card.substring(1);
        var cardParts = card.split(" \\| ");
        Arrays.stream(cardParts[0].split(" "))
                .map(Integer::parseInt)
                .forEach(winingNumbers::add);
        Arrays.stream(cardParts[1].split(" "))
                .map(Integer::parseInt)
                .forEach(numbers::add);
    }
}
