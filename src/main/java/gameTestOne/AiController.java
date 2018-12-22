package gameTestOne;

import game.Game;
import game.action.ControllerButton;
import game.action.PlayerController;
import game.entity.Player;

import java.util.HashSet;
import java.util.Set;

public class AiController extends PlayerController {
    /**
     * A player controller controls a specific player inside of a game.
     * This could be an AI player or an IRL player
     *
     * @param game
     * @param player
     */
    public AiController(Game game, Player player) {
        super(game, player);
    }

    @Override
    public void tick() {
        Set<ControllerButton> held = new HashSet<>();
        int xDirection = (int) (Math.random() * 3);
        switch (xDirection) {
            case 0:
                held.add(ControllerButton.LEFT);
            case 1:
                held.add(ControllerButton.RIGHT);

        }


        int yDirection = (int) (Math.random() * 3);
        switch (yDirection) {
            case 0:
                held.add(ControllerButton.DOWN);
            case 1:
                held.add(ControllerButton.UP);

        }

        controller.releaseAll();

        controller.hold(held);
    }
}
