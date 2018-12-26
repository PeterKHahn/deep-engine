package game.environment;

public class CollisionEnvironment {

    public final EnvironmentCollisionBox previousEcb;

    public final EnvironmentCollisionBox ecb;

    public final EnvironmentCollisionBox projectedEcb;


    private double xVel;
    private double yVel;
    private double xAcc;
    private double yAcc;

    private double xAdjustment;
    private double yAdjustment;

    public CollisionEnvironment(EnvironmentCollisionBox ecb) {
        this.previousEcb = ecb;
        this.ecb = ecb;
        this.projectedEcb = ecb;
    }


    public void updateProjectedPosition() {
        xVel += xAcc;
        yVel += yAcc;

        projectedEcb.bps().x += xVel;
        projectedEcb.bps().y += yVel;

    }

    public void updateEcb() {

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

    public void setYAdjustment(double adjust) {
        if (adjust > this.yAdjustment) {
            this.yAdjustment = adjust;
        }
    }


}
