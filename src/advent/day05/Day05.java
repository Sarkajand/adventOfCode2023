package advent.day05;

import advent.InputReader;

import java.util.*;

public class Day05 {

    public void firstPart(String filePath) {
        List<String> input = InputReader.readInput(filePath);
        long minLocation = Long.MAX_VALUE;
        List<Long> seeds = Arrays.stream(input.get(0).replace("seeds:", "").trim().split(" "))
                .map(Long::parseLong)
                .toList();
        for (Long seed : seeds) {
            int row = 3;

            long soil = seed;
            while (!input.get(row).isEmpty()) {
                String[] inputs = input.get(row).trim().split(" ");
                long drs = Long.parseLong(inputs[0]);
                long srs = Long.parseLong(inputs[1]);
                long rl = Long.parseLong(inputs[2]);
                if (seed >= srs && seed < srs + rl) {
                    soil = (drs - srs) + seed;
                }
                row++;
            }

            row += 2;
            long fertilizer = soil;
            while (!input.get(row).isEmpty()) {
                String[] inputs = input.get(row).trim().split(" ");
                long drs = Long.parseLong(inputs[0]);
                long srs = Long.parseLong(inputs[1]);
                long rl = Long.parseLong(inputs[2]);
                if (soil >= srs && soil < srs + rl) {
                    fertilizer = (drs - srs) + soil;
                }
                row++;
            }

            row += 2;
            long water = fertilizer;
            while (!input.get(row).isEmpty()) {
                String[] inputs = input.get(row).trim().split(" ");
                long drs = Long.parseLong(inputs[0]);
                long srs = Long.parseLong(inputs[1]);
                long rl = Long.parseLong(inputs[2]);
                if (fertilizer >= srs && fertilizer < srs + rl) {
                    water = (drs - srs) + fertilizer;
                }
                row++;
            }

            row += 2;
            long light = water;
            while (!input.get(row).isEmpty()) {
                String[] inputs = input.get(row).trim().split(" ");
                long drs = Long.parseLong(inputs[0]);
                long srs = Long.parseLong(inputs[1]);
                long rl = Long.parseLong(inputs[2]);
                if (water >= srs && water < srs + rl) {
                    light = (drs - srs) + water;
                }
                row++;
            }

            row += 2;
            long temperature = light;
            while (!input.get(row).isEmpty()) {
                String[] inputs = input.get(row).trim().split(" ");
                long drs = Long.parseLong(inputs[0]);
                long srs = Long.parseLong(inputs[1]);
                long rl = Long.parseLong(inputs[2]);
                if (light >= srs && light < srs + rl) {
                    temperature = (drs - srs) + light;
                }
                row++;
            }

            row += 2;
            long humidity = temperature;
            while (!input.get(row).isEmpty()) {
                String[] inputs = input.get(row).trim().split(" ");
                long drs = Long.parseLong(inputs[0]);
                long srs = Long.parseLong(inputs[1]);
                long rl = Long.parseLong(inputs[2]);
                if (temperature >= srs && temperature < srs + rl) {
                    humidity = (drs - srs) + temperature;
                }
                row++;
            }

            row += 2;
            long location = humidity;
            while (row < input.size()) {
                String[] inputs = input.get(row).trim().split(" ");
                long drs = Long.parseLong(inputs[0]);
                long srs = Long.parseLong(inputs[1]);
                long rl = Long.parseLong(inputs[2]);
                if (humidity >= srs && humidity < srs + rl) {
                    location = (drs - srs) + humidity;
                }
                row++;
            }

            System.out.printf("Seed %s, soil %s, fertilizer %s, water %s, light %s, temperature %s, humidity %s, location %s%n",
                    seed, soil, fertilizer, water, light, temperature, humidity, location);
            minLocation = Math.min(minLocation, location);
        }

        System.out.println(minLocation);
    }

