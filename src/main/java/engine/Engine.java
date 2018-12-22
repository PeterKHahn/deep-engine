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
            while (delta >= 1) {
                ticks++;
                tick();
                if (renderMode) {
                    render();
                }
                framesThisSecond++;

                delta -= 1.0;
            }

            long nowMilis = System.currentTimeMillis();
            long diff = nowMilis - lastMilis;
            if (diff > 1000) {
                lastMilis = nowMilis;
                System.out.println("FPS: " + framesThisSecond);
                framesThisSecond = 0;
            }

        }

    }

    private void tick() {
        game.tick();
        int[][] ar = new int[10000][10000];
        for (int i = 0; i < 10000; i++) {
            for (int j = 0; j < 10000; j++) {
                ar[i][j] = 5;

            }
        }
    }


    private void render() {
        d.render();
        d.tick++;
    }


}
