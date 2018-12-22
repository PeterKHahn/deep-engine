package game.map;


import game.action.PlayerController;
import game.entity.DynamicEntity;
import game.entity.Player;
import game.entity.StaticEntity;

import java.util.Collection;
import java.util.LinkedList;

/**
 * Tool for Building the Initial State Maps that a game can take on.
 */
public class MapBuilder {

    Collection<DynamicEntity> dynamicEntities;
    Collection<StaticEntity> staticEntities;
    Collection<PlayerController> controllers;


    public MapBuilder() {
        init();
    }

    private void init() {
        dynamicEntities = new LinkedList<>();
        staticEntities = new LinkedList<>();
        controllers = new LinkedList<>();

    }

    public void insertStaticEntity(StaticEntity entity) {
        staticEntities.add(entity);
    }

    public void insertDynamicEntity(DynamicEntity entity) {
        dynamicEntities.add(entity);
    }

    public void insertPlayer(PlayerController controller) {
        Player entity = controller.player; // TODO will have to change once this is not public
        dynamicEntities.add(entity);
        controllers.add(controller);
    }

    public GameMap build() {
        return new GameMap(controllers, staticEntities, dynamicEntities);
    }


}
