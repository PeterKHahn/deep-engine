package gameBuilder;

import org.jbox2d.common.Vec2;

public class Box {

    public final float hx;
    public final float hy;
    public final Vec2 offset;
    public final float angle;

    public Box(float hx, float hy, Vec2 offset, float angle) {

        this.hx = hx;
        this.hy = hy;
        this.offset = offset;
        this.angle = angle;
    }
}
