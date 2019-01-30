package main;

import org.eclipse.jetty.websocket.api.annotations.OnWebSocketClose;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketConnect;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketMessage;
import org.eclipse.jetty.websocket.api.annotations.WebSocket;
import spark.Session;

import static spark.Spark.*;

@WebSocket
public class ServerTest {

    public static void main(String[]args) {

        webSocket("/chat", ServerTest.class);
        System.out.println("HA");
        init();
        // get("/ack", (req, res) -> "Hello World");
    }



    @OnWebSocketConnect
    public void onConnect(Session user) throws Exception {
        System.out.println("Connected");

    }

    @OnWebSocketClose
    public void onClose(Session user, int statusCode, String reason) {
        System.out.println("Closed");

    }

    @OnWebSocketMessage
    public void onMessage(Session user, String message) {
        System.out.println("Got a message");
        System.out.println(message);
    }
}
