package engine;

import game.Game;
import rendering.JPanelRenderer;

public class Engine implements Runnable {

    private boolean renderMode;
    private boolean running = false;
    private final int FPS = 30;

    private boolean paused;

    private int ticks = 0;
    private Game game;

    private JPanelRenderer renderer;


    public Engine(Game game) {
        this.game = game;
        this.renderMode = true;
        renderer = new JPanelRenderer(game, this);
    }

    public void pause() {
        paused = !paused;
    }

    public void advance() {
        game.tick();
    }


    @Override
    public void run() {
        running = true;
        long lastTime = System.nanoTime();

        long lastMilis = System.currentTimeMillis();

        double nsPerTick = 1000000000.0 / FPS;
        double delta = 0;

        int framesThisSecond = 0;

        while (running) {
            long now = System.nanoTime();
            delta += (now - lastTime) / nsPerTick;
            lastTime = now;

            boolean shouldRender = false;
            while (delta >= 1) {
                ticks++;
                tick();


                delta -= 1.0;
                shouldRender = true;
            }

            if (renderMode && shouldRender) {
                framesThisSecond++;

                renderer.render();
            }

            if (System.currentTimeMillis() - lastMilis >= 1000) {
                lastMilis += 1000;
                // System.out.println("FPS: " + framesThisSecond);
                framesThisSecond = 0;
            }

        }

    }


    private void tick() {
        if (!paused) {

            game.tick();


        }


    }
}
