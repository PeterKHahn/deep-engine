package game.map;

import game.entity.DynamicEntity;
import game.entity.StaticEntity;

import java.util.Collection;

public class GameMap {


    // TODO change to private, and think of a better way to do this
    public final Collection<StaticEntity> staticEntities;
    public final Collection<DynamicEntity> dynamicEntities;

    public GameMap(Collection<StaticEntity> staticEntities, Collection<DynamicEntity> dynamicEntities) {

        this.staticEntities = staticEntities;
        this.dynamicEntities = dynamicEntities;
    }
}
