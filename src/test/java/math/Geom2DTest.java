package math;

import game.environment.Vector;
import org.junit.Test;

import static math.Geom2D.distance;
import static math.Geom2D.distanceSquared;
import static org.junit.Assert.assertEquals;

public class Geom2DTest {

    @Test
    public void testDistance() {

        Vector p1 = new Vector(3, 4);
        Vector p2 = new Vector(0, 0);

        Vector p3 = new Vector(-3, -4);

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
