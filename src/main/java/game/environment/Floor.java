package game.environment;

public class Floor extends EnvironmentObject {

    private final Point left;
    private final Point right;
    private final double length;
    private final double height;

    public Floor(Point left, double length) {

        this.left = left;
        this.length = length;
        this.height = left.y;
        this.right = new Point(left.x + length, left.y);
    }


    @Override
    public void actOn(CollisionEnvironment environment) {
        Point previous = environment.getPreviousEcb().bottom();
        Point projected = environment.getProjectedEcb().bottom();

        if (previous.y < height || projected.y > height) {
            return;
        }

        double linearX = projected.x - previous.x;
        double linearY = projected.y - previous.y;

        if (linearY == 0) {
            return;
        }

        double t = (height - previous.y) / linearY;
        double x = linearX * t + previous.x;

        if (x > left.x && x < right.x) {
            // WE have a collision!!
            environment.setYAdjustment(height - projected.y);

        }
    }
}
