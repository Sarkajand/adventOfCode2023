package advent.day03;

import advent.InputReader;

public class Day03 {

    public void firstPart(String filePath) {
        char[][] input = InputReader.readInput2(filePath);
        long sum = 0;

        for (int y = 0; y < input.length; y++) {
            int x = 0;
            while (x < input[y].length) {
                if (Character.isDigit(input[y][x])) {
                    int numberStart = x;
                    String strNumber = "" + input[y][x];
                    x++;
                    while (x < input[y].length && Character.isDigit(input[y][x])) {
                        strNumber += input[y][x];
                        x++;
                    }

                    int numberEnd = x;
                    boolean isPartNumber = false;
                    for (int i = Math.max(y - 1, 0); i < Math.min(y + 2, input.length) && !isPartNumber; i++) {
                        for (int j = Math.max(numberStart - 1, 0); j < Math.min(numberEnd + 1, input[i].length); j++) {
                            if (input[i][j] != '.' && !Character.isDigit(input[i][j])) {
                                isPartNumber = true;
                                break;
                            }
                        }
                    }

                    if (isPartNumber) {
                        int number = Integer.parseInt(strNumber);
                        sum += number;
                    }

                } else {
                    x++;
                }
            }
        }

        System.out.println(sum);

    }

    public void secondPart(String filePath) {
        char[][] input = InputReader.readInput2(filePath);
        long sum = 0;

        for (int y = 0; y < input.length; y++) {
            for (int x = 0; x < input[y].length; x++) {
                if (input[y][x] == '*') {
                    boolean isPartNumber = false;
                    String firstNumber = "";
                    String secondNumber = "";
                    for (int i = Math.max(y - 1, 0); i < Math.min(y + 2, input.length); i++) {
                        for (int j = Math.max(x - 1, 0); j < Math.min(x + 2, input[i].length); j++) {
                            if (Character.isDigit(input[i][j])) {
                                String strNumber = "";
                                int k = j;
                                while (k > 0 && Character.isDigit(input[i][k-1])) {
                                    k--;
                                }

                                while (k < input[i].length && Character.isDigit(input[i][k])) {
                                    strNumber += input[i][k];
                                    k++;
                                }

                                j = Math.max(k-1, j);

                                if (firstNumber.isEmpty()) {
                                    firstNumber = strNumber;
                                } else if (secondNumber.isEmpty()) {
                                    secondNumber = strNumber;
                                    isPartNumber = true;
                                } else {
                                    isPartNumber = false;
                                    break;
                                }
                            }
                        }
                    }

                    if (isPartNumber) {
                        sum += Long.parseLong(firstNumber) * Integer.parseInt(secondNumber);
                    }
                }
            }
        }


        System.out.println(sum);
    }
}
