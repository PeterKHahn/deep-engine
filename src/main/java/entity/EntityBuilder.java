package entity;

import gameBuilder.Box;
import org.jbox2d.collision.shapes.PolygonShape;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.*;

import java.util.List;

public abstract class EntityBuilder {

    private World world;

    private static long COUNT = 0;


    public EntityBuilder(World world) {
        this.world = world;

    }


    public abstract float density();

    public abstract float friction();

    public abstract BodyType bodyType();

    public abstract Vec2 initialPosition();

    public abstract List<Box> boxes();


    private FixtureDef addBox(Box box) {
        PolygonShape polygon = new PolygonShape();
        polygon.setAsBox(box.hx, box.hy, box.offset, box.angle);
        FixtureDef fd = new FixtureDef();
        fd.shape = polygon;
        fd.density = density();
        fd.friction = friction();

        return fd;


    }

    public Body createBody() {
        BodyDef bd = new BodyDef();
        bd.type = bodyType();
        bd.position = initialPosition();

        Body body = world.createBody(bd);

        for (Box box : boxes()) {
            body.createFixture(addBox(box));
        }



        return body;

    }

    public Entity createEntity() {
        return new Entity(COUNT++, createBody());
    }


}
