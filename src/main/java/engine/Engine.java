package engine;

import game.Game;
import rendering.JPanelRenderer;

import java.util.Collection;
import java.util.LinkedList;

public class Engine implements Runnable {

    private boolean running = false;
    private final int FPS = 30;

    private boolean paused;

    private int ticks = 0;
    private Game game;

    private JPanelRenderer renderer;

    private Collection<EngineListener> engineListeners;


    public Engine(Game game) {
        this.game = game;
        init();
    }

    private void init() {
        engineListeners = new LinkedList<>();

        renderer = new JPanelRenderer(game, this);
        this.addListener(renderer);

    }

    public void pause() {
        paused = !paused;
    }

    public void advance() {
        game.tick();
        updateListeners();
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

            while (delta >= 1) {
                ticks++;
                tick();


                delta -= 1.0;
            }


        }

    }


    private void tick() {
        if (!paused) {

            game.tick();
            updateListeners();


        }

    }


    public void addListener(EngineListener listener) {
        this.engineListeners.add(listener);

    }

    private void updateListeners() {
        for (EngineListener listener : engineListeners) {
            listener.onUpdate(game.getState()); // TODO replace argument
        }
    }
}
