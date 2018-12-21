package game.event;

import game.entity.DynamicEntity;
import game.entity.Entity;

public class CollisionEvent {

    public final int timestep;
    public final DynamicEntity d1;
    public final Entity d2;

    public CollisionEvent(int timestep, DynamicEntity d1, Entity d2) {


        this.timestep = timestep;
        this.d1 = d1;
        this.d2 = d2;
    }
}
