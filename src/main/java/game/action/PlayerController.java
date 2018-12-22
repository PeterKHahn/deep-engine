package game.action;

import game.Game;
import game.entity.Player;

public abstract class PlayerController {


    // TODO protected getters in future?
    public final Player player;
    protected GameController controller;

    /**
     * A player controller controls a specific player inside of a game.
     * This could be an AI player or an IRL player
     *
     * @param player
     */
    public PlayerController(Player player) {
        this.player = player;
        init();
    }

    private void init() {
        controller = new GameController();
    }

    // TODO we will be making this async in the future, this tick
    // TODO method is temporary.
    public abstract void tick(Game game);

    public void administer() {
        player.act(controller.getHeld());
    }
}
