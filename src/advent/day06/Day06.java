package advent.day06;

public class Day06 {

    public void firstPart() {
        var race1 = winCount(7, 9);
        var race2 = winCount(15, 40);
        var race3 = winCount(30, 200);
        System.out.println(race1 * race2 * race3);

        var race21 = winCount(52, 426);
        var race22 = winCount(94, 1374);
        var race23 = winCount(75, 1279);
        var race24 = winCount(94, 1216);
        System.out.println(race21 * race22 * race23 * race24);
    }

    public void secondPart() {
        System.out.println(winCount2(71530, 940200));
        System.out.println(winCount2(52947594, 426137412791216L));
    }

    private long winCount2(long maxTime, long minDistance) {
        long distance = 0;
        long time = 0;
        while (distance <= minDistance) {
            long speed = time;
            long remainingTime = maxTime - time;
            distance = speed * remainingTime;
            time++;
        }
        long startWining = time - 1;

        time = maxTime - 1;
        distance = 0;
        while (distance <= minDistance) {
            long speed = time;
            long remainingTime = maxTime - time;
            distance = speed * remainingTime;
            time--;
        }
        long endWining = time + 2;

        return endWining - startWining;
    }

    private Integer winCount(int maxTime, int minDistance) {
        int winCount = 0;
        for (int time = 1; time <= maxTime; time++) {
            int speed = time;
            int remainingTime = maxTime - time;
            int distance = speed * remainingTime;
            if (distance > minDistance) {
                winCount++;
            }
        }
        return winCount;
    }
}
