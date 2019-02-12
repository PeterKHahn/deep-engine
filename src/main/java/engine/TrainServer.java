package engine;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import game.DynamicGameState;
import game.Game;
import game.controller.GameController;
import game.environment.GameEnvironment;
import game.environment.Vector;
import game.environment.environmentObject.Floor;
import gameTestOne.AiController;
import gameTestOne.PlayerX;
import org.eclipse.jetty.websocket.api.Session;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketClose;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketConnect;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketMessage;
import org.eclipse.jetty.websocket.api.annotations.WebSocket;

import java.io.IOException;
import java.util.Set;

import static spark.Spark.init;
import static spark.Spark.webSocket;

@WebSocket
public class TrainServer implements EngineListener {

    private Session trainer;
    private GsonBuilder builder = new GsonBuilder();

    private Engine engine;

    public TrainServer() {
        webSocket("/train", TrainServer.class);
        init();
    }


    @OnWebSocketConnect
    public void onConnect(Session user) throws Exception {
        System.out.println("Connected");
        trainer = user;

        GameController controller1 = new GameController();
        GameController controller2 = new GameController();


        // Creates a game
        Game game = Game.builder()
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


        System.out.println("Initializing the engine");
        Engine engine = new Engine(game);

        // JPanelRenderer renderer = new JPanelRenderer(game, engine);
        // engine.addListener(renderer);
        System.out.println("Adding controllers");
        engine.addListener(this);

        engine.insertController(0, controller1);
        engine.insertController(1, controller2);

        new Thread(engine).start();

        user.getRemote().sendString("HELLO CHILD, WELCOME TO THE PARTY");

        Gson gson = builder.create();
        String json = gson.toJson(new Vector(5, 6));
        Set set = gson.fromJson("", Set.class);
        user.getRemote().sendString(json);

    }

    @OnWebSocketClose
    public void onClose(Session user, int statusCode, String reason) {
        System.out.println("Closed");
        trainer = null;

    }

    @OnWebSocketMessage
    public void onMessage(Session user, String message) {
        // This method will be called upon making an input.
        // In general gameplay, we will not advance the engine, it will just play
        engine.advance();

        System.out.println("Got a message");
        System.out.println(message);
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


