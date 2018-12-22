package rendering;

import javax.swing.*;

public class JFrameExtra extends JPanel {

    // TODO move this elsewhere
    int width = 640;
    int height = 480;

    String TITLE = "Temporary rendering";


    public JFrameExtra() {
        init();
    }

    private void init() {
        JFrame frame = new JFrame(TITLE);
        frame.setContentPane(new JFrameExtra());
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();             // "this" JFrame packs its components
        frame.setLocationRelativeTo(null); // center the application window
        frame.setVisible(true);            // show it
    }

}
