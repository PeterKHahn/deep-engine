package game.entity;

import game.box.CollisionEnvironment;

public abstract class Entity {


    protected double xVel;
    protected double yVel;
    protected double xAcc;
    protected double yAcc;


    private CollisionEnvironment environment;


    private int tick = 0;


    public Entity(CollisionEnvironment initialEnvironment, double xVel, double yVel,
                  double xAcc, double yAcc) {

        this.environment = initialEnvironment;

        this.xVel = xVel;
        this.yVel = yVel;
        this.xAcc = xAcc;
        this.yAcc = yAcc;
    }


    public void tick() {
        updateState();
        updatePosition();
        tick++;
    }

    public int currentTick() {
        return tick;
    }

    public abstract boolean collides(Entity e);

    public abstract void updateState();

    public void updatePosition() {
        xVel += xAcc;
        yVel += yAcc;

        environment.projectedEcb.bps().x += xVel;
        environment.projectedEcb.bps().y += yVel;

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


    public double xPos() {
        return environment.ecb.bottom().x;
    }

    public double yPos() {
        return environment.ecb.bottom().y;
    }


}
