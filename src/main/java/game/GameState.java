package game;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import entity.EntityState;

public class GameState {

    private static GsonBuilder builder = new GsonBuilder();

    private static Gson gson = builder.create();


    public final EntityState player1;
    public final EntityState player2;

    private GameState(Game game) {
        this.player1 = game.getPlayer1().state();
        this.player2 = game.getPlayer2().state();
    }

    public String toJson() {
        String json = gson.toJson(this);
        return json;
    }


    public static GameState state(Game game) {
        return new GameState(game);
    }


}
