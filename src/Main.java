import advent.day01.Day01;
import advent.day02.Day02;
import advent.day03.Day03;
import advent.day04.Day04;
import advent.day05.Day05;
import advent.day06.Day06;
import advent.day07.Day07;
import advent.day08.Day08;
import advent.day09.Day09;
import advent.day10.Day10;
import advent.day11.Day11;
import advent.day12.Day12;

public class Main {
    public static void main(String[] args) {
//        day01();
//        day02();
//        day03();
//        day04();
//        day05();
//        day06();
//        day07();
//        day08();
//        day09();
//        day10();
//        day11();
        day12();
    }

    private static void day12() {
        Day12 day12 = new Day12();
        day12.firstPart("day12test.txt");
        day12.firstPart("day12input.txt");
        day12.secondPart("day12test.txt");
        day12.secondPart("day12input.txt");
    }

    private static void day11() {
        Day11 day11 = new Day11();
        day11.firstPart("day11test.txt");
        day11.firstPart("day11input.txt");
        day11.secondPart("day11test.txt", 2);
        day11.secondPart("day11test.txt", 10);
        day11.secondPart("day11test.txt", 100);
        day11.secondPart("day11input.txt", 1000000);
    }

    private static void day10() {
        Day10 day10 = new Day10();
        day10.firstPart("day10test.txt");
        day10.firstPart("day10test2.txt");
        day10.firstPart("day10input.txt");
        day10.secondPart("day10test3.txt");
        day10.secondPart("day10test4.txt");
        day10.secondPart("day10input.txt");
    }

    private static void day09() {
        Day09 day09 = new Day09();
        day09.firstPart("day09test.txt");
        day09.firstPart("day09input.txt");
        day09.secondPart("day09test.txt");
        day09.secondPart("day09input.txt");
    }

    private static void day08() {
        Day08 day08 = new Day08();
        day08.firstPart("day08test.txt");
        day08.firstPart("day08input.txt");
        day08.secondPart("day08test2.txt");
        day08.secondPart("day08input.txt");
    }

    private static void day07() {
        Day07 day07 = new Day07();
        day07.firstPart("day07test.txt");
        day07.firstPart("day07input.txt");
        day07.secondPart("day07test.txt");
        day07.secondPart("day07input.txt");
    }

    private static void day06() {
        Day06 day06 = new Day06();
        day06.firstPart();
        day06.secondPart();
    }

    private static void day05() {
        Day05 day05 = new Day05();
        day05.firstPart("day05test.txt");
        day05.firstPart("day05input.txt");
        day05.secondPart("day05test.txt");
        day05.secondPart("day05input.txt");
    }

    private static void day04() {
        Day04 day04 = new Day04();
        day04.firstPart("day04test.txt");
        day04.firstPart("day04input.txt");
        day04.secondPart("day04test.txt");
        day04.secondPart("day04input.txt");
    }

    private static void day03() {
        Day03 day03 = new Day03();
        day03.firstPart("day03test.txt");
        day03.firstPart("day03input.txt");
        day03.secondPart("day03test.txt");
        day03.secondPart("day03input.txt");
    }

    private static void day02() {
        Day02 day02 = new Day02();
        day02.firstPart("day02test.txt");
        day02.firstPart("day02input.txt");
        day02.secondPart("day02test.txt");
        day02.secondPart("day02input.txt");
    }

    private static void day01() {
        Day01 day01 = new Day01();
        day01.firstPart("day01test.txt");
        day01.firstPart("day01input.txt");
        day01.secondPart("day01test.txt");
        day01.secondPart("day01test2.txt");
        day01.secondPart("day01input.txt");
    }
}