package main;

import engine.Engine;
import game.Game;
import game.GameSchematic;
import game.box.CollisionEnvironment;
import game.box.EnvironmentCollisionBox;
import game.box.Point;
import game.map.GameMap;
import game.map.MapBuilder;
import gameTestOne.AiController;
import gameTestOne.PlayerX;
import rendering.JPanelRenderer;

public class Main {

    public static void main(String[] args) {
        System.out.println("Welcome to Deep Engine");
        System.out.println("Buildering the Map...");
        MapBuilder builder = new MapBuilder();

        // TODO add more stuff to the map including static environment objects
        builder.insertPlayer(new AiController(
                new PlayerX(new CollisionEnvironment(
                        new EnvironmentCollisionBox(new Point(-5, 0))))));
        builder.insertPlayer(new AiController(
                new PlayerX(new CollisionEnvironment(
                        new EnvironmentCollisionBox(new Point(5, 0))))));


        GameMap map = builder.build();

        GameSchematic schematic = new GameSchematic(map);
        System.out.println("Creating the game...");
        Game g = new Game(schematic);

        System.out.println("Starting the engine...");
        Engine engine = new Engine(g, new JPanelRenderer(g));
        engine.run();
    }
}
