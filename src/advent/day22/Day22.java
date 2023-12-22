package advent.day22;

import advent.InputReader;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;

public class Day22 {
    public void firstPart(String filePath) {
        var bricks = processInput(filePath);
        settleBricks(bricks);
        var count = 0;
        for (var brick : bricks) {
            if (canBeDisintegrated(brick, bricks)) {
                count++;
            }
        }

        System.out.println(count);
    }

    public void secondPart(String filePath) {
        var bricks = processInput(filePath);
        settleBricks(bricks);
        List<Brick> supportingBricks = bricks.stream()
                .filter(b -> !canBeDisintegrated(b, bricks))
                .toList();

        var sum = 0;
        for (var supportingBrick : supportingBricks) {
            List<Brick> newBricks = new ArrayList<>();
            for (var b : bricks) {
                if (b != supportingBrick) {
                    newBricks.add(new Brick(b.x1, b.y1, b.z1, b.x2, b.y2, b.z2));
                }
            }
            sum += settleBricks(newBricks);
        }

        System.out.println(sum);
    }

    private boolean canBeDisintegrated(Brick brick, List<Brick> bricks) {
        var aboveBricks = bricks.stream()
                .filter(b -> b != brick && b.z1 == brick.z2 + 1 &&
                        !(b.x2 < brick.x1 || b.x1 > brick.x2 || b.y2 < brick.y1 || b.y1 > brick.y2))
                .toList();

        if (aboveBricks.isEmpty()) {
            return true;
        }

        for (var aboveBrick : aboveBricks) {
            var hasSupport = bricks.stream()
                    .anyMatch(b -> b != brick && b != aboveBrick && b.z2 + 1 == aboveBrick.z1 &&
                            !(b.x2 < aboveBrick.x1 || b.x1 > aboveBrick.x2 || b.y2 < aboveBrick.y1 || b.y1 > aboveBrick.y2));
            if (!hasSupport) {
                return false;
            }
        }
        return true;
    }

    private int settleBricks(List<Brick> bricks) {
        bricks.sort(Comparator.comparing(b -> b.z1));
        int fallen = 0;
        for (var brick : bricks) {
            if (brick.z1 == 1) {
                continue;
            }

            var belowBricks = bricks.stream()
                    .filter(b -> b != brick && b.z2 < brick.z1 &&
                            !(b.x2 < brick.x1 || b.x1 > brick.x2 || b.y2 < brick.y1 || b.y1 > brick.y2))
                    .toList();

            if (belowBricks.isEmpty()) {
                fallen++;
                brick.z2 = brick.z2 - brick.z1 + 1;
                brick.z1 = 1;
            }

            var toZ = belowBricks.stream()
                    .mapToInt(b -> b.z2)
                    .max().orElse(0) + 1;

            if (brick.z1 != toZ) {
                fallen++;
                brick.z2 = brick.z2 - brick.z1 + toZ;
                brick.z1 = toZ;
            }
        }

        return fallen;
    }

    private List<Brick> processInput(String filePath) {
        var input = InputReader.readInput(filePath);
        List<Brick> bricks = new ArrayList<>();
        for (var row : input) {
            var brick = new Brick();
            var ends = row.split("~");
            var coordinates = ends[0].split(",");
            brick.x1 = Integer.parseInt(coordinates[0]);
            brick.y1 = Integer.parseInt(coordinates[1]);
            brick.z1 = Integer.parseInt(coordinates[2]);
            coordinates = ends[1].split(",");
            brick.x2 = Integer.parseInt(coordinates[0]);
            brick.y2 = Integer.parseInt(coordinates[1]);
            brick.z2 = Integer.parseInt(coordinates[2]);
            bricks.add(brick);
        }
        return bricks;
    }


    private static class Brick {
        int x1;
        int y1;
        int z1;
        int x2;
        int y2;
        int z2;

        public Brick() {
        }

        public Brick(int x1, int y1, int z1, int x2, int y2, int z2) {
            this.x1 = x1;
            this.y1 = y1;
            this.z1 = z1;
            this.x2 = x2;
            this.y2 = y2;
            this.z2 = z2;
        }

        @Override
        public int hashCode() {
            return Objects.hash(x1, y1, z1, x2, y2, z2);
        }

        @Override
        public boolean equals(Object o) {
            if (o == this) {
                return true;
            }

            if (!(o instanceof Brick b)) {
                return false;
            }

            return x1 == b.x1 && x2 == b.x2 && y1 == b.y1 && y2 == b.y2 && z1 == b.z1 && z2 == b.z2;
        }
    }
}
