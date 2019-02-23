package game;

import com.google.common.collect.HashMultimap;
import entity.Entity;
import gameBuilder.controller.ControllerButton;
import org.jbox2d.dynamics.Body;

import java.util.HashMap;
import java.util.Map;

public class Game {

    private final Entity player1;
    private final Entity player2;

    private Map<Body, Long> bodyMap;
    private Map<Long, Entity> entityMap;

    private HashMultimap<Entity, ControllerButton> buttonsMap;

    public Game(Entity player1, Entity player2) {
        this.player1 = player1;
        this.player2 = player2;
        init();
    }

    private void init() {
        bodyMap = new HashMap<>();
        entityMap = new HashMap<>();

        buttonsMap = HashMultimap.create();

        addEntity(player1);
        addEntity(player2);
    }

    public void addEntity(Entity e) {
        bodyMap.put(e.getBody(), e.getEntityId());
        entityMap.put(e.getEntityId(), e);
    }


    public Entity getEntityFromId(long id) {
        return entityMap.get(id);
    }

    public Entity getEntityFromBody(Body body) {
        return entityMap.get(bodyMap.get(body));
    }

    public long getIdFromBody(Body body) {
        return bodyMap.get(body);
    }

    public Entity getPlayer1() {
        return player1;
    }

    public Entity getPlayer2() {
        return player2;
    }
}
