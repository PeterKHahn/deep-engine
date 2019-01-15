package gameTestOne;

import game.Game;
import game.controller.ControllerButton;
import game.entity.Player;
import game.environment.CollisionEnvironment;
import game.environment.EnvironmentCollisionBox;
import game.environment.Vector;
import game.physics.collision.hitbox.HitBox;
import game.physics.collision.hitbox.HurtBox;

import java.util.Set;

public class PlayerX extends Player {

    private static final int FIRE_COOLDOWN = 30;

    private static final HurtBox.HurtBoxBuilder builder = HurtBox.builder()
            .setOffset(new Vector(0, 10))
            .setRadius(20);

    private static final HitBox.HitBoxBuilder leftHitBox = HitBox.builder()
            .setDamage(1)
            .setOffset(new Vector(-10, 0))
            .setRadius(20);

    private static final HitBox.HitBoxBuilder rightHitBox = HitBox.builder()
            .setDamage(1)
            .setOffset(new Vector(10, 0))
            .setRadius(20);


    private int lastFire = -1;


    public PlayerX(Vector spawn) {
        super(new CollisionEnvironment(
                        new EnvironmentCollisionBox(
                                spawn),
                        builder.setOrigin(spawn).build()),
                0, 0, 0, -2.0);


    }


    @Override
    public boolean hitboxActive() {
        return getEnvironment().hitBoxActive();
    }

    @Override
    public HitBox hitBox() {
        return getEnvironment().getHitBox();
    }

    @Override
    public HurtBox hurtBox() {
        return getEnvironment().getHurtBox();
    }

    @Override
    public void updateState(Game game) {
        // TODO lmao forgot what this is supposed to do
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
            fireLeft();
        } else if (actionSet.contains(ControllerButton.B)) {
            fireRight();
        } else {
            getEnvironment().setHitBoxActive(false);
        }
        if (getEnvironment().grounded() && tmpYVelocity > 0) {
            getEnvironment().setGrounded(false);

        }

        getEnvironment().setXVel(tmpXVelocity);
        getEnvironment().setYVel(tmpYVelocity);


    }

    private void fireLeft() {
        getEnvironment().setHitBox(leftHitBox.setOrigin(getEnvironment().getEcb().bps()).build());
        getEnvironment().setHitBoxActive(true);
    }

    private void fireRight() {
        getEnvironment().setHitBox(rightHitBox.setOrigin(getEnvironment().getEcb().bps()).build());
        getEnvironment().setHitBoxActive(true);

    }


}
