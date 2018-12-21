package main;

import engine.Engine;
import game.Game;
import game.GameSchematic;
import game.map.GameMap;
import game.map.MapBuilder;

public class Main {

    public static void main(String[] args) {
        System.out.println("Welcome to Deep Engine");
        System.out.println("Buildering the Map...");
        MapBuilder builder = new MapBuilder();

        // TODO add more stuff to the map

        GameMap map = builder.build();

        GameSchematic schematic = new GameSchematic(map);
        System.out.println("Creating the game...");
        Game g = new Game(schematic);

        System.out.println("Starting the engine...");
        Engine engine = new Engine(g);
        engine.run();
    }
}
