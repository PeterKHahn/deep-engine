package game.environment;

public class CollisionEnvironment {

    private EnvironmentCollisionBox previousEcb;

    private EnvironmentCollisionBox ecb;

    private EnvironmentCollisionBox projectedEcb;


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

        Point newPoint = new Point(previousEcb.bps());
        newPoint.x += xVel;
        newPoint.y += yVel;
        EnvironmentCollisionBox newBox = new EnvironmentCollisionBox(newPoint);

        projectedEcb = newBox;

    }

    public void updateEcb() {
        // TODO does not handle affect changes such as airborn or whatnot. 
        Point newPoint = new Point(projectedEcb.bps());
        newPoint.x += xAdjustment;
        newPoint.y += yAdjustment;
        EnvironmentCollisionBox newBox = new EnvironmentCollisionBox(newPoint);
        ecb = newBox;
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

    public void setXAdjustment(double adjust) {
        if (adjust > this.xAdjustment) {
            this.xAdjustment = adjust;
        }
    }


}
