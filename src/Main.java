import advent.day01.Day01;
import advent.day02.Day02;
import advent.day03.Day03;
import advent.day04.Day04;

public class Main {
    public static void main(String[] args) {
//        day01();
//        day02();
//        day03();
        day04();
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