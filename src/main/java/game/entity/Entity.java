package game.entity;

import game.environment.CollisionEnvironment;

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


    public void tick() {
        updateState();
        updateProjectedPosition();
        environment.updateEcb();
        tick++;
    }

    public int currentTick() {
        return tick;
    }

    public abstract boolean collides(Entity e);

    public abstract void updateState();

    public void updateProjectedPosition() {
        environment.updateProjectedPosition();

    }

    public void updateEcb() {
        environment.updateEcb();
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


    public double xPos() {
        return environment.xPos();
    }

    public double yPos() {
        return environment.yPos();
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


}
