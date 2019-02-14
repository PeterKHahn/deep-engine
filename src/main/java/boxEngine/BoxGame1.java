package boxEngine;

import org.jbox2d.collision.shapes.PolygonShape;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.Body;
import org.jbox2d.dynamics.BodyDef;
import org.jbox2d.testbed.framework.TestbedSettings;
import org.jbox2d.testbed.framework.TestbedTest;

public class BoxGame1 extends TestbedTest {

    @Override
    public void initTest(boolean argDeserialized) {
        if (argDeserialized) {
            return;
        }

        BodyDef bd = new BodyDef();
        Body ground = getWorld().createBody(bd);

        PolygonShape shape = new PolygonShape();
        shape.setAsEdge(new Vec2(-40.0f, 0.0f), new Vec2(40.0f, 0.0f));
        ground.createFixture(shape, 0.0f);

        createBoundaries();

        shape.setAsEdge(new Vec2(-30.0f, 20.0f), new Vec2(-10.0f, 20.0f));
        ground.createFixture(shape, 0.0f);

        shape.setAsEdge(new Vec2(10.0f, 20.0f), new Vec2(30.0f, 20.0f));
        ground.createFixture(shape, 0.0f);
        Person.createPerson(getWorld());

    }

    private void createBoundaries() {
        // TODO abstract this logic a bit more out

        BodyDef bd = new BodyDef();
        Body boundaries = getWorld().createBody(bd);

        float x = -10;
        float x2 = 10;
        float y = -10;
        float y2 = 10;

        float size = 70;

        Vec2 corner1 = new Vec2(x, y);
        Vec2 corner2 = new Vec2(x, y2);
        Vec2 corner3 = new Vec2(x2, y2);
        Vec2 corner4 = new Vec2(x2, y);

        createBox(new Vec2(-size, -size), new Vec2(size, size));
/*
        PolygonShape shape = new PolygonShape();

        shape.setAsEdge(corner1, corner2);
        boundaries.createFixture(shape, 0.0f);

        shape.setAsEdge(corner2, corner3);
        boundaries.createFixture(shape, 0.0f);

        shape.setAsEdge(corner3, corner4);
        boundaries.createFixture(shape, 0.0f);

        shape.setAsEdge(corner4, corner1);
        boundaries.createFixture(shape, 0.0f);
*/

    }

    private void createBox(Vec2 corner1, Vec2 corner2) {

        BodyDef bd = new BodyDef();
        Body box = getWorld().createBody(bd);


        PolygonShape shape = new PolygonShape();

        Vec2 corner3 = new Vec2(corner1.x, corner2.y);
        Vec2 corner4 = new Vec2(corner2.x, corner1.y);

        shape.setAsEdge(corner1, corner3);
        box.createFixture(shape, 1);

        shape.setAsEdge(corner3, corner2);
        box.createFixture(shape, 1);

        shape.setAsEdge(corner2, corner4);
        box.createFixture(shape, 1);
        shape.setAsEdge(corner4, corner1);
        box.createFixture(shape, 1);


    }


    @Override
    public String getTestName() {
        return "First Box Game";
    }

    @Override
    public void step(TestbedSettings settings) {
        super.step(settings);

    }

    @Override
    public void keyPressed(char argKeyChar, int argKeyCode) {

    }
}
