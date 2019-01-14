package game;

import engine.ControllerBoard;
import game.action.GameController;
import game.entity.Entity;
import game.entity.EntityState;
import game.entity.Player;
import game.environment.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Game {

    private Set<Entity> entities;
    private Player[] players;


    private GameEnvironment environment;


    private Game() {

        init();
    }

    private void init() {

        entities = new HashSet<>();


    }

    public static GameBuilder builder() {
        return new GameBuilder();
    }

    public void updateControllers(ControllerBoard controllerBoard) {
        for (int i = 0; i < players.length; i++) {
            GameController controller = controllerBoard.getController(i);

            if (controller == null) continue;

            players[i].act(controller.getHeld());

        }
    }


    public void tick() {


        for (Entity e : entities) {
            e.tick(this);
        }


    }

    public DynamicGameState getState() {
        Set<EntityState> states = new HashSet<>();
        for (Entity e : entities) {
            states.add(e.getState());
        }

        return DynamicGameState.builder()
                .addEntities(states)
                .build();
    }


    public void printState() {
        System.out.println("Printing Game State");
        for (Entity e : entities) {
            System.out.println(e);
        }
    }

    public Set<Entity> getEntities() {
        return entities;
    }

    public Set<EnvironmentObject> getEnvironmentObjects() {
        return environment.getEnvironmentObjects();
    }

    public Set<Floor> getFloors() {
        return environment.getFloors();
    }

    public Set<Ceiling> getCeilings() {
        return environment.getCeilings();
    }

    public Set<LeftWall> getLeftWalls() {
        return environment.getLeftWalls();
    }

    public Set<RightWall> getRightWalls() {
        return environment.getRightWalls();
    }

    public GameEnvironment getEnvironment() {
        return environment;
    }


    /**
     * The Game Builder class uses the builder pattern to create Games, also allowing
     * for input on a Game Environment.
     */
    public static class GameBuilder {

        private Game game;
        private List<Player> players;


        private GameBuilder() {
            init();
        }

        private void init() {
            game = new Game();
            players = new ArrayList<>();


        }


        public GameBuilder setEnvironment(GameEnvironment gameEnvironment) {
            game.environment = gameEnvironment;
            return this;
        }

        public GameBuilder insertPlayer(Player player) {
            game.entities.add(player);
            players.add(player);
            return this;
        }


        public Game build() {

            game.players = new Player[players.size()];
            for (int i = 0; i < players.size(); i++) {
                game.players[i] = players.get(i);
            }
            return game;
        }


    }


}
