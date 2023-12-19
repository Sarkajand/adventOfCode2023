package advent.day19;

import advent.InputReader;

import java.util.*;

public class Day19 {

    private record Rule(char category, char operator, int limit, String result) {
    }

    private record Part(int x, int m, int a, int s) {
    }

    private record Combination(long minX, long maxX, long minM, long maxM, long minA, long maxA, long minS, long maxS) {
    }

    public void firstPart(String filePath) {
        List<Part> parts = new ArrayList<>();
        Map<String, Workflow> workflows = new HashMap<>();
        processInput(filePath, parts, workflows);
        List<Part> accepted = new ArrayList<>();
        List<Part> rejected = new ArrayList<>();
        for (var part : parts) {
            workflows.get("in").processPart(part, accepted, rejected, workflows);
        }
        var sum = accepted.stream()
                .mapToInt(p -> p.x + p.m + p.a + p.s)
                .sum();
        System.out.println(sum);
    }

    public void secondPart(String filePath) {
        Map<String, Workflow> workflows = new HashMap<>();
        processInput(filePath, null, workflows);
        List<Combination> accepted = new ArrayList<>();
        var combination = new Combination(1, 4000, 1, 4000, 1, 4000, 1, 4000);
        getCombinations(workflows.get("in"), combination, accepted, workflows);

        var sum = accepted.stream()
                .mapToLong(c -> (c.maxX - c.minX + 1) * (c.maxM - c.minM + 1) * (c.maxA - c.minA + 1) * (c.maxS - c.minS + 1))
                .sum();
        System.out.println(sum);
    }

    private void getCombinations(Workflow workflow, Combination combination, List<Combination> accepted, Map<String, Workflow> workflows) {
        for (var rule : workflow.rules) {
            if (rule.category == '0') {
                if (rule.result.equals("A")) {
                    accepted.add(combination);
                } else if (!rule.result.equals("R")) {
                    getCombinations(workflows.get(rule.result), combination, accepted, workflows);
                }
            } else {
                Combination newCombination = null;
                if (rule.category == 'x') {
                    if (rule.operator == '<') {
                        newCombination = new Combination(combination.minX, rule.limit - 1, combination.minM, combination.maxM, combination.minA, combination.maxA, combination.minS, combination.maxS);
                        combination = new Combination(rule.limit, combination.maxX, combination.minM, combination.maxM, combination.minA, combination.maxA, combination.minS, combination.maxS);
                    } else {
                        newCombination = new Combination(rule.limit + 1, combination.maxX, combination.minM, combination.maxM, combination.minA, combination.maxA, combination.minS, combination.maxS);
                        combination = new Combination(combination.minX, rule.limit, combination.minM, combination.maxM, combination.minA, combination.maxA, combination.minS, combination.maxS);
                    }
                } else if (rule.category == 'm') {
                    if (rule.operator == '<') {
                        newCombination = new Combination(combination.minX, combination.maxX, combination.minM, rule.limit - 1, combination.minA, combination.maxA, combination.minS, combination.maxS);
                        combination = new Combination(combination.minX, combination.maxX, rule.limit, combination.maxM, combination.minA, combination.maxA, combination.minS, combination.maxS);
                    } else {
                        newCombination = new Combination(combination.minX, combination.maxX, rule.limit + 1, combination.maxM, combination.minA, combination.maxA, combination.minS, combination.maxS);
                        combination = new Combination(combination.minX, combination.maxX, combination.minM, rule.limit, combination.minA, combination.maxA, combination.minS, combination.maxS);
                    }
                } else if (rule.category == 'a') {
                    if (rule.operator == '<') {
                        newCombination = new Combination(combination.minX, combination.maxX, combination.minM, combination.maxM, combination.minA, rule.limit - 1, combination.minS, combination.maxS);
                        combination = new Combination(combination.minX, combination.maxX, combination.minM, combination.maxM, rule.limit, combination.maxA, combination.minS, combination.maxS);
                    } else {
                        newCombination = new Combination(combination.minX, combination.maxX, combination.minM, combination.maxM, rule.limit + 1, combination.maxA, combination.minS, combination.maxS);
                        combination = new Combination(combination.minX, combination.maxX, combination.minM, combination.maxM, combination.minA, rule.limit, combination.minS, combination.maxS);
                    }
                } else if (rule.category == 's') {
                    if (rule.operator == '<') {
                        newCombination = new Combination(combination.minX, combination.maxX, combination.minM, combination.maxM, combination.minA, combination.maxA, combination.minS, rule.limit - 1);
                        combination = new Combination(combination.minX, combination.maxX, combination.minM, combination.maxM, combination.minA, combination.maxA, rule.limit, combination.maxS);
                    } else {
                        newCombination = new Combination(combination.minX, combination.maxX, combination.minM, combination.maxM, combination.minA, combination.maxA, rule.limit + 1, combination.maxS);
                        combination = new Combination(combination.minX, combination.maxX, combination.minM, combination.maxM, combination.minA, combination.maxA, combination.minS, rule.limit);
                    }
                }
                if (rule.result.equals("A")) {
                    accepted.add(newCombination);
                } else if (!rule.result.equals("R")) {
                    getCombinations(workflows.get(rule.result), newCombination, accepted, workflows);
                }
            }
        }
    }

    private void processInput(String filePath, List<Part> parts, Map<String, Workflow> workflows) {
        var input = InputReader.readInput(filePath);
        var row = input.removeFirst();
        while (!row.isEmpty()) {
            var workflow = new Workflow();
            var rs = row.split("\\{");
            workflow.name = rs[0];
            rs = rs[1].split(",");
            for (var r : rs) {
                if (r.contains(":")) {
                    var a = r.split(":");
                    var rule = new Rule(r.charAt(0), r.charAt(1), Integer.parseInt(a[0].substring(2)), a[1]);
                    workflow.rules.add(rule);
                } else {
                    var rule = new Rule('0', '0', 0, r.substring(0, r.length() - 1));
                    workflow.rules.add(rule);
                }
            }

            workflows.put(workflow.name, workflow);
            row = input.removeFirst();
        }

        while (parts != null && !input.isEmpty()) {
            row = input.removeFirst();
            row = row.substring(1, row.length() - 1);
            var rs = row.split(",");
            var part = new Part(Integer.parseInt(rs[0].substring(2)), Integer.parseInt(rs[1].substring(2)), Integer.parseInt(rs[2].substring(2)), Integer.parseInt(rs[3].substring(2)));
            parts.add(part);
        }
    }

    private static class Workflow {
        String name;
        List<Rule> rules = new ArrayList<>();

        public void processPart(Part part, List<Part> accepted, List<Part> rejected, Map<String, Workflow> workflows) {
            for (var rule : rules) {
                boolean result;
                if (rule.category == '0') {
                    result = true;
                } else {
                    int partValue = switch (rule.category) {
                        case 'x' -> part.x;
                        case 'm' -> part.m;
                        case 'a' -> part.a;
                        case 's' -> part.s;
                        default -> 0;
                    };
                    result = switch (rule.operator) {
                        case '>' -> partValue > rule.limit;
                        case '<' -> partValue < rule.limit;
                        default -> false;
                    };
                }

                if (result && Objects.equals(rule.result, "A")) {
                    accepted.add(part);
                    break;
                } else if (result && Objects.equals(rule.result, "R")) {
                    rejected.add(part);
                    break;
                } else if (result) {
                    workflows.get(rule.result).processPart(part, accepted, rejected, workflows);
                    break;
                }
            }
        }
    }
}
