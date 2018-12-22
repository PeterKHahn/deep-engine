package rendering;

import javax.swing.*;
import java.awt.*;

public class Drawing extends JPanel {

    public int tick = 0;

    // TODO move this elsewhere
    int width = 640;
    int height = 480;

    String TITLE = "Temporary rendering";


    public Drawing() {
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


    public void render() {

        repaint();
    }

    public void paint(Graphics g) {
        super.paint(g);
        g.setColor(Color.BLACK);
        g.drawRect(0, 0, 640, 480);
        g.setColor(Color.BLUE);
        g.drawOval(100, 100 + tick % 200, 200, 200);

    }


}
