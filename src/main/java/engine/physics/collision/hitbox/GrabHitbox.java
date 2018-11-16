package engine.physics.collision.hitbox;

import java.awt.*;

public class GrabHitbox implements Hitbox {

    private static final Color COLOR = new Color(255, 0, 255);

    @Override
    public Color color() {
        return COLOR;
    }
}
