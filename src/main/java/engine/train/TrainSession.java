package engine.train;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import engine.Engine;
import engine.EngineListener;
import game.DynamicGameState;
import game.Game;
import game.controller.GameController;
import game.environment.GameEnvironment;
import game.environment.Vector;
import game.environment.environmentObject.Floor;
import gameTestOne.PlayerX;
import org.eclipse.jetty.websocket.api.Session;

import java.io.IOException;

public class TrainSession implements EngineListener {

    private Session trainer;

    private Engine engine;
    private Game game;

    private GameController controller1;
    private GameController controller2;


    private GsonBuilder builder = new GsonBuilder();


    public TrainSession(Session trainer) {
        this.trainer = trainer;
        init();
    }

    private void init() {
        controller1 = new GameController();
        controller2 = new GameController();

        game = Game.builder()
                .insertPlayer(
                        new PlayerX(new Vector(-5, 0)))
                .insertPlayer(
                        new PlayerX(new Vector(5, 0)))
                .setEnvironment(
                        GameEnvironment.builder()
                                .addFloor(new Floor(new Vector(-250, -10), 500))
                                .build()
                )
                .build();

        engine = new Engine(game);

        engine.addListener(this);

    }

    @Override
    public void onUpdate(DynamicGameState gameState) {
        Gson gson = builder.create();
        String json = gson.toJson(gameState);
        try {
            trainer.getRemote().sendString(json);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
