package rendering;

import game.Game;
import game.entity.Entity;

import javax.swing.*;
import java.awt.*;
import java.util.Set;

public class Drawing extends JPanel implements Renderer {

    public int tick = 0;

    // TODO move this elsewhere
    int width = 640;
    int height = 480;

    String TITLE = "Temporary rendering";
    private Game game;


    public Drawing(Game game) {
        this.game = game;
        init();
    }

    private void init() {
        JFrame frame = new JFrame(TITLE);

        setPreferredSize(new Dimension(width, height));


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


    public void paint(Graphics g) {
        super.paint(g);
        Set<Entity> entities = game.getEntities();


        g.setColor(Color.BLACK);
        g.drawRect(0, 0, 640, 480);

        g.setColor(Color.BLUE);


        for (Entity e : entities) {
            g.drawOval(e.x() + 300, e.y() + 100, 5, 5);
        }


    }


}
