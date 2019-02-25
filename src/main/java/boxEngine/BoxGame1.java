package boxEngine;

import connect.TrainServer;
import entity.Entity;
import game.Game;
import game.GameTransitionStatus;
import gameBuilder.PlayerBuilder;
import io.javalin.websocket.WsSession;
import org.jbox2d.callbacks.ContactImpulse;
import org.jbox2d.callbacks.ContactListener;
import org.jbox2d.collision.Manifold;
import org.jbox2d.collision.shapes.PolygonShape;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.Body;
import org.jbox2d.dynamics.BodyDef;
import org.jbox2d.dynamics.Fixture;
import org.jbox2d.dynamics.contacts.Contact;
import org.jbox2d.testbed.framework.TestbedSettings;
import org.jbox2d.testbed.framework.TestbedTest;

public class BoxGame1 extends TestbedTest {
    private Entity player1;
    private Entity player2;

    private Body boundaries;

    private Game game;

    private TrainServer trainServer;
    private WsSession session;


    public BoxGame1(WsSession session) {

        this.session = session;
    }


    @Override
    public Long getTag(Body body) {
        if (body == boundaries) {
            return -1L;
        }

        return game.getIdFromBody(body);
    }

    @Override
    public void initTest(boolean argDeserialized) {

        if (argDeserialized) {
            return;
        }


        boundaries = createBoundaries();


        player1 = new PlayerBuilder(getWorld()).createEntity();
        player2 = new PlayerBuilder(getWorld()).createEntity();

        game = new Game(player1, player2);

        getWorld().setGravity(new Vec2(0, 0));
        getWorld().setContactListener(new ContactListener() {
            @Override
            public void beginContact(Contact contact) {
                Fixture a = contact.m_fixtureA;
                Fixture b = contact.m_fixtureB;
                System.out.println(getTag(a.getBody()));
                System.out.println(getTag(b.getBody()));

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


    }

    private Body createBoundaries() {
        // TODO abstract this logic a bit more out


        float size = 70;


        return createBox(new Vec2(-size, -size), new Vec2(size, size));


    }

    private Body createBox(Vec2 corner1, Vec2 corner2) {

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


        return box;


    }


    @Override
    public String getTestName() {
        return "First Box Game";
    }

    @Override
    public void step(TestbedSettings settings) {
        super.step(settings);

        double[] rewards = game.step();
        session.send(GameTransitionStatus.status(game, rewards).toJson());


    }

    @Override
    public void keyPressed(char argKeyChar, int argKeyCode) {
        switch (argKeyChar) {
            case 'a':
                player2.incRotationalVelocity();
                break;
            case 'd':
                player2.decRotationalVelocity();
                break;

            case 'j':
                player2.accelerate();
                break;

        }
    }


    @Override
    public void keyReleased(char argKeyChar, int argKeyCode) {
        switch (argKeyChar) {
            case 'a':
                player2.incRotationalVelocity();
                break;
            case 'd':
                player2.decRotationalVelocity();
                break;

            case 'j':
                player2.accelerate();
                reset();
                break;

        }
    }
}
