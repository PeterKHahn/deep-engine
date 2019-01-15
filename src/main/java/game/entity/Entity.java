package game.entity;

import game.Game;
import game.environment.CollisionEnvironment;
import game.environment.GameEnvironment;
import game.physics.collision.hitbox.HitBox;
import game.physics.collision.hitbox.HurtBox;

import java.util.Collection;

public abstract class Entity {


    private CollisionEnvironment environment;
    private double damage = 0;


    private int tick = 0;


    public Entity(CollisionEnvironment initialEnvironment, double xVel, double yVel,
                  double xAcc, double yAcc) {

        this.environment = initialEnvironment;

        environment.setXVel(xVel);
        environment.setYVel(yVel);
        environment.setXAcc(xAcc);
        environment.setYAcc(yAcc);
    }


    public void tick(Game game) {

        Collection<Entity> entities = game.getEntities();
        GameEnvironment environment = game.getEnvironment();

        if (hitboxActive()) {// handle hitbox to hurtbox collision across entities
            for (Entity e : entities) {
                if (this != e && this.hitBox().collides(e.hurtBox())) {
                    // TODO fill
                    e.damage += this.hitBox().damage;
                }
            }
        }


        updateState(game);
        updateProjectedPosition();


        // handle collision across environment
        environment.actOn(this);


        updateEnvironment();


        tick++;


    }

    public abstract boolean hitboxActive();

    public abstract HitBox hitBox();

    public abstract HurtBox hurtBox();

    public int currentTick() {
        return tick;
    }


    /**
     * Can update the self state or the state of the game using this method
     *
     * @param game
     */
    public abstract void updateState(Game game);

    public void updateProjectedPosition() {
        environment.updateProjectedPosition();

    }

    public void updateEnvironment() {
        environment.updateEnvironment();
    }


    public double xPos() {
        return environment.xPos();
    }

    public double yPos() {
        return environment.yPos();
    }

    public double xVel() {
        return environment.xVel();
    }

    public double yVel() {
        return environment.yVel();
    }

    public double xAcc() {
        return environment.xAcc();
    }

    public double yAcc() {
        return environment.yAcc();
    }

    public CollisionEnvironment getEnvironment() {
        return environment;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Position: " + xPos() + " " + yPos());
        sb.append("\n");
        sb.append("Velocity: " + xVel() + " " + yVel());
        sb.append("\n");

        sb.append("Acceleration: " + xAcc() + " " + yAcc());
        sb.append("\n");

        return sb.toString();
    }

    public EntityState getState() {
        return EntityState.builder()
                .tick(tick)
                .damage(damage)
                .grounded(environment.grounded())
                .ecb(environment.getEcb())
                .build();
    }


}
