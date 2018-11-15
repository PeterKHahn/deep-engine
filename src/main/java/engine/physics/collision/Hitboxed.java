package engine.physics.collision;

import java.util.List;

public interface Hitboxed {

    List<Hitbox> hitboxes();

    default boolean collides(Hitboxed opp) {
        for (Hitbox hitbox : hitboxes()) {
            for (Hitbox hitboxOpp : opp.hitboxes()) {
                if (hitbox.collides(hitboxOpp)) {
                    return true;
                }
            }
        }
        return false;
    }



}