    public void secondPart(String filePath) {
        List<String> input = InputReader.readInput(filePath);
        Queue<Long[]> seeds = new LinkedList<>();
        String[] seedValues = input.get(0).replace("seeds:", "").trim().split(" ");
        for (int i = 0; i < seedValues.length; i += 2) {
            Long[] seedRange = new Long[2];
            seedRange[0] = Long.parseLong(seedValues[i]);
            seedRange[1] = seedRange[0] + Long.parseLong(seedValues[i + 1]) - 1;
            seeds.add(seedRange);
        }

        int row = 3;
        List<Long[]> soilMap = new ArrayList<>();
        row = processMap(input, soilMap, row);
        Queue<Long[]> soils = new LinkedList<>();
        processRanges(seeds, soils, soilMap);
        printRanges(soils);

        List<Long[]> fertilizerMap = new ArrayList<>();
        row = processMap(input, fertilizerMap, row);
        Queue<Long[]> fertilizers = new LinkedList<>();
        processRanges(soils, fertilizers, fertilizerMap);
        printRanges(fertilizers);

        List<Long[]> waterMap = new ArrayList<>();
        row = processMap(input, waterMap, row);
        Queue<Long[]> waters = new LinkedList<>();
        processRanges(fertilizers, waters, waterMap);
        printRanges(waters);

        List<Long[]> lightMap = new ArrayList<>();
        row = processMap(input, lightMap, row);
        Queue<Long[]> lights = new LinkedList<>();
        processRanges(waters, lights, lightMap);
        printRanges(lights);

        List<Long[]> temperatureMap = new ArrayList<>();
        row = processMap(input, temperatureMap, row);
        Queue<Long[]> temperatures = new LinkedList<>();
        processRanges(lights, temperatures, temperatureMap);
        printRanges(temperatures);

        List<Long[]> humidityMap = new ArrayList<>();
        row = processMap(input, humidityMap, row);
        Queue<Long[]> humidyty = new LinkedList<>();
        processRanges(temperatures, humidyty, humidityMap);
        printRanges(humidyty);

        List<Long[]> locationMap = new ArrayList<>();
        processMap(input, locationMap, row);
        Queue<Long[]> locations = new LinkedList<>();
        processRanges(humidyty, locations, locationMap);
        printRanges(locations);

        long minLocation = locations.stream().mapToLong(x -> x[0]).min().orElse(-1);
        System.out.println(minLocation);
    }

    private int processMap(List<String> input, List<Long[]> map, int row) {
        while (row < input.size() && !input.get(row).isEmpty()) {
            String[] inputs = input.get(row).trim().split(" ");
            long drs = Long.parseLong(inputs[0]);
            long srs = Long.parseLong(inputs[1]);
            long rl = Long.parseLong(inputs[2]);

            Long[] soilRange = new Long[3];
            soilRange[0] = srs;
            soilRange[1] = srs + rl - 1;
            soilRange[2] = drs - srs;
            map.add(soilRange);

            row++;
        }
        row += 2;
        return row;
    }

    private void processRanges(Queue<Long[]> input, Queue<Long[]> output, List<Long[]> map) {
        while (!input.isEmpty()) {
            Long[] inputRange = input.remove();
            Long[] startRange = map.stream()
                    .filter(x -> inputRange[0] >= x[0] && inputRange[0] <= x[1])
                    .findFirst().orElse(null);

            if (startRange != null && inputRange[1] <= startRange[1]) {
                Long[] range = new Long[2];
                range[0] = inputRange[0] + startRange[2];
                range[1] = inputRange[1] + startRange[2];
                output.add(range);
            } else if (startRange != null) {
                Long[] range = new Long[2];
                range[0] = inputRange[0] + startRange[2];
                range[1] = startRange[1] + startRange[2];
                output.add(range);

                Long[] remainingSeedRange = new Long[2];
                remainingSeedRange[0] = startRange[1] + 1;
                remainingSeedRange[1] = inputRange[1];
                input.add(remainingSeedRange);
            } else {
                Long[] endRange = map.stream()
                        .filter(x -> inputRange[1] > x[0] && inputRange[1] < x[1])
                        .findFirst().orElse(null);
                if (endRange != null) {
                    Long[] range = new Long[2];
                    range[0] = endRange[0] + endRange[2];
                    range[1] = inputRange[1] + endRange[2];
                    output.add(range);

                    Long[] remainingSeedRange = new Long[2];
                    remainingSeedRange[0] = inputRange[0];
                    remainingSeedRange[1] = endRange[0] - 1;
                    input.add(remainingSeedRange);
                } else {
                    output.add(inputRange);
                }
            }
        }
    }

    private void printRanges(Queue<Long[]> input) {
        StringBuilder result = new StringBuilder();
        input.forEach(x -> result.append(String.format("%d, %d | ", x[0], x[1])));
        System.out.println(result);
    }
}
