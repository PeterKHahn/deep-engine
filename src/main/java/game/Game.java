package game;

import game.action.PlayerController;
import game.entity.DynamicEntity;
import game.entity.Entity;
import game.event.CollisionEventHandler;
import game.map.GameMap;

import java.util.HashSet;
import java.util.Set;

public class Game {

    private Set<PlayerController> controllers;
    private Set<Entity> entities;
    private Set<DynamicEntity> dynamicEntities;
    private GameSchematic schematic;

    private CollisionEventHandler eventHandler;

    public Game(GameSchematic schematic) {

        this.schematic = schematic;
        init();
    }

    private void init() {
        entities = new HashSet<>();
        dynamicEntities = new HashSet<>();
        controllers = new HashSet<>();

        eventHandler = new CollisionEventHandler();

        GameMap initialMap = schematic.getInitialMap();
        entities.addAll(initialMap.dynamicEntities);
        dynamicEntities.addAll(initialMap.dynamicEntities);
        entities.addAll(initialMap.staticEntities);

        controllers.addAll(initialMap.controllers);

    }


    public void tick() {

        // Iterate over all players for actions
        for (PlayerController controller : controllers) {
            controller.tick(this);
            controller.administer();
        }

        // Iterate over dynamic entities, and see if it collides with other entities,

        handleCollision();

        // lookup in the collision matrix

        // tick all dynamic entities

        for (DynamicEntity e : dynamicEntities) {
            e.tick();
        }
    }

    private void handleCollision() {
        for (DynamicEntity dy : dynamicEntities) {

            for (Entity e : entities) {
                if (dy.collides(e)) {

                }
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
