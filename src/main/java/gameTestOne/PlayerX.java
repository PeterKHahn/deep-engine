package gameTestOne;

import game.action.ControllerButton;
import game.box.CollisionEnvironment;
import game.box.EnvironmentCollisionBox;
import game.box.Point;
import game.entity.Entity;
import game.entity.Player;

import java.util.Set;

public class PlayerX extends Player {

    private static final int FIRE_COOLDOWN = 30;

    private int lastFire = -1;


    public PlayerX() {
        super(new CollisionEnvironment(new EnvironmentCollisionBox(new Point(0, 0))),
                0, 0, 0, 0);
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
        if (actionSet.contains(ControllerButton.A)) {
            fire();
        }

        this.xVel = tmpXVelocity;
        this.yVel = tmpYVelocity;

    }

    private void fire() {
        if (lastFire < 0 || currentTick() - lastFire > FIRE_COOLDOWN) {
            lastFire = currentTick();
            // TODO add fire effects
        }

    }


    @Override
    public String toString() {
        // TODO fix later
        return "PlayerX";
    }

}
