package game.entity;

public abstract class Entity {

    protected double xPos;
    protected double yPos;

    public Entity(double xPos, double yPos) {
        this.xPos = xPos;
        this.yPos = yPos;
    }


    public double xPos() {
        return xPos;
    }

    public double yPos() {
        return yPos;
    }


}
