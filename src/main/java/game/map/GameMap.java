package game.map;

import game.action.PlayerController;
import game.entity.DynamicEntity;
import game.entity.StaticEntity;

import java.util.Collection;

public class GameMap { // TODO this isn't really a game map anymore, as much as it's an inital game


    private final Collection<PlayerController> controllers;
    private final Collection<StaticEntity> staticEntities;
    private final Collection<DynamicEntity> dynamicEntities;

    public GameMap(Collection<PlayerController> controllers, Collection<StaticEntity> staticEntities, Collection<DynamicEntity> dynamicEntities) {
        this.controllers = controllers;

        this.staticEntities = staticEntities;
        this.dynamicEntities = dynamicEntities;
    }

    public Collection<StaticEntity> getStaticEntities() {
        return staticEntities;
    }

    public Collection<DynamicEntity> getDynamicEntities() {
        return dynamicEntities;
    }

    public Collection<PlayerController> getControllers() {
        return controllers;
    }
}
