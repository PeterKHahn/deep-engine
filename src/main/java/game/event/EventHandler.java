package game.event;

import game.entity.DynamicEntity;
import game.entity.Entity;

import java.util.LinkedList;
import java.util.Queue;

public class EventHandler {

    private Queue<Event> eventQueue;

    public EventHandler() {
        init();
    }

    private void init() {
        eventQueue = new LinkedList<>();
    }

    public void tick() {
        
    }


    /**
     * Deals with the particular event where Dynamic Entity dy collides with another entity e
     *
     * @param dy
     * @param e
     */
    public void onCollide(DynamicEntity dy, Entity e) {

    }
}
