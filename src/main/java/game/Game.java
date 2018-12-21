package game;

import game.entity.DynamicEntity;
import game.entity.Entity;
import game.entity.Player;
import game.event.CollisionEventHandler;
import game.map.GameMap;

import java.util.HashSet;
import java.util.Set;

public class Game {

    private Set<Player> players;
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

        eventHandler = new CollisionEventHandler();

        GameMap initialMap = schematic.getInitialMap();
        entities.addAll(initialMap.dynamicEntities);
        dynamicEntities.addAll(initialMap.dynamicEntities);
        entities.addAll(initialMap.staticEntities);

        players.addAll(initialMap.players);

    }


    public void tick() {

        // Iterate over all players for actions
        for (Player player : players) {
            player.act();
        }

        // Iterate over dynamic entities, and see if it collides with other entities,

        handleCollision();

        // lookup in the collision matrix
    }

    private void handleCollision() {
        for (DynamicEntity dy : dynamicEntities) {

            for (Entity e : entities) {
                if (dy.collides(e)) {

                }
            }

        }
    }
}
