package game.box;

public class CollisionEnvironment {

    public final EnvironmentCollisionBox previousEcb;

    public final EnvironmentCollisionBox ecb;

    public final EnvironmentCollisionBox projectedEcb;


    private double xPos;
    private double yPos;
    private double xVel;
    private double yVel;
    private double xAcc;
    private double yAcc;

    public CollisionEnvironment(EnvironmentCollisionBox ecb) {
        this.previousEcb = ecb;
        this.ecb = ecb;
        this.projectedEcb = ecb;
    }


    public void updatePosition() {
        xVel += xAcc;
        yVel += yAcc;

        projectedEcb.bps().x += xVel;
        projectedEcb.bps().y += yVel;

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


}
