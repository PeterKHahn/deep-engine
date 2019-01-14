package game.environment;

public class Floor extends EnvironmentObject {

    private final Vector left;
    private final Vector right;
    private final double length;
    private final double height;

    public Floor(Vector left, double length) {

        this.left = left;
        this.length = length;
        this.height = left.y;
        this.right = new Vector(left.x + length, left.y);
    }


    @Override
    public void actOn(CollisionEnvironment environment) {
        Vector previous = environment.getPreviousEcb().bottom();
        Vector projected = environment.getProjectedEcb().bottom();

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
            environment.setGrounded(true);

        }
    }

    @Override
    public Vector p1() {
        return left;
    }

    @Override
    public Vector p2() {
        return right;
    }
}
