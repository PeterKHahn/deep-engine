package game.environment;

public class Vector {

    public static final Vector ORIGIN = new Vector(0, 0);

    public final double x;
    public final double y;

    public Vector(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public Vector(Vector v) {
        this.x = v.x;
        this.y = v.y;
    }

    public Vector(Vector v, double dx, double dy) {
        this.x = v.x + dx;
        this.y = v.y + dy;
    }

    public Vector(Vector v, Vector offset) {
        this.x = v.x + offset.x;
        this.y = v.y + offset.y;
    }


    @Override
    public String toString() {
        return "x: " + x + " y: " + y;
    }

}
