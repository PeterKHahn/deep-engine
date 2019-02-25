package game;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class GameTransitionStatus {

    public final GameState state;
    public final boolean ongoing;
    public final double[] rewards;

    private static GsonBuilder builder = new GsonBuilder();

    private static Gson gson = builder.create();


    private GameTransitionStatus(GameState state, boolean ongoing, double[] rewards) {
        this.state = state;
        this.ongoing = ongoing;
        this.rewards = rewards;
    }

    public String toJson() {
        String json = gson.toJson(this);
        return json;
    }


    public static GameTransitionStatus status(Game game, double[] rewards) {
        GameState state = GameState.state(game);
        boolean ongoing = game.ongoing();
        return new GameTransitionStatus(state, ongoing, rewards);
    }


}
