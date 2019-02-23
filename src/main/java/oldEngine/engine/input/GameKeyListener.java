package oldEngine.engine.input;

import oldEngine.engine.Engine;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class GameKeyListener implements KeyListener {

    private Engine engine;

    public GameKeyListener(Engine engine) {
        this.engine = engine;
    }

    @Override
    public void keyTyped(KeyEvent e) {
        if (e.getKeyChar() == 'p') {
            engine.pause();
        }
        if (e.getKeyChar() == 'd') {
            engine.advance();
        }

    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyChar() == 'w') {

        }
        if (e.getKeyChar() == 'a') {

        }
        if (e.getKeyChar() == 's') {

        }
        if (e.getKeyChar() == 'd') {

        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        if (e.getKeyChar() == 'a') {

        }
        if (e.getKeyChar() == 'd') {

        }
    }
}
