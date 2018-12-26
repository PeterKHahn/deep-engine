package game.entity;

public abstract class Entity {

    protected double xPos;
    protected double yPos;

    protected double xVel;
    protected double yVel;
    protected double xAcc;
    protected double yAcc;


    private int tick = 0;


    public Entity(double xPos, double yPos, double xVel, double yVel, double xAcc, double yAcc) {
        this.xPos = xPos;
        this.yPos = yPos;

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

        xPos += xVel;
        yPos += yVel;
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
        return xPos;
    }

    public double yPos() {
        return yPos;
    }


}
