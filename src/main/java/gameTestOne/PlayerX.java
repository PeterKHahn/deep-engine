package gameTestOne;

import game.Game;
import game.action.ControllerButton;
import game.entity.Entity;
import game.entity.Player;
import game.environment.CollisionEnvironment;

import java.util.Set;

public class PlayerX extends Player {

    private static final int FIRE_COOLDOWN = 30;

    private int lastFire = -1;


    public PlayerX(CollisionEnvironment environment) {

        super(environment, 0, 0, 0, -2.0);
    }


    @Override
    public boolean collides(Entity e) {
        return false;
    }

    @Override
    public void updateState(Game game) {
        // TODO
    }


    public void act(Set<ControllerButton> actionSet) {

        int tmpXVelocity = 0;
        int tmpYVelocity = 0;

        if (actionSet.contains(ControllerButton.UP)) {
            tmpYVelocity += 4;
        }
        if (actionSet.contains(ControllerButton.DOWN)) {
            tmpYVelocity -= 0;
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
        if (getEnvironment().grounded() && tmpYVelocity > 0) {
            getEnvironment().setGrounded(false);
            // TODO fix this
        }

        getEnvironment().setXVel(tmpXVelocity);
        getEnvironment().setYVel(tmpYVelocity);


    }

    private void fire() {
        if (lastFire < 0 || currentTick() - lastFire > FIRE_COOLDOWN) {
            lastFire = currentTick();
            // TODO add fire effects
        }

    }


}
