package gameBuilder;

import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.Body;

public class Entity {

    private Body body;
    // TODO uncomment when ready
    // private List<Box> hurtBox;

    public Entity(Body body) {
        this.body = body;
        // TODO uncomment when ready
        // this.hurtBox = hurtBox;
    }


    public Vec2 position() {
        return body.getPosition();
    }

    public float angle() {
        return body.getAngle();
    }

    public float angularVelocity() {
        return body.m_angularVelocity;
    }

    public Vec2 linearVelocity() {
        return body.m_linearVelocity;
    }

    public void incRotationalVelocity() {
        body.m_angularVelocity += 0.1;
    }

    public void decRotationalVelocity() {
        body.m_angularVelocity -= 0.1;

    }

    public void accelerate() {
        float angle = body.getAngle();
        body.applyForce(angleToVec2(angle).mul(100000), body.getWorldCenter());

    }

    private Vec2 angleToVec2(float angle) {
        return new Vec2((float) Math.cos(angle), (float) Math.sin(angle));
    }

    public EntityState state() {

        return EntityState.builder()
                .position(position())
                .angle(angle())
                .angularVelocity(angularVelocity())
                .linearVelocity(linearVelocity())
                .build();
    }
}
