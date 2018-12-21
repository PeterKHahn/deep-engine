package engine;

import game.Game;

public class Engine {

    private boolean running = false;
    private final int FPS = 30;

    private int ticks = 0;
    private Game game;

    public Engine(Game game) {

        this.game = game;
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
                framesThisSecond++;

                delta -= 1.0;
            }

            long nowMilis = System.currentTimeMillis();
            long diff = nowMilis - lastMilis;
            if (diff > 1000) {
                lastMilis = nowMilis;
                System.out.println(framesThisSecond);
                framesThisSecond = 0;
            }

        }

    }

    private void tick() {
        game.tick();
        game.printState();
    }


}
