package rendering;

import game.Game;
import game.entity.Entity;

import javax.swing.*;
import java.awt.*;
import java.util.Set;

public class JPanelRenderer extends JPanel implements Renderer {

    // TODO move this elsewhere
    private static final int PREFERRED_WIDTH = 640;
    private static final int PREFERRED_HEIGHT = 480;

    private static final int CENTER_HEIGHT = PREFERRED_HEIGHT / 2;
    private static final int CENTER_WIDTH = PREFERRED_WIDTH / 2;

    private static final String TITLE = "Temporary rendering";

    private Game game;


    public JPanelRenderer(Game game) {
        this.game = game;
        init();
    }

    private void init() {
        JFrame frame = new JFrame(TITLE);

        setPreferredSize(new Dimension(PREFERRED_WIDTH, PREFERRED_HEIGHT));


        frame.add(this);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();             // "this" JFrame packs its components
        frame.setLocationRelativeTo(null); // center the application window
        frame.setVisible(true);            // show it
    }


    @Override
    public void render() {
        repaint();
    }


    @Override
    public void paint(Graphics g) {
        super.paint(g);
        Set<Entity> entities = game.getEntities();


        g.setColor(Color.BLACK);
        g.drawRect(0, 0, 640, 480);

        g.setColor(Color.BLUE);


        for (Entity e : entities) {
            int x = (int) (e.xPos() + CENTER_WIDTH);
            int y = (int) (CENTER_HEIGHT - e.yPos());
            int radius = 6;


            int centerX = x - (radius / 2);
            int centerY = y - (radius / 2);


            g.drawOval(centerX, centerY, radius, radius);


        }
        g.setColor(Color.RED);

        g.drawLine(CENTER_WIDTH, 0, CENTER_WIDTH, PREFERRED_HEIGHT);
        g.drawLine(0, CENTER_HEIGHT, PREFERRED_WIDTH, CENTER_HEIGHT);


        g.drawOval(CENTER_WIDTH, CENTER_HEIGHT, 1, 1);
        g.drawOval(CENTER_WIDTH - 20, CENTER_HEIGHT - 20, 20, 20);
        g.drawOval(CENTER_WIDTH - 10, CENTER_HEIGHT - 10, 20, 20);


    }


}
