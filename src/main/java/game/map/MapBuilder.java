package game.map;


import game.entity.DynamicEntity;
import game.entity.StaticEntity;

import java.util.Collection;
import java.util.LinkedList;

/**
 * Tool for Building the Initial State Maps that a game can take on.
 */
public class MapBuilder {

    Collection<DynamicEntity> dynamicEntities;
    Collection<StaticEntity> staticEntities;

    public MapBuilder() {
        init();
    }

    private void init() {
        dynamicEntities = new LinkedList<>();
        staticEntities = new LinkedList<>();

    }

    public void insertStaticEntity(StaticEntity entity) {
        staticEntities.add(entity);
    }

    public void insertDynamicEntity(DynamicEntity entity) {
        dynamicEntities.add(entity);
    }

    public GameMap build() {
        return new GameMap(staticEntities, dynamicEntities);
    }


}
