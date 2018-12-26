package game.box;

public class FlatFloor extends EnvironmentObject {

    private final Point left;
    private final Point right;
    private final double length;
    private final double height;

    public FlatFloor(Point left, double length) {

        this.left = left;
        this.length = length;
        this.height = left.y;
        this.right = new Point(left.x + length, left.y);
    }

    @Override
    public void actOnCollide(CollisionEnvironment environment) {
        Point prev = environment.previousEcb.bottom();
        Point projected = environment.projectedEcb.bottom();

        if (projected.x >= left.x && projected.x <= right.x
                && projected.y < height && prev.y >= height) {
            // ground the entity
            environment.ecb.ground(height);

        }


    }

    @Override
    public boolean collide(CollisionEnvironment environment) {
        Point prev = environment.previousEcb.bottom();
        Point projected = environment.projectedEcb.bottom();

        return projected.x >= left.x && projected.x <= right.x
                && projected.y < height && prev.y >= height;
    }
}
