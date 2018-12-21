package game.map;

import game.entity.DynamicEntity;
import game.entity.StaticEntity;

import java.util.Collection;

public class GameMap {


    private final Collection<StaticEntity> staticEntities;
    private final Collection<DynamicEntity> dynamicEntities;

    public GameMap(Collection<StaticEntity> staticEntities, Collection<DynamicEntity> dynamicEntities) {

        this.staticEntities = staticEntities;
        this.dynamicEntities = dynamicEntities;
    }
}
