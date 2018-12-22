package game.action;

import game.Game;
import game.entity.Player;

public abstract class PlayerController {


    // TODO protected getters in future?
    protected final Game game;
    protected final Player player;
    protected GameController controller;

    /**
     * A player controller controls a specific player inside of a game.
     * This could be an AI player or an IRL player
     *
     * @param game
     * @param player
     */
    public PlayerController(Game game, Player player) {
        this.game = game;
        this.player = player;
        init();
    }

    private void init() {
        controller = new GameController();
    }

    // TODO we will be making this async in the future, this tick
    // TODO method is temporary.
    public abstract void tick();

    public void administer() {
        player.act(controller.getHeld());
    }
}
