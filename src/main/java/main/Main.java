package main;

import engine.Engine;
import game.Game;
import game.environment.CollisionEnvironment;
import game.environment.EnvironmentCollisionBox;
import game.environment.Point;
import gameTestOne.AiController;
import gameTestOne.PlayerX;

import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        System.out.println("Welcome to Deep Engine");
        System.out.println("Building the Map...");
        Game.GameBuilder builder = new Game.GameBuilder();

        // TODO add more stuff to the map including static environment objects
        builder.insertPlayer(new AiController(
                new PlayerX(new CollisionEnvironment(
                        new EnvironmentCollisionBox(new Point(-5, 0))))));
        builder.insertPlayer(new AiController(
                new PlayerX(new CollisionEnvironment(
                        new EnvironmentCollisionBox(new Point(5, 0))))));


        Game game = builder.build();


        Engine engine = new Engine(game);
        Thread t = new Thread(engine);
        t.start();

        Scanner scanner = new Scanner(System.in);
        while (scanner.hasNextLine()) {
            String input = scanner.nextLine();

            System.out.println("inputed: " + input);

            if (input.equals("pause")) {
                engine.pause();
            }

        }
        scanner.close();
    }
}
