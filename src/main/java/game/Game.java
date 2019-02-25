package game;

import entity.Entity;
import gameBuilder.controller.ControllerButton;
import org.jbox2d.dynamics.Body;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class Game {

    private final Entity player1;
    private final Entity player2;

    private Set<ControllerButton> player1Buttons;
    private Set<ControllerButton> player2Buttons;


    private Map<Body, Long> bodyMap;
    private Map<Long, Entity> entityMap;

    private boolean ongoing = true;


    public Game(Entity player1, Entity player2) {
        this.player1 = player1;
        this.player2 = player2;
        init();
    }

    private void init() {
        bodyMap = new HashMap<>();
        entityMap = new HashMap<>();

        player1Buttons = new HashSet<>();
        player2Buttons = new HashSet<>();

        addEntity(player1);
        addEntity(player2);
    }

    public void finish() {
        ongoing = false;
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

    public boolean ongoing() {
        return ongoing;
    }

    /**
     * This function steps the game in a particular direction, taking in the
     * actions of the players and outputing a reward vector, where each index represents
     * a given player
     *
     * @return
     */
    public double[] step() {
        // TODO implement
        return new double[2];
    }
}

