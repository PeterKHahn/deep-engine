package game.map;

import game.action.PlayerController;
import game.environment.EnvironmentObject;
import game.entity.Entity;

import java.util.Collection;

public class GameMap { // TODO this isn't really a game map anymore, as much as it's an inital game


    private final Collection<PlayerController> controllers;
    private final Collection<Entity> entities;
    private final Collection<EnvironmentObject> environmentObjects;

    public GameMap(Collection<PlayerController> controllers, Collection<Entity> entities,
                   Collection<EnvironmentObject> environmentObjects) {
        this.controllers = controllers;

        this.entities = entities;
        this.environmentObjects = environmentObjects;
    }


    public Collection<Entity> getEntities() {
        return entities;
    }

    public Collection<PlayerController> getControllers() {
        return controllers;
    }

    public Collection<EnvironmentObject> getEnvironmentObjects() {
        return environmentObjects;
    }
}
