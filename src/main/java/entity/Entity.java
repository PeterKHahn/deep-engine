package entity;

import oldEngine.game.controller.ControllerButton;
import oldEngine.game.controller.GameController;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.Body;

import java.util.Set;

public class Entity {

    private long entityId;
    private Body body;
    // TODO uncomment when ready
    // private List<Box> hurtBox;

    public Entity(long entityId, Body body) {
        this.entityId = entityId;
        this.body = body;
        // TODO uncomment when ready
        // this.hurtBox = hurtBox;
    }

    public Body getBody() {
        return body;
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
        Vec2 unit = angleToVec2(angle);
        body.m_linearVelocity.set(body.m_linearVelocity.add(unit));
        // body.applyForce(angleToVec2(angle).mul(100000), body.getWorldCenter());

    }

    public long getEntityId() {
        return entityId;
    }

    private Vec2 angleToVec2(float angle) {
        return new Vec2((float) Math.cos(angle), (float) Math.sin(angle));
    }


    public void inputController(GameController controller) {

        Set<ControllerButton> buttons = controller.getHeld();
        if (buttons.contains(ControllerButton.LEFT)) {
            incRotationalVelocity();
        }
        if (buttons.contains(ControllerButton.RIGHT)) {
            decRotationalVelocity();
        }
        if (buttons.contains(ControllerButton.A)) {
            accelerate();
        }
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
