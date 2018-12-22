package game.map;

import game.action.PlayerController;
import game.entity.DynamicEntity;
import game.entity.StaticEntity;

import java.util.Collection;

public class GameMap { // TODO this isn't really a game map anymore, as much as it's an inital game


    public final Collection<PlayerController> controllers;
    // TODO change to private, and think of a better way to do this
    public final Collection<StaticEntity> staticEntities;
    public final Collection<DynamicEntity> dynamicEntities;

    public GameMap(Collection<PlayerController> controllers, Collection<StaticEntity> staticEntities, Collection<DynamicEntity> dynamicEntities) {
        this.controllers = controllers;

        this.staticEntities = staticEntities;
        this.dynamicEntities = dynamicEntities;
    }
}
