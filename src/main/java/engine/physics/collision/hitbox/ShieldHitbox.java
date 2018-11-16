package engine.physics.collision.hitbox;

import java.awt.*;

public class ShieldHitbox implements Hitbox {

    private static final Color COLOR = new Color(0, 255, 255);

    @Override
    public Color color() {
        return COLOR;
    }
}
