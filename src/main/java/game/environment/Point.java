package game.environment;

public class Point {

    public static final Point ORIGIN = new Point(0, 0);

    public final double x;
    public final double y;

    public Point(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public Point(Point p) {
        this.x = p.x;
        this.y = p.y;
    }

    public Point(Point p, double dx, double dy) {
        this.x = p.x + dx;
        this.y = p.y + dy;
    }


    @Override
    public String toString() {
        return "x: " + x + " y: " + y;
    }

}
