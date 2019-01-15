package rendering;

import engine.Engine;
import engine.EngineListener;
import game.DynamicGameState;
import game.Game;
import game.action.ControllerButton;
import game.action.GameController;
import game.entity.EntityState;
import game.environment.EnvironmentCollisionBox;
import game.environment.Vector;
import game.environment.environmentObject.Floor;
import game.physics.collision.hitbox.HitBox;
import game.physics.collision.hitbox.HurtBox;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.util.HashSet;
import java.util.Set;

public class JPanelRenderer extends JPanel implements EngineListener, KeyListener {
    // TODO potentilly make this not extends JPanel

    // TODO move this elsewhere
    private static final int PREFERRED_WIDTH = 640;
    private static final int PREFERRED_HEIGHT = 480;


    private static final int CENTER_HEIGHT = PREFERRED_HEIGHT / 2;
    private static final int CENTER_WIDTH = PREFERRED_WIDTH / 2;

    private static final String TITLE = "Your Game Rendered";

    private static final int NUM_CONTROLLERS = 2;


    private Game game;
    private Engine engine;

    private BufferedImage image;
    private JFrame frame;

    private GameController[] controllers;


    public JPanelRenderer(Game game, Engine engine) {
        this.game = game;
        this.engine = engine;

        init();
    }

    private void init() {

        controllers = new GameController[NUM_CONTROLLERS];
        for (int i = 0; i < NUM_CONTROLLERS; i++) {
            controllers[i] = new GameController();
        }


        frame = new JFrame(TITLE);
        frame.addKeyListener(this);

        image = new BufferedImage(PREFERRED_WIDTH, PREFERRED_HEIGHT, BufferedImage.TYPE_INT_RGB);

        setPreferredSize(new Dimension(PREFERRED_WIDTH, PREFERRED_HEIGHT));


        frame.add(this);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.pack();             // "this" JFrame packs its components
        frame.setLocationRelativeTo(null); // center the application window
        frame.setVisible(true);            // show it
    }


    public void render(DynamicGameState state) {
        BufferStrategy bs = frame.getBufferStrategy();
        if (bs == null) {
            frame.createBufferStrategy(3);
            return;
        }
        Graphics g = bs.getDrawGraphics();

        Set<EntityState> entityStates = state.getEntityStates();

        Graphics g2 = image.getGraphics();

        g2.setColor(Color.WHITE);
        g2.fillRect(0, 0, 640, 480);

        g2.setColor(Color.BLUE);


        for (EntityState entityState : entityStates) {
            EnvironmentCollisionBox ecb = entityState.getEcb();

            Color c = new Color(255, 161, 0, 255);

            renderEcb(ecb, g2, c);

            Color c2 = new Color(255, 116, 0, 255);

            drawCircle(ecb.bps().x, ecb.bps().y, 6.0, g2, Color.BLUE);
            HurtBox hurtBox = entityState.getHurtBox();
            HitBox hitBox = entityState.getHitBox();
            if (hurtBox != null) {
                Vector center = hurtBox.center();
                drawCircle(center.x, center.y, hurtBox.radius, g2, new Color(255, 254, 0, 128));
            }

            if (entityState.getHitBoxActive() && hitBox != null) {

                Vector center = hitBox.center();
                drawCircle(center.x, center.y, hitBox.radius, g2, new Color(255, 0, 0, 255));
            }


        }
        g2.setColor(Color.RED);

        g2.drawLine(CENTER_WIDTH, 0, CENTER_WIDTH, PREFERRED_HEIGHT);
        g2.drawLine(0, CENTER_HEIGHT, PREFERRED_WIDTH, CENTER_HEIGHT);


        g2.drawOval(CENTER_WIDTH, CENTER_HEIGHT, 1, 1);
        g2.drawOval(CENTER_WIDTH - 10, CENTER_HEIGHT - 10, 20, 20);


        Set<Floor> environmentObjects = game.getFloors();

        g2.setColor(Color.GREEN);
        for (Floor floor : environmentObjects) {
            g2.drawLine(convertX(floor.p1().x),
                    convertY(floor.p1().y),
                    convertX(floor.p2().x),
                    convertY(floor.p2().y));
        }

        g.drawImage(image, 0, 0, null);

        g.dispose();
        bs.show();


    }

    private void drawCircle(double x, double y, double radius, Graphics g, Color c) {
        int xInt = convertX(x);
        int yInt = convertY(y);
        int centerX = xInt - (int) (radius);
        int centerY = yInt - (int) (radius);
        g.setColor(c);
        g.fillOval(centerX, centerY, (int) (radius * 2), (int) (radius * 2));
    }


    private void renderEcb(EnvironmentCollisionBox ecb, Graphics g, Color color) {
        int[] x = new int[4];
        int[] y = new int[4];

        g.setColor(color);

        x[0] = convertX(ecb.bottom().x);
        y[0] = convertY(ecb.bottom().y);
        x[1] = convertX(ecb.left().x);
        y[1] = convertY(ecb.left().y);
        x[2] = convertX(ecb.top().x);
        y[2] = convertY(ecb.top().y);
        x[3] = convertX(ecb.right().x);
        y[3] = convertY(ecb.right().y);

        g.drawPolygon(x, y, 4);
    }

    private int convertX(double x) {
        return (int) (x + CENTER_WIDTH);
    }

    private int convertY(double y) {
        return (int) (CENTER_HEIGHT - y);
    }


    @Override
    public void onUpdate(DynamicGameState gameState) {
        render(gameState);

    }

    public GameController getController(int port) {
        return controllers[port]; // TODO check for array bounds
    }


    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        // Meta Engine Mechanics
        if (e.getKeyChar() == 'p') {
            engine.pause();
        }
        if (e.getKeyChar() == 'd') {
            engine.advance();
        }

        // Game Mechanics, Controller Port 1

        Set<ControllerButton> buttons = new HashSet<>();

        if (e.getKeyChar() == 'w') {
            buttons.add(ControllerButton.UP);
        }
        if (e.getKeyChar() == 'a') {
            buttons.add(ControllerButton.LEFT);

        }
        if (e.getKeyChar() == 's') {
            buttons.add(ControllerButton.DOWN);

        }
        if (e.getKeyChar() == 'd') {
            buttons.add(ControllerButton.RIGHT);

        }
        if (e.getKeyChar() == 'j') {
            buttons.add(ControllerButton.A);

        }
        if (e.getKeyChar() == 'k') {
            buttons.add(ControllerButton.B);

        }

        controllers[0].hold(buttons);
    }

    @Override
    public void keyReleased(KeyEvent e) {

        // Game Mechanics, Controller Port 1

        Set<ControllerButton> buttons = new HashSet<>();

        if (e.getKeyChar() == 'w') {
            buttons.add(ControllerButton.UP);
        }
        if (e.getKeyChar() == 'a') {
            buttons.add(ControllerButton.LEFT);

        }
        if (e.getKeyChar() == 's') {
            buttons.add(ControllerButton.DOWN);

        }
        if (e.getKeyChar() == 'd') {
            buttons.add(ControllerButton.RIGHT);

        }
        if (e.getKeyChar() == 'j') {
            buttons.add(ControllerButton.A);

        }
        if (e.getKeyChar() == 'k') {
            buttons.add(ControllerButton.B);

        }

        controllers[0].release(buttons);

    }
}
