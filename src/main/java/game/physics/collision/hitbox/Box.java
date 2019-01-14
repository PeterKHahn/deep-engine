package game.physics.collision.hitbox;

import game.environment.Vector;
import game.physics.math.Geom2D;

public class Box {

    private double radius;
    private Vector center;

    public Box(Vector center, double radius) {
        this.radius = radius;
        this.center = center;
    }

    public double getRadius() {
        return radius;
    }

    public Vector getCenter() {
        return center;
    }

    public void setCenter(Vector center) {
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
