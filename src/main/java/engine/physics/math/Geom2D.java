package engine.physics.math;

import engine.physics.collision.Point;

public class Geom2D {

    public static double distance(Point p1, Point p2) {
        return Math.sqrt(distanceSquared(p1, p2));
    }

    public static double distanceSquared(Point p1, Point p2) {
        return (p1.x - p2.x) * (p1.x - p2.x) + (p1.y - p2.y) * (p1.y - p2.y);
    }
}
