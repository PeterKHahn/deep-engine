package main;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import game.controller.GameController;
import game.environment.Vector;
import org.eclipse.jetty.websocket.api.Session;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketClose;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketConnect;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketMessage;
import org.eclipse.jetty.websocket.api.annotations.WebSocket;

import static spark.Spark.init;
import static spark.Spark.webSocket;

@WebSocket
public class ServerTest {

    public static void main(String[] args) {

        webSocket("/chat", ServerTest.class);
        System.out.println("HA");
        init();
        // get("/ack", (req, res) -> "Hello World");
    }


    @OnWebSocketConnect
    public void onConnect(Session user) throws Exception {
        System.out.println("Connected");
        user.getRemote().sendString("HELLO CHILD, WELCOME TO THE PARTY");
        GsonBuilder builder = new GsonBuilder();

        Gson gson = builder.create();
        String json = gson.toJson(new Vector(5, 6));
        user.getRemote().sendString(json);


    }

    @OnWebSocketClose
    public void onClose(Session user, int statusCode, String reason) {
        System.out.println("Closed");

    }

    @OnWebSocketMessage
    public void onMessage(Session user, String message) {
        System.out.println("Got a message!");
        System.out.println(message);
        GsonBuilder builder = new GsonBuilder();

        Gson gson = builder.create();
        try {
            GameController controller = gson.fromJson(message, GameController.class);
            System.out.println(controller.getHeld());
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
