package game.entity;

import game.action.ControllerButton;
import game.box.CollisionEnvironment;

import java.util.Set;

public abstract class Player extends Entity {


    public Player(CollisionEnvironment environment, double xVel, double yVel, double xAcc, double yAcc) {
        super(environment, xVel, yVel, xAcc, yAcc);
    }

    public abstract void act(Set<ControllerButton> buttons);
}
