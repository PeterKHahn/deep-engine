package gameTestOne;

import oldEngine.engine.EngineListener;
import oldEngine.game.DynamicGameState;
import oldEngine.game.controller.ControllerButton;
import oldEngine.game.controller.GameController;

import java.util.HashSet;
import java.util.Set;

public class AiController implements EngineListener {

    private GameController controller;

    /**
     * A player controller controls a specific player inside of a game.
     * This could be an AI player or an IRL player
     */
    public AiController() {
        init();
    }

    private void init() {
        controller = new GameController();
    }


    @Override
    public void onUpdate(DynamicGameState gameState) {
        Set<ControllerButton> held = new HashSet<>();
        int xDirection = (int) (Math.random() * 3);
        switch (xDirection) {
            case 0:
                held.add(ControllerButton.LEFT);
                break;
            case 1:
                held.add(ControllerButton.RIGHT);
                break;
            default:
                break;

        }


        int yDirection = (int) (Math.random() * 3);
        switch (yDirection) {
            case 0:
                held.add(ControllerButton.DOWN);
                break;
            case 1:
                held.add(ControllerButton.UP);
                break;
            default:
                break;

        }

        controller.releaseAll();

        controller.hold(held);
    }

    public GameController getController() {
        return controller;
    }
}
