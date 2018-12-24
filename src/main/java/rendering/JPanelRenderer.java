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
            g.drawOval((int) (e.xPos() + 300), (int) (e.yPos() + 100), 5, 5);
        }


    }


}
