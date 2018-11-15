package engine.physics.collision.boundingbox;

import engine.physics.collision.Vector;

public class AxisAlignedBoundingBox {

    private final Vector min;
    private final Vector max;

    public AxisAlignedBoundingBox(Vector min, Vector max) {

        this.min = min;
        this.max = max;
    }

    public boolean collides(AxisAlignedBoundingBox aabb) {
        boolean b1 = this.max.x < aabb.min.x;
        boolean b2 = aabb.max.x < this.min.x;
        boolean b3 = this.max.y < aabb.min.y;
        boolean b4 = aabb.max.y < this.min.y;

        return !(b1 || b2 || b3 || b4);

    }
}
