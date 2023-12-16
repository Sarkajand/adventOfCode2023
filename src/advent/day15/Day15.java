package advent.day15;

import advent.InputReader;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Day15 {

    public record Lens(String label, int focalLength) {
    }

    public void firstPart(String filePath) {
        List<String> input = InputReader.readInput(filePath);
        var sum = 0L;
        for (var str : input.get(0).split(",")) {
            sum += hash(str);
        }
        System.out.println(sum);
    }

    public void secondPart(String filePath) {
        List<String> input = InputReader.readInput(filePath);
        var sum = 0L;
        List<List<Lens>> map = Stream.generate(ArrayList<Lens>::new)
                .limit(256)
                .collect(Collectors.toList());

        for (var str : input.get(0).split(",")) {
            var label = str.replaceAll("=|-|[0-9]", "");
            var boxNumber = hash(label);
            var box = map.get(boxNumber);
            if (str.contains("-")) {
                var optLens = box.stream().filter(x -> Objects.equals(x.label, label)).findFirst();
                optLens.ifPresent(box::remove);
            } else {
                var optLens = box.stream().filter(x -> Objects.equals(x.label, label)).findFirst();
                var lens = new Lens(label, Integer.parseInt(str.substring(str.length() - 1)));
                if (optLens.isPresent()) {
                    var i = box.indexOf(optLens.get());
                    box.remove(optLens.get());
                    box.add(i, lens);
                } else {
                    box.add(lens);
                }
            }
        }

        for (var i = 0; i < map.size(); i++) {
            for (var j = 0; j < map.get(i).size(); j++) {
                var lensResult = (i + 1) * (j + 1) * map.get(i).get(j).focalLength;
                sum += lensResult;
            }
        }

        System.out.println(sum);
    }


    private int hash(String str) {
        var hash = 0;
        for (var c : str.toCharArray()) {
            hash += c;
            hash = hash * 17;
            hash = hash % 256;
        }
        return hash;
    }
}
