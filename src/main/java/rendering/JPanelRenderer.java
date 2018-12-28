package rendering;

import engine.Engine;
import engine.EngineListener;
import engine.input.GameKeyListener;
import game.DynamicGameState;
import game.Game;
import game.entity.Entity;
import game.entity.EntityState;
import game.environment.CollisionEnvironment;
import game.environment.EnvironmentCollisionBox;
import game.environment.Floor;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.util.Set;

public class JPanelRenderer extends JPanel implements Renderer, EngineListener {


    // TODO move this elsewhere
    private static final int PREFERRED_WIDTH = 640;
    private static final int PREFERRED_HEIGHT = 480;


    private static final int CENTER_HEIGHT = PREFERRED_HEIGHT / 2;
    private static final int CENTER_WIDTH = PREFERRED_WIDTH / 2;

    private static final String TITLE = "Your Game Rendered";

    private Game game;
    private Engine engine;

    private BufferedImage image;
    private JFrame frame;


    public JPanelRenderer(Game game, Engine engine) {
        this.game = game;
        this.engine = engine;

        init();
    }

    private void init() {

        frame = new JFrame(TITLE);
        frame.addKeyListener(new GameKeyListener(engine));

        image = new BufferedImage(PREFERRED_WIDTH, PREFERRED_HEIGHT, BufferedImage.TYPE_INT_RGB);

        setPreferredSize(new Dimension(PREFERRED_WIDTH, PREFERRED_HEIGHT));


        frame.add(this);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.pack();             // "this" JFrame packs its components
        frame.setLocationRelativeTo(null); // center the application window
        frame.setVisible(true);            // show it
    }


    @Override
    public void render() {
        repaint();
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


            int x = convertX(ecb.bps().x);
            int y = convertY(ecb.bps().y);
            int radius = 6;


            int centerX = x - (radius / 2);
            int centerY = y - (radius / 2);

            g2.setColor(Color.BLUE);
            g2.drawOval(centerX, centerY, radius, radius);


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


    @Override
    public void paint(Graphics g) {
        super.paint(g);
        Set<Entity> entities = game.getEntities();

        Graphics g2 = image.getGraphics();

        g2.setColor(Color.WHITE);
        g2.fillRect(0, 0, 640, 480);

        g2.setColor(Color.BLUE);


        for (Entity e : entities) {
            CollisionEnvironment environment = e.getEnvironment();
            EnvironmentCollisionBox ecb = environment.getEcb();

            Color c = new Color(255, 161, 0, 255);

            renderEcb(ecb, g2, c);

            Color c2 = new Color(255, 116, 0, 255);

            renderEcb(environment.getPreviousEcb(), g2, c2);
            // renderEcb(environment.getProjectedEcb(), g2, Color.WHITE);

            int x = convertX(e.xPos());
            int y = convertY(e.yPos());
            int radius = 6;


            int centerX = x - (radius / 2);
            int centerY = y - (radius / 2);

            g2.setColor(Color.BLUE);
            g2.drawOval(centerX, centerY, radius, radius);


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
}
