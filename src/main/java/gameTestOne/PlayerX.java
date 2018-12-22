package gameTestOne;

import game.action.ControllerButton;
import game.entity.Entity;
import game.entity.Player;

import java.util.Set;

public class PlayerX implements Player {
    int xPos = 0;
    int yPos = 0;

    int xVelocity = 0;
    int yVelocity = 0;


    @Override
    public void tick() {
        xPos += xVelocity;
        yPos += yVelocity;
    }

    @Override
    public boolean collides(Entity e) {
        return false;
    }


    public void act(Set<ControllerButton> actionSet) {

        int tmpXVelocity = 0;
        int tmpYVelocity = 0;
        if (actionSet.contains(ControllerButton.UP)) {
            tmpYVelocity += 1;
        }
        if (actionSet.contains(ControllerButton.DOWN)) {
            tmpYVelocity -= 1;
        }
        if (actionSet.contains(ControllerButton.LEFT)) {
            tmpXVelocity -= 1;
        }
        if (actionSet.contains(ControllerButton.RIGHT)) {
            tmpXVelocity += 1;
        }

        xVelocity = tmpXVelocity;
        yVelocity = tmpYVelocity;

    }


    @Override
    public String toString() {
        // TODO fix later
        return "Player at: " + xPos;
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
