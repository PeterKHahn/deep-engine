package engine.physics.collision;

import static engine.physics.math.Geom2D.distanceSquared;

public class SingularHitbox implements Hitbox {


    private final Point center;
    private final double radius;

    public SingularHitbox(Point center, double radius) {

        this.center = center;
        this.radius = radius;

    }

    @Override
    public boolean collides(SingularHitbox hitbox) {
        double radiusSum = (this.radius + hitbox.radius);
        double radiusSumSquared = radiusSum * radiusSum;
        return distanceSquared(this.center, hitbox.center) < radiusSumSquared;
    }


    @Override
    public boolean collides(ExtendedHitbox hitbox) {
        return false;

    }
}
