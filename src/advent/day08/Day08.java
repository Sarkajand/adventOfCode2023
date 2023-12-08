package advent.day08;

import advent.InputReader;

import java.math.BigInteger;
import java.util.*;

public class Day08 {

    public void firstPart(String filePath) {
        var input = InputReader.readInput(filePath);
        Map<String, String[]> map = getMap(input);
        var instructions = input.get(0);
        var position = "AAA";
        int i = 0;
        var steps = 0;
        while (!Objects.equals(position, "ZZZ")) {
            var options = map.get(position);
            if (instructions.charAt(i) == 'L') {
                position = options[0];
            } else {
                position = options[1];
            }
            i = i < instructions.length() - 1 ? i + 1 : 0;
            steps++;
        }
        System.out.println(steps);
    }

    public void secondPart(String filePath) {
        var input = InputReader.readInput(filePath);
        Map<String, String[]> map = getMap(input);
        var instructions = input.get(0);
        var positions = map.keySet().stream().filter(x -> x.matches("..A")).toList();
        int i = 0;
        List<Integer> steps = new ArrayList<>();

        for (var position : positions) {
            var count = 0;
            while (!position.matches("..Z")) {
                var options = map.get(position);
                if (instructions.charAt(i) == 'L') {
                    position = options[0];
                } else {
                    position = options[1];
                }
                i = i < instructions.length() - 1 ? i + 1 : 0;

                count++;
            }
            steps.add(count);
        }

        steps.add(instructions.length());
        BigInteger lcm = BigInteger.ONE;
        for (Integer step : steps) {
            lcm = lcm(lcm, BigInteger.valueOf(step));
        }

        System.out.println(lcm);

    }

    private Map<String, String[]> getMap(List<String> input) {
        Map<String, String[]> map = new HashMap<>();
        for (int i = 2; i < input.size(); i++) {
            var line = input.get(i);
            map.put(line.substring(0, 3), line.substring(7, 15).split(", "));
        }
        return map;
    }

    private BigInteger lcm(BigInteger number1, BigInteger number2) {
        BigInteger gcd = number1.gcd(number2);
        BigInteger absProduct = number1.multiply(number2).abs();
        return absProduct.divide(gcd);
    }
}
