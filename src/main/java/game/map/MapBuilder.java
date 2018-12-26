package game.map;


import game.action.PlayerController;
import game.entity.Entity;
import game.entity.Player;

import java.util.Collection;
import java.util.LinkedList;

/**
 * Tool for Building the Initial State Maps that a game can take on.
 */
public class MapBuilder {

    Collection<Entity> entities;
    Collection<PlayerController> controllers;


    public MapBuilder() {
        init();
    }

    private void init() {
        entities = new LinkedList<>();
        controllers = new LinkedList<>();

    }


    public void insertDynamicEntity(Entity entity) {
        entities.add(entity);
    }

    public void insertPlayer(PlayerController controller) {
        Player entity = controller.getPlayer(); // TODO will have to change once this is not public
        entities.add(entity);
        controllers.add(controller);
    }

    public GameMap build() {
        return new GameMap(controllers, entities);
    }


}
