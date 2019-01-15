package game.environment;

import game.physics.collision.hitbox.HitBox;
import game.physics.collision.hitbox.HurtBox;

public class CollisionEnvironment {

    private EnvironmentCollisionBox previousEcb;
    private EnvironmentCollisionBox ecb;
    private EnvironmentCollisionBox projectedEcb;

    private HitBox hitBox;
    private HurtBox hurtBox;

    private boolean hitBoxActive = false;


    private boolean grounded;


    private double xVel;
    private double yVel;
    private double xAcc;
    private double yAcc;

    private double xAdjustment;
    private double yAdjustment;

    public CollisionEnvironment(EnvironmentCollisionBox ecb, HurtBox hurtBox) {
        this.previousEcb = ecb;
        this.ecb = ecb;
        this.projectedEcb = ecb;
        this.hurtBox = hurtBox;
    }


    public void updateProjectedPosition() {
        xVel += xAcc;
        yVel += yAcc;

        previousEcb = new EnvironmentCollisionBox(ecb.bps());


        Vector newVector = new Vector(previousEcb.bps(), xVel, yVel);


        projectedEcb = new EnvironmentCollisionBox(newVector);

    }

    public void updateEnvironment() {
        // TODO does not handle affect changes such as airborn or whatnot.
        Vector newVector = new Vector(projectedEcb.bps(), xAdjustment, yAdjustment);


        ecb = new EnvironmentCollisionBox(newVector);

        xAdjustment = 0;
        yAdjustment = 0;

        hitBox = HitBox.builder(hitBox).setOrigin(newVector).build(); // adjusts the hitbox with the bps
        hurtBox = HurtBox.builder(hurtBox).setOrigin(newVector).build();


    }


    public double xVel() {
        return xVel;
    }

    public double yVel() {
        return yVel;
    }

    public double xAcc() {
        return xAcc;
    }

    public double yAcc() {
        return yAcc;
    }

    public boolean grounded() {
        return grounded;
    }

    public double xPos() {
        return ecb.bps().x;
    }

    public double yPos() {
        return ecb.bps().y;
    }

    public void setXVel(double xVel) {
        this.xVel = xVel;
    }

    public void setYVel(double yVel) {
        this.yVel = yVel;
    }

    public void setXAcc(double xAcc) {
        this.xAcc = xAcc;
    }

    public void setYAcc(double yAcc) {
        this.yAcc = yAcc;
    }

    public void setGrounded(boolean grounded) {
        this.grounded = grounded;
    }


    public void setYAdjustment(double adjust) {
        if (adjust > this.yAdjustment) {
            this.yAdjustment = adjust;
        }
    }

    public void setXAdjustment(double adjust) {
        if (adjust > this.xAdjustment) {
            this.xAdjustment = adjust;
        }
    }

    public EnvironmentCollisionBox getEcb() {
        return ecb;
    }

    public HitBox getHitBox() {
        return hitBox;
    }

    public HurtBox getHurtBox() {
        return hurtBox;
    }

    public boolean hitBoxActive() {
        return hitBoxActive;
    }

    public EnvironmentCollisionBox getPreviousEcb() {
        return previousEcb;
    }

    public EnvironmentCollisionBox getProjectedEcb() {
        return projectedEcb;
    }


}
