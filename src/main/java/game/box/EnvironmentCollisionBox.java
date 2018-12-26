package game.box;

/**
 * A box defines a region called the ecb, that allows an entity to interact
 * with their environment
 */
public class EnvironmentCollisionBox {
    // TODO fix this so that it is less prone to bugs, reassignment needs
    // to be changed

    private static final int AIR_BOUND_BPS = 10;

    private boolean grounded;
    private int framesAirBound;

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
        this.ecbLeft = new Point(bps.x + 4, bps.y + 4);
        this.ecbRight = new Point(bps.x - 4, bps.y + 4);

    }

    public Point top() {
        return ecbTop;
    }

    public Point bottom() {
        if (!grounded && framesAirBound < AIR_BOUND_BPS) {
            return bps;
        } else {
            return ecbBottom;
        }
    }

    public Point left() {
        return ecbLeft;
    }

    public Point right() {
        return ecbRight;
    }

    public void tick() {
        framesAirBound++;
    }

    public void ground(double height) {
        grounded = true;
        bps.y = height;
        readjust();
    }

    private void readjust() {
        this.ecbBottom = new Point(bps);
        this.ecbTop = new Point(bps.x, bps.y + 8);
        this.ecbLeft = new Point(bps.x + 4, bps.y + 4);
        this.ecbRight = new Point(bps.x - 4, bps.y + 4);
    }

    public void lift() {
        grounded = false;
        framesAirBound = 0;
    }


    public Point bps() {
        return bps;
    }

}
