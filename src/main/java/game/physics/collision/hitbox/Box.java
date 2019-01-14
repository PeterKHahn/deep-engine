package game.physics.collision.hitbox;

import game.environment.Point;
import game.physics.math.Geom2D;

public class Box {

    private double radius;
    private Point center;

    public Box(double radius, Point center) {
        this.radius = radius;
        this.center = center;
    }

    public double getRadius() {
        return radius;
    }

    public Point getCenter() {
        return center;
    }

    public void setCenter(Point center) {
        this.center = center;
    }

    public void setRadius(double radius) {
        this.radius = radius;
    }

    public boolean collides(Box other) {

        double distSq = Geom2D.distanceSquared(this.center, other.center);
        double radSum = (this.radius + other.radius);
        return distSq < radSum * radSum;
    }
}
