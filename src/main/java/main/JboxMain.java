package main;


import boxEngine.BoxGame1;
import connect.TrainServer;
import io.javalin.Javalin;
import io.javalin.websocket.WsSession;
import org.jbox2d.testbed.framework.TestbedFrame;
import org.jbox2d.testbed.framework.TestbedModel;
import org.jbox2d.testbed.framework.TestbedPanel;
import org.jbox2d.testbed.framework.TestbedSetting;
import org.jbox2d.testbed.framework.j2d.TestPanelJ2D;

import javax.swing.*;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import static spark.Spark.webSocket;

public class JboxMain {

    private static Map<WsSession, BoxGame1> gameMap = new ConcurrentHashMap<>();

    public static void main(String[] args) {
        System.out.println("we in there");
        webSocket("/train", TrainServer.class);


        TestbedModel model = new TestbedModel();         // create our model
        model.addCategory("My Super Tests");             // add a category
        model.getSettings().addSetting(new TestbedSetting("My Range Setting", TestbedSetting.SettingType.ENGINE, 10, 0, 20));

        Javalin.create()
                .ws("/train", ws -> {
                    ws.onConnect(session -> {
                        BoxGame1 game = new BoxGame1(session);
                        model.addTest(game);
                        TestbedPanel panel = new TestPanelJ2D(model);    // create our testbed panel

                        gameMap.put(session, game);
                        JFrame testbed = new TestbedFrame(model, panel); // put both into our testbed frame
// etc
                        testbed.setVisible(true);
                        testbed.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);


                    });

                    ws.onClose((session, status, message) -> {


                    });

                    ws.onMessage((session, message) -> {

                        BoxGame1 game = gameMap.get(session);

                        if (message.equals("RESET")) {
                            System.out.println("RESETTI");
                            game.reset();

                        } else {
                            game.processAction(message);

                        }
                    });
                })
                .start(8080);

        System.out.println("Hail xo");


    }
}
