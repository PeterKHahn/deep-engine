package main;

import engine.Engine;
import game.Game;
import game.environment.*;
import gameTestOne.AiController;
import gameTestOne.PlayerX;

public class Main {

    public static void main(String[] args) {
        System.out.println("Welcome to Deep Engine");
        System.out.println("Building the Map...");
        Game game = Game.builder()
                .insertPlayer(
                        new PlayerX(new CollisionEnvironment(
                                new EnvironmentCollisionBox(new Point(-5, 0)))))
                .insertPlayer(
                        new PlayerX(new CollisionEnvironment(
                                new EnvironmentCollisionBox(new Point(5, 0)))))
                .setEnvironment(
                        GameEnvironment.builder()
                                .addFloor(new Floor(new Point(-250, -10), 500))
                                .build()
                )
                .build();

        AiController controller1 = new AiController();
        AiController controller2 = new AiController();

        System.out.println("Initializing the engine");
        Engine engine = new Engine(game);

        System.out.println("Adding controllers");
        engine.addListener(controller1);
        engine.addListener(controller2);
        engine.insertController(0, controller1.getController());
        engine.insertController(1, controller2.getController());
        new Thread(engine).start();


    }
}
