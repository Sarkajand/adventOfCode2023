package advent.day20;

import advent.InputReader;

import java.math.BigInteger;
import java.util.*;

public class Day20 {

    private record Pulse(int pulse, String fromModule, String toModule) {
    }

    public void firstPart(String filePath) {
        Map<String, Module> modules = processInput(filePath);

        for (int i = 0; i < 1000; i++) {
            Queue<Pulse> pulses = new LinkedList<>();
            pulses.add(new Pulse(0, "", "broadcaster"));
            while (!pulses.isEmpty()) {
                var pulse = pulses.remove();
                var module = modules.get(pulse.toModule);

                if (pulse.pulse == 1) {
                    module.high++;
                } else {
                    module.low++;
                }

                if (Objects.equals(module.type, "%")) {
                    if (pulse.pulse == 0) {
                        module.on = !module.on;
                        if (module.on) {
                            for (var nextModule : module.connections) {
                                pulses.add(new Pulse(1, module.name, nextModule));
                            }
                        } else {
                            for (var nextModule : module.connections) {
                                pulses.add(new Pulse(0, module.name, nextModule));
                            }
                        }
                    }
                } else if (Objects.equals(module.type, "&")) {
                    module.lastPulseMap.put(pulse.fromModule, pulse.pulse);
                    if (module.lastPulseMap.values().stream().allMatch(x -> x == 1)) {
                        for (var nextModule : module.connections) {
                            pulses.add(new Pulse(0, module.name, nextModule));
                        }
                    } else {
                        for (var nextModule : module.connections) {
                            pulses.add(new Pulse(1, module.name, nextModule));
                        }
                    }
                } else if (Objects.equals(module.type, "broadcaster")) {
                    for (var nextModule : module.connections) {
                        pulses.add(new Pulse(pulse.pulse, module.name, nextModule));
                    }
                }
            }
        }

        var low = modules.values().stream().mapToInt(m -> m.low).sum();
        var high = modules.values().stream().mapToInt(m -> m.high).sum();
        var result = low * high;
        System.out.println(result);
    }

    public void secondPart(String filePath) {
        Map<String, Module> modules = processInput(filePath);
        Map<String, Long> cycleLengths = new HashMap<>();

        var i = 0L;
        while (cycleLengths.size() < modules.size() - 2) {
            i++;
            Queue<Pulse> pulses = new LinkedList<>();
            pulses.add(new Pulse(0, "", "broadcaster"));
            while (!pulses.isEmpty()) {
                var pulse = pulses.remove();
                var module = modules.get(pulse.toModule);

                if (module == null) {
                    continue;
                }

                if (Objects.equals(module.type, "%")) {
                    if (pulse.pulse == 0) {
                        module.on = !module.on;
                        if (module.on) {
                            for (var nextModule : module.connections) {
                                cycleLengths.putIfAbsent(module.name, i);
                                pulses.add(new Pulse(1, module.name, nextModule));
                            }
                        } else {
                            for (var nextModule : module.connections) {
                                pulses.add(new Pulse(0, module.name, nextModule));
                            }
                        }
                    }
                } else if (Objects.equals(module.type, "&")) {
                    module.lastPulseMap.put(pulse.fromModule, pulse.pulse);
                    if (module.lastPulseMap.values().stream().allMatch(x -> x == 1)) {
                        for (var nextModule : module.connections) {
                            pulses.add(new Pulse(0, module.name, nextModule));
                        }
                    } else {
                        for (var nextModule : module.connections) {
                            cycleLengths.putIfAbsent(module.name, i);
                            pulses.add(new Pulse(1, module.name, nextModule));
                        }
                    }
                } else if (Objects.equals(module.type, "broadcaster")) {
                    for (var nextModule : module.connections) {
                        pulses.add(new Pulse(pulse.pulse, module.name, nextModule));
                    }
                }
            }
        }

        var result = 1L;
        var lastBeforeOutput = modules.values().stream().filter(m -> m.connections.contains("rx")).findFirst().get().name;
        for (var cycleLength : cycleLengths.entrySet()) {
            var name = cycleLength.getKey();
            if (modules.get(name).connections.contains(lastBeforeOutput)) {
                result = result * cycleLength.getValue() / BigInteger.valueOf(result).gcd(BigInteger.valueOf(cycleLength.getValue())).longValue();
            }
        }

        System.out.println(result);
    }


    private Map<String, Module> processInput(String filePath) {
        Map<String, Module> modules = new HashMap<>();
        var input = InputReader.readInput(filePath);
        for (var row : input) {
            var module = new Module();
            var rs = row.split(" -> ");
            if (Objects.equals(rs[0], "broadcaster")) {
                module.name = "broadcaster";
                module.type = "broadcaster";
            } else {
                module.name = rs[0].substring(1);
                module.type = rs[0].substring(0, 1);
            }
            module.connections = Arrays.stream(rs[1].split(", ")).toList();
            modules.put(module.name, module);
        }

        for (var module : modules.values().stream().filter(x -> Objects.equals(x.type, "&")).toList()) {
            var inputs = modules.values().stream().filter(x -> x.connections.contains(module.name)).toList();
            for (var inputModule : inputs) {
                module.lastPulseMap.put(inputModule.name, 0);
            }
        }

        var outputName = modules.values().stream().flatMap(m -> m.connections.stream()).filter(n -> !modules.containsKey(n)).findFirst().orElse("");
        if (!outputName.isEmpty()) {
            var module = new Module();
            module.name = outputName;
            module.type = "output";
            modules.put(module.name, module);
        }

        return modules;
    }

    private class Module {
        String name;
        String type;
        boolean on = false;
        Map<String, Integer> lastPulseMap = new HashMap<>();
        List<String> connections = new ArrayList<>();
        int low = 0;
        int high = 0;
    }
}
