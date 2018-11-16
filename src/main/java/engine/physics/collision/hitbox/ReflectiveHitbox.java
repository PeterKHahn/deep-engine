package engine.physics.collision.hitbox;

import java.awt.*;

public class ReflectiveHitbox implements Hitbox {

    private static final Color COLOR = new Color(36, 255, 176);

    @Override
    public Color color() {
        return COLOR;
    }
}
