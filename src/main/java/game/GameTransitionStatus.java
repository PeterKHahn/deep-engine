package game;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class GameTransitionStatus {

    public enum MessageType {
        INIT, NORMAL;
    }

    public final MessageType type;

    public final GameState state;
    public final boolean ongoing;
    public final double[] rewards;

    private static GsonBuilder builder = new GsonBuilder();

    private static Gson gson = builder.create();


    private GameTransitionStatus(GameState state, boolean ongoing, double[] rewards, MessageType type) {
        this.state = state;
        this.ongoing = ongoing;
        this.rewards = rewards;
        this.type = type;
    }

    public String toJson() {
        String json = gson.toJson(this);
        return json;
    }


    public static GameTransitionStatus status(Game game, double[] rewards, MessageType type) {
        GameState state = GameState.state(game);
        boolean ongoing = game.ongoing();
        return new GameTransitionStatus(state, ongoing, rewards, type);
    }


}
