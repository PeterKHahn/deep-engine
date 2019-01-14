package game.environment;

/**
 * A box defines a region called the ecb, that allows an entity to interact
 * with their environment. This class is immutable, and is access only
 */
public class EnvironmentCollisionBox {


    // Also known as top-n or base point star
    private final Vector bps;


    private final Vector ecbTop;
    private final Vector ecbBottom;
    private final Vector ecbRight;
    private final Vector ecbLeft;


    public EnvironmentCollisionBox(Vector bps) {
        // TODO fix from default
        this.bps = bps;
        this.ecbBottom = new Vector(bps);
        this.ecbTop = new Vector(bps.x, bps.y + 32);
        this.ecbLeft = new Vector(bps.x - 16, bps.y + 16);
        this.ecbRight = new Vector(bps.x + 16, bps.y + 16);

    }

    public Vector top() {
        return ecbTop;
    }

    public Vector bottom() {
        return ecbBottom;
    }

    public Vector left() {
        return ecbLeft;
    }

    public Vector right() {
        return ecbRight;
    }


    public Vector bps() {
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
