package game.environment;

/**
 * A box defines a region called the ecb, that allows an entity to interact
 * with their environment. This class is immutable, and is access only
 */
public class EnvironmentCollisionBox {


    // Also known as top-n or base point star
    private final Point bps;


    private final Point ecbTop;
    private final Point ecbBottom;
    private final Point ecbRight;
    private final Point ecbLeft;


    public EnvironmentCollisionBox(Point bps) {
        // TODO fix from default
        this.bps = bps;
        this.ecbBottom = new Point(bps);
        this.ecbTop = new Point(bps.x, bps.y + 32);
        this.ecbLeft = new Point(bps.x - 16, bps.y + 16);
        this.ecbRight = new Point(bps.x + 16, bps.y + 16);

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
