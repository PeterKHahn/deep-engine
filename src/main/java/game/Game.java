package game;

import game.action.PlayerController;
import game.entity.Entity;
import game.map.GameMap;

import java.util.HashSet;
import java.util.Set;

public class Game {

    private Set<PlayerController> controllers;
    private Set<Entity> entities;
    private GameSchematic schematic;


    public Game(GameSchematic schematic) {

        this.schematic = schematic;
        init();
    }

    private void init() {
        entities = new HashSet<>();
        controllers = new HashSet<>();


        GameMap initialMap = schematic.getInitialMap();

        entities.addAll(initialMap.getEntities());
        controllers.addAll(initialMap.getControllers());

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

        for (Entity e : entities) {
            e.tick();
        }
    }

    private void handleCollision() {
        for (Entity e : entities) {

            for (Entity e1 : entities) {
                if (e != e1 && e.collides(e1)) {

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
