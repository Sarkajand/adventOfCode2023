package advent.day24;

import advent.InputReader;

import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.List;

public class Day24 {

    public void firstPart(String filePath, long minPosition, long maxPosition) {
        var hailstones = processInput(filePath);
        hailstones.forEach(this::countSlopeAndYIntercept);
        var count = 0;
        for (int i = 0; i < hailstones.size() - 1; i++) {
            for (int j = i + 1; j < hailstones.size(); j++) {
                var h1 = hailstones.get(i);
                var h2 = hailstones.get(j);
                var intersection = intersection(h1, h2);
                if (intersection != null
                        && intersection.getX() >= minPosition && intersection.getX() <= maxPosition
                        && intersection.getY() >= minPosition && intersection.getY() <= maxPosition
                        && !intersectionIsInThePast(intersection, h1)
                        && !intersectionIsInThePast(intersection, h2)) {
                    count++;
                }
            }
        }
        System.out.println(count);
    }

//    public void secondPart(String filePath) {
//        x+t1*vx = h1.x + t1 * h1.vx
//        y+t1*vy = h1.y + t1 * h1.vy
//        z+t1*vz = h1.z + t1 * h1.vz
//        x+t2*vx = h2.x + t1 * h2.vx
//        y+t2*vy = h2.y + t1 * h2.vy
//        z+t2*vz = h2.z + t1 * h2.vz
//        x+t3*vx = h3.x + t1 * h3.vx
//        y+t3*vy = h3.y + t1 * h3.vy
//        z+t3*vz = h3.z + t1 * h3.vz
//    }

    private boolean intersectionIsInThePast(Point2D point, Hailstone h){
        if (h.vx > 0) {
            return h.x > point.getX();
        } else {
            return h.x < point.getX();
        }
    }

    private Point2D intersection(Hailstone h1, Hailstone h2) {
        if (h1.m == h2.m) {
            return null;
        }
        double x = (h2.b - h1.b) / (h1.m - h2.m);
        double y = h1.m * x + h1.b;
        return new Point2D.Double(x, y);
    }

    private void countSlopeAndYIntercept(Hailstone hailstone) {
//        y = mx + b
        hailstone.m = (double) ((hailstone.y + hailstone.vy) - hailstone.y) / ((hailstone.x + hailstone.vx) - hailstone.x);
        hailstone.b = -(hailstone.m * hailstone.x) + hailstone.y;
    }

    private List<Hailstone> processInput(String filePath) {
        var input = InputReader.readInput(filePath);
        List<Hailstone> hailstones = new ArrayList<>();
        for (var line : input) {
            var s = line.split(" @ ");
            var hailstone = new Hailstone();
            var s1 = s[0].split(", ");
            hailstone.x = Long.parseLong(s1[0].trim());
            hailstone.y = Long.parseLong(s1[1].trim());
            hailstone.z = Long.parseLong(s1[2].trim());
            s1 = s[1].split(", ");
            hailstone.vx = Long.parseLong(s1[0].trim());
            hailstone.vy = Long.parseLong(s1[1].trim());
            hailstone.vz = Long.parseLong(s1[2].trim());
            hailstones.add(hailstone);
        }
        return hailstones;
    }

    private static class Hailstone {
        long x;
        long y;
        long z;
        long vx;
        long vy;
        long vz;
        double m;
        double b;
    }
}
