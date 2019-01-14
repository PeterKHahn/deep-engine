package game.physics.math;


import game.environment.Vector;

public class Geom2D {

    public static double distance(Vector p1, Vector p2) {
        return Math.sqrt(distanceSquared(p1, p2));
    }

    public static double distanceSquared(Vector p1, Vector p2) {
        return (p1.x - p2.x) * (p1.x - p2.x) + (p1.y - p2.y) * (p1.y - p2.y);
    }


}
