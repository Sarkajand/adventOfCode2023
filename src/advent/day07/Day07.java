package advent.day07;

import advent.InputReader;

import java.util.*;
import java.util.stream.Collectors;

public class Day07 {

    private final Map<Character, Integer> cardStrength = new HashMap<>();

    public record Hand(String cards, int bid) {
    }

    Comparator<Hand> handComparator = (h1, h2) -> {
        var h1kind = cardKind(h1.cards);
        var h2kind = cardKind(h2.cards);
        if (h1kind != h2kind) {
            return h1kind - h2kind;
        } else {
            for (int i = 0; i < 5; i++) {
                if (!Objects.equals(cardStrength.get(h1.cards.charAt(i)), cardStrength.get(h2.cards.charAt(i)))) {
                    return cardStrength.get(h1.cards.charAt(i)) - cardStrength.get(h2.cards.charAt(i));
                } else if (i == 4) {
                    return 0;
                }
            }
        }

        return 0;
    };

    Comparator<Hand> handComparator2 = (h1, h2) -> {
        var h1kind = cardKind2(h1.cards);
        var h2kind = cardKind2(h2.cards);
        if (h1kind != h2kind) {
            return h1kind - h2kind;
        } else {
            for (int i = 0; i < 5; i++) {
                if (!Objects.equals(cardStrength.get(h1.cards.charAt(i)), cardStrength.get(h2.cards.charAt(i)))) {
                    return cardStrength.get(h1.cards.charAt(i)) - cardStrength.get(h2.cards.charAt(i));
                } else if (i == 4) {
                    return 0;
                }
            }
        }

        return 0;
    };

    public Day07() {
        cardStrength.put('A', 13);
        cardStrength.put('K', 12);
        cardStrength.put('Q', 11);
        cardStrength.put('J', 10);
        cardStrength.put('T', 9);
        cardStrength.put('9', 8);
        cardStrength.put('8', 7);
        cardStrength.put('7', 6);
        cardStrength.put('6', 5);
        cardStrength.put('5', 4);
        cardStrength.put('4', 3);
        cardStrength.put('3', 2);
        cardStrength.put('2', 1);
    }

    public void firstPart(String filePath) {
        var input = InputReader.readInput(filePath);
        var hands = processInput(input);
        hands.sort(handComparator);
        var result = 0L;
        for (int i = 0; i < hands.size(); i++) {
            result += (long) hands.get(i).bid * (i + 1);
        }
        System.out.println(result);

    }

    public void secondPart(String filePath) {
        cardStrength.replace('J', 0);
        var input = InputReader.readInput(filePath);
        var hands = processInput(input);
        hands.sort(handComparator2);
        var result = 0L;
        for (int i = 0; i < hands.size(); i++) {
            result += (long) hands.get(i).bid * (i + 1);
        }
        System.out.println(result);
    }

    private int cardKind(String cards) {
        var cardCounts = cards.chars()
                .mapToObj(i -> (char) i)
                .collect(Collectors.groupingBy(Object::toString, Collectors.counting()));

        if (cardCounts.containsValue(5L)) {
            return 7;
        } else if (cardCounts.containsValue(4L)) {
            return 6;
        } else if (cardCounts.containsValue(3L) && cardCounts.containsValue(2L)) {
            return 5;
        } else if (cardCounts.containsValue(3L)) {
            return 4;
        } else if (cardCounts.values().stream().filter(x -> x == 2L).count() == 2) {
            return 3;
        } else if (cardCounts.containsValue(2L)) {
            return 2;
        } else return 1;
    }

    private int cardKind2(String cards) {
        var cardCounts = cards.chars()
                .mapToObj(i -> (char) i)
                .collect(Collectors.groupingBy(Object::toString, Collectors.counting()));

        if (cardCounts.containsValue(5L)
                || (cardCounts.containsValue(4L) && Objects.equals(cardCounts.get("J"), 1L))
                || (cardCounts.containsValue(3L) && Objects.equals(cardCounts.get("J"), 2L))
                || (cardCounts.containsValue(2L) && Objects.equals(cardCounts.get("J"), 3L))
                || (cardCounts.containsValue(1L) && Objects.equals(cardCounts.get("J"), 4L))
        ) {
            return 7;
        } else if (cardCounts.containsValue(4L)
                || (cardCounts.containsValue(3L) && Objects.equals(cardCounts.get("J"), 1L))
                || (cardCounts.containsValue(2L) && Objects.equals(cardCounts.get("J"), 2L) && !Objects.equals(cardCounts.get("J"), 2L))
                || (cardCounts.values().stream().filter(x -> x == 2L).count() == 2 && Objects.equals(cardCounts.get("J"), 2L))
                || (cardCounts.containsValue(1L) && Objects.equals(cardCounts.get("J"), 3L))
        ) {
            return 6;
        } else if (cardCounts.containsValue(3L) && cardCounts.containsValue(2L)
                || (cardCounts.values().stream().filter(x -> x == 2L).count() == 2 && Objects.equals(cardCounts.get("J"), 1L))
        ) {
            return 5;
        } else if (cardCounts.containsValue(3L)
                || (cardCounts.containsValue(2L) && Objects.equals(cardCounts.get("J"), 1L))
                || (cardCounts.containsValue(1L) && Objects.equals(cardCounts.get("J"), 2L))
        ) {
            return 4;
        } else if (cardCounts.values().stream().filter(x -> x == 2L).count() == 2) {
            return 3;
        } else if (cardCounts.containsValue(2L)
                || (cardCounts.containsValue(1L) && Objects.equals(cardCounts.get("J"), 1L))
        ) {
            return 2;
        } else return 1;
    }

    private List<Hand> processInput(List<String> input) {
        List<Hand> hands = new ArrayList<>();
        for (String row : input) {
            var parts = row.split(" ");
            Hand hand = new Hand(parts[0], Integer.parseInt(parts[1]));
            hands.add(hand);
        }

        return hands;
    }
}
