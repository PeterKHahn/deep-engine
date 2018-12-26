package engine.input;

import engine.Engine;

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
        System.out.println(e.getKeyChar() + " CJ");
    }

    @Override
    public void keyReleased(KeyEvent e) {

    }
}
