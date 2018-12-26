package game.map;


import game.action.PlayerController;
import game.environment.EnvironmentObject;
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
    Collection<EnvironmentObject> environmentObjects;


    public MapBuilder() {
        init();
    }

    private void init() {
        entities = new LinkedList<>();
        controllers = new LinkedList<>();
        environmentObjects = new LinkedList<>();

    }


    public void insertDynamicEntity(Entity entity) {
        entities.add(entity);
    }

    public void insertPlayer(PlayerController controller) {
        Player entity = controller.getPlayer(); // TODO will have to change once this is not public
        entities.add(entity);
        controllers.add(controller);
    }

    public void insertEnvironmentObject(EnvironmentObject obj) {
        environmentObjects.add(obj);
    }

    public GameMap build() {
        return new GameMap(controllers, entities, environmentObjects);
    }


}
