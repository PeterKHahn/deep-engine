package engine.physics.collision;

import static engine.physics.math.Geom2D.distance;

public class Vector {

    public final double x;
    public final double y;
    private final double magnitude;

    public Vector(double x, double y) {
        this.x = x;
        this.y = y;
        this.magnitude = distance(Point.origin, new Point(x, y));
    }


    public Vector projection(Vector b) {
        return mult(component(b) / magnitude);
    }

    public double component(Vector b) {
        return this.dot(b) / magnitude;
    }

    public double dot(Vector b) {
        return this.x * b.x + this.y * b.y;
    }

    public Vector mult(double scalar) {
        return new Vector(x * scalar, y * scalar);
    }
}
