package engine;

import game.controller.GameController;

public class ControllerBoard {

    private GameController[] controllers;
    public final int numControllers;

    public ControllerBoard(int numControllers) {
        this.numControllers = numControllers;
        init();
    }

    private void init() {
        controllers = new GameController[numControllers];
    }


    public void insertController(int port, GameController controller) {
        if (port >= 0 && port < numControllers) {
            controllers[port] = controller;
        }
    }

    public GameController getController(int i) {
        if (i >= 0 && i < numControllers) {
            return controllers[i];
        }
        throw new RuntimeException(); // TODO add a real exception
    }


}
