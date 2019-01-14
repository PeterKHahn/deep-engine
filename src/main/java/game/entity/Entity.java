package game.entity;

import game.Game;
import game.environment.CollisionEnvironment;
import game.environment.GameEnvironment;
import game.physics.collision.hitbox.Box;

import java.util.Collection;

public abstract class Entity {


    private CollisionEnvironment environment;


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
        if (hitboxActive()) {
            for (Entity e : entities) { // handle collision across entities
                if (this != e && this.hitbox().collides(e.hurtbox())) {
                    // TODO fill
                }
            }
        }


        updateState(game);
        updateProjectedPosition();


        // handle collision across environment
        environment.actOn(this);


        updateEcb();


        tick++;


    }

    public abstract boolean hitboxActive();

    public abstract Box hitbox();

    public abstract Box hurtbox();

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

    public void updateEcb() {
        environment.updateEcb();
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
                .grounded(environment.grounded())
                .ecb(environment.getEcb())
                .build();
    }


}
