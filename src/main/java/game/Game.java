package game;

import game.action.PlayerController;
import game.entity.Entity;
import game.environment.EnvironmentObject;
import game.map.GameMap;

import java.util.HashSet;
import java.util.Set;

public class Game {

    private Set<PlayerController> controllers;
    private Set<Entity> entities;
    private Set<EnvironmentObject> environmentObjects;
    private GameSchematic schematic;


    public Game(GameSchematic schematic) {

        this.schematic = schematic;
        init();
    }

    private void init() {
        entities = new HashSet<>();
        controllers = new HashSet<>();
        environmentObjects = new HashSet<>();


        GameMap initialMap = schematic.getInitialMap();

        entities.addAll(initialMap.getEntities());
        controllers.addAll(initialMap.getControllers());
        environmentObjects.addAll(initialMap.getEnvironmentObjects());

    }


    public void tick() {


        // Iterate over all players for actions
        for (PlayerController controller : controllers) {
            controller.tick(this);
            controller.administer();
        }

        // tick all entities

        for (Entity e : entities) {
            e.tick();
        }

        handleCollision();

        handleEnvironmentCollision();


    }

    private void handleCollision() {
        for (Entity e : entities) {

            for (Entity e1 : entities) {
                if (e != e1 && e.collides(e1)) {
                    // TODO Fill
                }
            }

        }
    }

    private void handleEnvironmentCollision() {
        for (Entity e : entities) {

            for (EnvironmentObject obj : environmentObjects) {
                // TODO fill
            }

        }
    }

    public void printState() {
        System.out.println("Printing Game State");
        for (Entity e : entities) {
            System.out.println(e);
        }
    }

    public Set<Entity> getEntities() {
        return entities;
    }


}
