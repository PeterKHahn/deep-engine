package engine;

import game.Game;
import rendering.Drawing;

public class Engine {

    private boolean renderMode = true;
    private boolean running = false;
    private final int FPS = 300;

    private int ticks = 0;
    private Game game;

    Drawing d;

    public Engine(Game game) {

        this.game = game;
        d = new Drawing();
    }

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

                render();
            }

            if (System.currentTimeMillis() - lastMilis >= 1000) {
                lastMilis += 1000;
                System.out.println("FPS: " + framesThisSecond);
                framesThisSecond = 0;
            }

        }

    }

    int count = 1000;

    private void tick() {
        game.tick();
        int[][] ar = new int[count][count];
        for (int i = 0; i < count; i++) {
            for (int j = 0; j < count; j++) {
                ar[i][j] = 5;

            }
        }

    }


    private void render() {
        d.render();
        d.tick++;
    }


}
