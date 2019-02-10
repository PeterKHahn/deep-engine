package engine;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import game.DynamicGameState;
import game.environment.Vector;
import org.eclipse.jetty.websocket.api.Session;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketClose;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketConnect;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketMessage;
import org.eclipse.jetty.websocket.api.annotations.WebSocket;

import java.io.IOException;

import static spark.Spark.init;
import static spark.Spark.webSocket;

@WebSocket
public class TrainServer implements EngineListener {

    private Session trainer = null;
    private GsonBuilder builder = new GsonBuilder();


    public TrainServer() {
        webSocket("/chat", TrainServer.class);
        init();
    }


    @OnWebSocketConnect
    public void onConnect(Session user) throws Exception {
        System.out.println("Connected");
        user.getRemote().sendString("HELLO CHILD, WELCOME TO THE PARTY");

        Gson gson = builder.create();
        String json = gson.toJson(new Vector(5, 6));
        user.getRemote().sendString(json);
        trainer = user;

    }

    @OnWebSocketClose
    public void onClose(Session user, int statusCode, String reason) {
        System.out.println("Closed");
        trainer = null;

    }

    @OnWebSocketMessage
    public void onMessage(Session user, String message) {
        try {
            user.getRemote().sendString("hi");
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("Got a message");
        System.out.println(message);
    }


    @Override
    public void onUpdate(DynamicGameState gameState) {
        if (trainer != null) {
            Gson gson = builder.create();
            String json = gson.toJson(new Vector(5, 6));
            try {
                trainer.getRemote().sendString(json);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

}


