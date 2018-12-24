package game.entity;

import game.action.ControllerButton;

import java.util.Set;

public abstract class Player extends DynamicEntity {


    public Player(double xPos, double yPos, double xVel, double yVel, double xAcc, double yAcc) {
        super(xPos, yPos, xVel, yVel, xAcc, yAcc);
    }

    public abstract void act(Set<ControllerButton> buttons);
}
