package game.physics.math;

import game.environment.Point;
import org.junit.Test;

import static game.physics.math.Geom2D.distance;
import static game.physics.math.Geom2D.distanceSquared;
import static org.junit.Assert.assertEquals;

public class Geom2DTest {

    @Test
    public void testDistance() {

        Point p1 = new Point(3, 4);
        Point p2 = new Point(0, 0);

        Point p3 = new Point(-3, -4);

        assertEquals(distance(p1, p2), 5, 0.001);
        assertEquals(distanceSquared(p1, p2), 25, 0.0001);
        assertEquals(distance(p2, p3), 5, 0.0001);
        assertEquals(distanceSquared(p2, p3), 25, 0.0001);


        assertEquals(distance(p1, p2), distance(p2, p1), 0.0001);
        assertEquals(distance(p1, p3), distance(p3, p1), 0.0001);
        assertEquals(distance(p2, p3), distance(p3, p2), 0.0001);

        assertEquals(distanceSquared(p1, p2), distanceSquared(p2, p1), 0.0001);
        assertEquals(distanceSquared(p1, p3), distanceSquared(p3, p1), 0.0001);
        assertEquals(distanceSquared(p2, p3), distanceSquared(p3, p2), 0.00001);
    }

}
