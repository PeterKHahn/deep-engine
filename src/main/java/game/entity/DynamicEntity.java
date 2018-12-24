package game.entity;

public abstract class DynamicEntity extends Entity {


    protected double xVel;
    protected double yVel;
    protected double xAcc;
    protected double yAcc;

    public DynamicEntity(double xPos, double yPos, double xVel, double yVel, double xAcc, double yAcc) {
        super(xPos, yPos);
        this.xVel = xVel;
        this.yVel = yVel;
        this.xAcc = xAcc;
        this.yAcc = yAcc;
    }


    public void tick() {
        updateState();
        updatePosition();
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

}
