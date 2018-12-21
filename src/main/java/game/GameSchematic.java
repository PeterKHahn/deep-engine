package game;


import game.map.GameMap;

public class GameSchematic {

    private GameMap initialMap;

    public GameSchematic(GameMap initialMap) {

        this.initialMap = initialMap;
    }

    public GameMap getInitialMap() {
        return initialMap;
    }


}
