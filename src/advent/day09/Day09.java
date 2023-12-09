package advent.day09;

import advent.InputReader;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Day09 {

    public void firstPart(String filePath) {
        var input = InputReader.readInput(filePath);
        var sum = 0L;
        for (var line : input) {
            var sequences = getSequences(line);

            var a = 0;
            for (int i = sequences.size() - 2; i > -1; i--) {
                var b = sequences.get(i).getLast();
                sequences.get(i).add(a + b);
                a += b;
            }
            sum += a;
        }
        System.out.println(sum);
    }

    public void secondPart(String filePath) {
        var input = InputReader.readInput(filePath);
        var sum = 0L;
        for (var line : input) {
            var sequences = getSequences(line);

            var a = 0;
            for (int i = sequences.size() - 2; i > -1; i--) {
                var b = sequences.get(i).getFirst();
                sequences.get(i).add(0, b - a);
                a = b - a;
            }
            sum += a;
        }

        System.out.println(sum);
    }

    private List<List<Integer>> getSequences(String input) {
        var numbers = Arrays.stream(input.split(" "))
                .map(Integer::parseInt)
                .toList();
        List<List<Integer>> sequences = new ArrayList<>();
        List<Integer> lastSequence = new ArrayList<>(numbers);
        List<Integer> newSequence = List.of(1, 2, 3);
        sequences.add(lastSequence);
        while (newSequence.stream().anyMatch(x -> x != 0)) {
            newSequence = new ArrayList<>();
            for (int i = 0; i < lastSequence.size() - 1; i++) {
                var a = lastSequence.get(i);
                var b = lastSequence.get(i + 1);
                newSequence.add(b - a);
            }
            sequences.add(newSequence);
            lastSequence = newSequence;
        }
        return sequences;
    }
}
