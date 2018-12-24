package gameTestOne;

import game.action.ControllerButton;
import game.entity.Entity;
import game.entity.Player;

import java.util.Set;

public class PlayerX extends Player {


    public PlayerX() {
        super(0, 0, 0, 0, 0, 0);
    }


    @Override
    public boolean collides(Entity e) {
        return false;
    }

    @Override
    public void updateState() {
        // TODO
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

        this.xVel = tmpXVelocity;
        this.yVel = tmpYVelocity;

    }


    @Override
    public String toString() {
        // TODO fix later
        return "Player at: " + xPos;
    }

}
