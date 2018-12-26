package game.environment;

public class Point {

    public static final Point ORIGIN = new Point(0, 0);

    public double x;
    public double y;

    public Point(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public Point(Point p) {
        this.x = p.x;
        this.y = p.y;
    }


    @Override
    public String toString() {
        return "x: " + x + " y: " + y;
    }

}
