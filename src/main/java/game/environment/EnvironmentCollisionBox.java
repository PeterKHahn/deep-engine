package game.environment;

/**
 * A box defines a region called the ecb, that allows an entity to interact
 * with their environment
 */
public class EnvironmentCollisionBox {
    // TODO fix this so that it is less prone to bugs, reassignment needs
    // to be changed


    // Also known as top-n or base point star
    private Point bps;


    private Point ecbTop;
    private Point ecbBottom;
    private Point ecbRight;
    private Point ecbLeft;


    public EnvironmentCollisionBox(Point bps) {
        // TODO fix from default
        this.bps = bps;
        this.ecbBottom = new Point(bps);
        this.ecbTop = new Point(bps.x, bps.y + 8);
        this.ecbLeft = new Point(bps.x - 4, bps.y + 4);
        this.ecbRight = new Point(bps.x + 4, bps.y + 4);

    }

    public Point top() {
        return ecbTop;
    }

    public Point bottom() {
        return ecbBottom;
    }

    public Point left() {
        return ecbLeft;
    }

    public Point right() {
        return ecbRight;
    }


    public Point bps() {
        return bps;
    }

    public EnvironmentCollisionBox copyWith(double xVel, double yVel) {
        // TODO Fxi this as well
        Point newPoint = new Point(bps.x + xVel, bps.y + yVel);
        EnvironmentCollisionBox newBox = new EnvironmentCollisionBox(newPoint);
        return newBox;
    }


    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Top: ");
        sb.append(ecbTop + "\n");
        sb.append("Bottom: " + ecbBottom + "\n");
        sb.append("Left: " + ecbLeft + "\n");
        sb.append("Right: " + ecbRight + "\n");
        return sb.toString();
    }


}
