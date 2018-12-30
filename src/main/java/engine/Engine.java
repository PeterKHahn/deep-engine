package engine;

import game.Game;
import game.action.GameController;

import java.util.HashSet;
import java.util.Set;

public class Engine implements Runnable {

    private boolean running = false;
    private final int FPS = 30;

    private boolean paused;

    private int ticks = 0;
    private Game game;


    private Set<EngineListener> engineListeners;

    private ControllerBoard controllerBoard;


    public Engine(Game game) {
        this.game = game;
        init();
    }

    private void init() {
        engineListeners = new HashSet<>();
        controllerBoard = new ControllerBoard(2);


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
            updateGameState();

            game.tick();
            updateListeners();


        }

    }

    private void updateGameState() {
        game.updateControllers(controllerBoard);
    }


    public void addListener(EngineListener listener) {
        if (!engineListeners.contains(listener)) {
            this.engineListeners.add(listener);
        }

    }

    private void updateListeners() { // TODO so this is all single threaded at this point
        for (EngineListener listener : engineListeners) {
            listener.onUpdate(game.getState());
        }
    }

    public void insertController(int port, GameController controller) {
        controllerBoard.insertController(port, controller);
    }
}
