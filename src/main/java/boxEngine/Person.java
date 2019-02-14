package boxEngine;

import org.jbox2d.collision.shapes.CircleShape;
import org.jbox2d.collision.shapes.PolygonShape;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.*;

public class Person {


    public Body body;

    private CircleShape shape;


    public Person(World world) {

        // Body Definition
        // Shape
        //
        initBody(world);

    }

    private void initBody(World world) {
        BodyDef bd = new BodyDef();
        bd.type = BodyType.DYNAMIC;
        bd.linearVelocity.set(new Vec2(10, 10));
        bd.position.set(0, 0);

        body = world.createBody(bd);

        PolygonShape polygon1 = new PolygonShape();

        polygon1.setAsBox(6, 8, new Vec2(8, 15), 0.7f);

        // this.shape = circle;

        FixtureDef fd = new FixtureDef();
        fd.shape = polygon1;

        fd.density = 5.0f;
        fd.friction = 0.0f;


        body.createFixture(fd);

        FixtureDef fd2 = new FixtureDef();
        fd2.density = 5.0f;
        fd2.friction = 0.0f;
        PolygonShape polygon2 = new PolygonShape();
        polygon2.setAsBox(3, 5, new Vec2(3, 5), 0.3f);

        fd2.shape = polygon2;


        body.createFixture(fd2);


    }

    private void initShape() {


    }

    public static Person createPerson(World world) {
        return new Person(world);
    }

}
