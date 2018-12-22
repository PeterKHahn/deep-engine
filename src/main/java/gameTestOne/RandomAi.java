package gameTestOne;

import game.entity.Entity;
import game.entity.Player;

public class RandomAi implements Player {

    int xPos = 0;
    int yPos = 0;

    int velocity = 0;


    @Override
    public void tick() {
        xPos += velocity;
    }

    @Override
    public boolean collides(Entity e) {
        return false;
    }

    @Override
    public void act() {
        int direction = (int) (3 * Math.random());
        this.velocity = direction - 1;
    }

    @Override
    public String toString() {
        // TODO fix later
        return "AI Player at: " + xPos;
    }


    @Override
    public int x() {
        return xPos;
    }

    @Override
    public int y() {
        return yPos;
    }
}
