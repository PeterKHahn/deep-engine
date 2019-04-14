package connect;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import oldEngine.game.controller.GameController;
import oldEngine.game.environment.Vector;
import org.eclipse.jetty.websocket.api.Session;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketClose;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketConnect;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketMessage;
import org.eclipse.jetty.websocket.api.annotations.WebSocket;

@WebSocket
public class TrainServer {

    private Session trainer;
    private GsonBuilder builder = new GsonBuilder();


    @OnWebSocketConnect
    public void onConnect(Session user) throws Exception {
        System.out.println("Connected");
        trainer = user;


        user.getRemote().sendString("HELLO CHILD, WELCOME TO THE PARTY");

        Gson gson = builder.create();
        String json = gson.toJson(new Vector(5, 6));
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

        Gson gson = builder.create();
        try {
            GameController controller = gson.fromJson(message, GameController.class);
                System.out.println(controller.getHeld());
        } catch (Exception e) {
            e.printStackTrace();
        }


        System.out.println("Got a message");
    }

    public void sendMessage() {

    }
}
