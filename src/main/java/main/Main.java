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
                .insertPlayer(new AiController(
                        new PlayerX(new CollisionEnvironment(
                                new EnvironmentCollisionBox(new Point(-5, 0))))))
                .insertPlayer(new AiController(
                        new PlayerX(new CollisionEnvironment(
                                new EnvironmentCollisionBox(new Point(5, 0))))))
                .setEnvironment(
                        GameEnvironment.builder()
                                .addFloor(new Floor(new Point(-250, -10), 500))
                                .build()
                )
                .build();


        Engine engine = new Engine(game);
        new Thread(engine).start();


    }
}
