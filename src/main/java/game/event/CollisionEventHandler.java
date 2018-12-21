package game.event;

import game.entity.DynamicEntity;
import game.entity.Entity;

import java.util.LinkedList;
import java.util.Queue;

public class CollisionEventHandler {

    private Queue<CollisionEvent> eventQueue;

    public CollisionEventHandler() {
        init();
    }

    private void init() {
        eventQueue = new LinkedList<>();
    }

    public void addEvent(CollisionEvent e) {
        eventQueue.add(e);
    }

    public void tick() {

        for (CollisionEvent e : eventQueue) {
            onCollide(e.d1, e.d2);

        }

    }


    /**
     * Deals with the particular event where Dynamic Entity dy collides with another entity e
     *
     * @param dy
     * @param e
     */
    public void onCollide(DynamicEntity dy, Entity e) {
        System.out.println(dy + " has collided with " + e);

    }
}
