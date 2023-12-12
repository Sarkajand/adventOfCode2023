package advent.day12;

import advent.InputReader;

import java.util.*;

public class Day12 {

    public void firstPart(String filePath) {
        List<String> springList = new ArrayList<>();
        List<List<Integer>> groupList = new ArrayList<>();
        processInput(filePath, springList, groupList);
        var sum = 0L;
        for (var r = 0; r < springList.size(); r++) {
            var springs = springList.get(r);
            var groups = groupList.get(r);

            var count = countOptions(springs, groups);
            sum += count;
        }

        System.out.println(sum);
    }

    public void secondPart(String filePath) {
        List<String> springList = new ArrayList<>();
        List<List<Integer>> groupList = new ArrayList<>();
        processInput(filePath, springList, groupList);
        var sum = 0L;
        for (var r = 0; r < springList.size(); r++) {
            var springs = springList.get(r);
            var groups = groupList.get(r);

            List<Integer> unfoldedGroups = new ArrayList<>();
            unfoldedGroups.addAll(groups);
            unfoldedGroups.addAll(groups);
            unfoldedGroups.addAll(groups);
            unfoldedGroups.addAll(groups);
            unfoldedGroups.addAll(groups);
            var unfoldedSprings = springs + "?" + springs + "?" + springs + "?" + springs + "?" + springs;
            Map<String, Long> results = new HashMap<>();
            var count = countOptions(unfoldedSprings, unfoldedGroups, 0, 0, 0, results);
            sum += count;
        }

        System.out.println(sum);
    }

    private long countOptions(String springs, List<Integer> groups, int i, int j, int groupLength, Map<String, Long> results) {
        var key = String.format("%s_%s_%s", i, j, groupLength);
        if (results.containsKey(key)) {
            return results.get(key);
        }

        if (i == springs.length()) {
            if (j == groups.size() && groupLength == 0) {
                return 1;
            } else if (j == groups.size() - 1 && groupLength == groups.getLast()) {
                return 1;
            } else
                return 0;
        }

        var ans = 0L;
        if (springs.charAt(i) == '.' || springs.charAt(i) == '?') {
            if (groupLength == 0) {
                ans += countOptions(springs, groups, i + 1, j, groupLength, results);
            } else if (groupLength > 0 && j < groups.size() && groups.get(j) == groupLength) {
                ans += countOptions(springs, groups, i + 1, j + 1, 0, results);
            }
        }

        if (springs.charAt(i) == '#' || springs.charAt(i) == '?') {
            ans += countOptions(springs, groups, i + 1, j, groupLength + 1, results);
        }
        results.put(key, ans);
        return ans;
    }

    private long countOptions(String springs, List<Integer> groups) {
        var count = 0L;
        Queue<String> options = new LinkedList<>();
        options.add(springs);
        while (!options.isEmpty()) {
            var line = options.remove();
            var i = line.indexOf('?');
            if (i == 0) {
                options.add(line.replaceFirst("\\?", "\\."));
                options.add(line.replaceFirst("\\?", "#"));
            } else if (i > 0) {
                var possible = checkLine(line.substring(0, i), groups, false);
                if (possible) {
                    var a = line.replaceFirst("\\?", "\\.");
                    var b = line.replaceFirst("\\?", "\\#");
                    if (!options.contains(a)) {
                        options.add(a);
                    }
                    if (!options.contains(b)) {
                        options.add(b);
                    }
                }
            } else {
                var possible = checkLine(line, groups, true);
                if (possible) {
                    count++;
                }
            }
        }
        return count;
    }

    private boolean checkLine(String line, List<Integer> result, boolean wholeLine) {
        var counts = Arrays.stream(line.split("\\.+"))
                .filter(x -> !x.isEmpty())
                .map(String::length)
                .toList();
        boolean possible = true;
        if (wholeLine) {
            return counts.equals(result);
        } else if (counts.size() > result.size()) {
            return false;
        }

        for (var j = 0; j < counts.size(); j++) {
            if (!Objects.equals(counts.get(j), result.get(j)) && j < counts.size() - 1) {
                possible = false;
                break;
            } else if (j == counts.size() - 1 && counts.get(j) > result.get(j)) {
                possible = false;
                break;
            }
        }
        return possible;
    }

    private void processInput(String filePath, List<String> springs, List<List<Integer>> groups) {
        var input = InputReader.readInput(filePath);
        for (var line : input) {
            var parts = line.split(" ");
            springs.add(parts[0]);
            List<Integer> numbers = new ArrayList<>();
            for (var number : parts[1].split(",")) {
                numbers.add(Integer.parseInt(number));
            }
            groups.add(numbers);
        }
    }
}
