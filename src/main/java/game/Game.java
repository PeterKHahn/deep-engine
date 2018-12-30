package game;

import game.action.PlayerController;
import game.entity.Entity;
import game.entity.EntityState;
import game.entity.Player;
import game.environment.*;

import java.util.HashSet;
import java.util.Set;

public class Game {

    private Set<PlayerController> controllers;
    private Set<Entity> entities;


    private GameEnvironment environment;


    private Game() {

        init();
    }

    private void init() {

        entities = new HashSet<>();
        controllers = new HashSet<>();


    }

    public static GameBuilder builder() {
        return new GameBuilder();
    }


    public void tick() {


        // Iterate over all players for actions
        for (PlayerController controller : controllers) {
            controller.tick(getState());
            controller.administer();
        }


        for (Entity e : entities) {
            e.updateState();
            e.updateProjectedPosition();


            // handle collision across environment
            environment.actOn(e);


            e.updateEcb();
        }

        for (Entity e : entities) {

            for (Entity e1 : entities) { // handle collision across entities
                if (e != e1 && e.collides(e1)) {
                    // TODO fill
                }
            }
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

    private void handleCollision() {
        for (Entity e : entities) {

            for (Entity e1 : entities) {
                if (e != e1 && e.collides(e1)) {
                    // TODO Fill
                }
            }

        }
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


    public static class GameBuilder {


        private Game game;


        private GameBuilder() {
            init();
        }

        private void init() {
            game = new Game();


        }


        public GameBuilder setEnvironment(GameEnvironment gameEnvironment) {
            game.environment = gameEnvironment;
            return this;
        }


        public GameBuilder insertPlayer(PlayerController controller) {
            Player entity = controller.getPlayer();
            game.entities.add(entity);
            game.controllers.add(controller);
            return this;
        }


        public Game build() {


            return game;
        }


    }


}
