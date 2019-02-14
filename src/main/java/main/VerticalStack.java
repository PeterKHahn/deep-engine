package main;

import org.jbox2d.callbacks.ContactImpulse;
import org.jbox2d.callbacks.ContactListener;
import org.jbox2d.collision.Manifold;
import org.jbox2d.collision.shapes.CircleShape;
import org.jbox2d.collision.shapes.PolygonShape;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.*;
import org.jbox2d.dynamics.contacts.Contact;
import org.jbox2d.testbed.framework.TestbedSettings;
import org.jbox2d.testbed.framework.TestbedTest;

public class VerticalStack extends TestbedTest {
    private static final long BULLET_TAG = 1;

    public static final int e_columnCount = 5;
    public static final int e_rowCount = 3;

    Body m_bullet;

    @Override
    public Long getTag(Body argBody) {
        if (argBody == m_bullet) {
            return BULLET_TAG;
        }
        return super.getTag(argBody);
    }

    @Override
    public void processBody(Body argBody, Long argTag) {
        if (argTag == BULLET_TAG) {
            m_bullet = argBody;
            return;
        }
        super.processBody(argBody, argTag);
    }

    @Override
    public boolean isSaveLoadEnabled() {
        return true;
    }

    @Override
    public void initTest(boolean deserialized) {
        if (deserialized) {
            return;
        }
        {
            BodyDef bd = new BodyDef();

            Body ground = getWorld().createBody(bd);

            PolygonShape shape = new PolygonShape();
            shape.setAsEdge(new Vec2(-40.0f, 0.0f), new Vec2(40.0f, 0.0f));
            ground.createFixture(shape, 0.0f);

            shape.setAsEdge(new Vec2(20.0f, 0.0f), new Vec2(20.0f, 20.0f));
            ground.createFixture(shape, 0.0f);
        }

        float xs[] = new float[]{0.0f, -10.0f, -5.0f, 5.0f, 10.0f};

        for (int j = 0; j < e_columnCount; ++j) {
            PolygonShape shape = new PolygonShape();
            shape.setAsBox(0.7f, 0.7f);


            FixtureDef fd = new FixtureDef();
            fd.shape = shape;
            fd.density = 10000.0f;
            fd.friction = 10000.0f;


            for (int i = 0; i < e_rowCount; ++i) {
                BodyDef bd = new BodyDef();
                bd.type = BodyType.DYNAMIC;
                int n = j * e_rowCount + i;
                float x = 0.0f;
                bd.position.set(xs[j] + x, 0.752f + 1.54f * i);
                Body body = getWorld().createBody(bd);

                body.createFixture(fd);


                // body.createFixture(stillf);
            }

        }

        BodyDef still = new BodyDef();
        still.type = BodyType.KINEMATIC;
        still.position.set(12, 12);

        still.angle = 0.3f;
        still.angularVelocity = 0.5f;

        FixtureDef stillf = new FixtureDef();
        CircleShape circle = new CircleShape();
        circle.m_radius = 5;
        stillf.shape = circle;
        stillf.isSensor = true;

        getWorld().setContactListener(new ContactListener() {
            @Override
            public void beginContact(Contact contact) {
                Fixture a = contact.m_fixtureA;
                Fixture b = contact.m_fixtureB;

                System.out.println(a + " : " + b);

            }

            @Override
            public void endContact(Contact contact) {

            }

            @Override
            public void preSolve(Contact contact, Manifold oldManifold) {

            }

            @Override
            public void postSolve(Contact contact, ContactImpulse impulse) {

            }
        });
        getWorld().createBody(still).createFixture(stillf);


        m_bullet = null;
    }


    private Vec2 angledVector(double theta, double velocity) {
        return new Vec2((float) Math.cos(theta), (float) Math.sin(theta)).mul((float) velocity);
    }

    private double theta = 0;

    private void incTheta() {
        theta += 0.1;
    }

    private void decTheta() {
        theta -= 0.1;
    }

    @Override
    public void keyPressed(char argKeyChar, int argKeyCode) {
        switch (argKeyChar) {
            case ',':
                if (m_bullet != null) {
                    getWorld().destroyBody(m_bullet);
                    m_bullet = null;
                }

            {
                CircleShape shape = new CircleShape();

                shape.m_radius = 1.0f;

                FixtureDef fd = new FixtureDef();
                fd.shape = shape;
                fd.density = 50.0f;
                fd.restitution = 0.05f;

                BodyDef bd = new BodyDef();
                bd.type = BodyType.DYNAMIC;
                bd.bullet = true;
                bd.position.set(-31.0f, 5.0f);

                m_bullet = getWorld().createBody(bd);
                m_bullet.createFixture(fd);

                // m_bullet.setLinearVelocity(new Vec2(10.0f, 0.0f));
                m_bullet.setLinearVelocity(angledVector(theta, 30.0));
            }
            break;
            case 'q':
                incTheta();
                break;
            case 'w':
                decTheta();
                break;

        }
    }

    @Override
    public void step(TestbedSettings settings) {
        super.step(settings);
        addTextLine("Press ',' to launch bullet.");
    }

    @Override
    public String getTestName() {
        return "Vertical Stack";
    }
}