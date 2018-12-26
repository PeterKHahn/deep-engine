package game.map;

import game.action.PlayerController;
import game.entity.Entity;

import java.util.Collection;

public class GameMap { // TODO this isn't really a game map anymore, as much as it's an inital game


    private final Collection<PlayerController> controllers;
    private final Collection<Entity> entities;

    public GameMap(Collection<PlayerController> controllers, Collection<Entity> entities) {
        this.controllers = controllers;

        this.entities = entities;
    }


    public Collection<Entity> getEntities() {
        return entities;
    }

    public Collection<PlayerController> getControllers() {
        return controllers;
    }
}
