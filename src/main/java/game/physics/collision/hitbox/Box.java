package game.physics.collision.hitbox;

import game.environment.Vector;
import math.Geom2D;

public class Box {


    public final Vector origin;
    public final Vector offset;
    public final double radius;

    public Box(Vector origin, Vector offset, double radius) {
        this.origin = origin;
        this.offset = offset;
        this.radius = radius;
    }


    public boolean collides(Box other) {

        double distSq = Geom2D.distanceSquared(this.center(), other.center());
        double radSum = (this.radius + other.radius);
        return distSq < radSum * radSum;
    }

    public Vector center() {
        return new Vector(origin, offset);
    }
}
