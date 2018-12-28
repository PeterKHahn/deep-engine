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

    private Set<EnvironmentObject> environmentObjects;

    private Set<LeftWall> leftWalls;
    private Set<RightWall> rightWalls;
    private Set<Ceiling> ceilings;
    private Set<Floor> floors;


    public Game() {

        init();
    }

    private void init() {
        entities = new HashSet<>();
        controllers = new HashSet<>();
        environmentObjects = new HashSet<>();

        leftWalls = new HashSet<>();
        rightWalls = new HashSet<>();
        ceilings = new HashSet<>();
        floors = new HashSet<>();


    }


    public void tick() {


        // Iterate over all players for actions
        for (PlayerController controller : controllers) {
            controller.tick(this);
            controller.administer();
        }


        for (Entity e : entities) {
            e.updateState();
            e.updateProjectedPosition();


            // handle collision across environment
            for (EnvironmentObject obj : environmentObjects) {
                obj.actOn(e.getEnvironment());
            }

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

    private void handleEnvironmentCollision() {
        for (Entity e : entities) {

            for (EnvironmentObject obj : environmentObjects) {
                obj.actOn(e.getEnvironment());
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
        return environmentObjects;
    }

    public Set<Floor> getFloors() {
        return floors;
    }

    public Set<Ceiling> getCeilings() {
        return ceilings;
    }

    public Set<LeftWall> getLeftWalls() {
        return leftWalls;
    }

    public Set<RightWall> getRightWalls() {
        return rightWalls;
    }


    public static class GameBuilder {


        private Game game;


        public GameBuilder() {
            init();
        }

        private void init() {
            game = new Game();


        }


        public void insertPlayer(PlayerController controller) {
            Player entity = controller.getPlayer();
            game.entities.add(entity);
            game.controllers.add(controller);
        }

        public void insertCeiling(Ceiling ceiling) {
            game.environmentObjects.add(ceiling);
            game.ceilings.add(ceiling);
        }

        public void insertFloor(Floor floor) {
            game.environmentObjects.add(floor);
            game.floors.add(floor);
        }

        public void insertRightWall(RightWall rightWall) {
            game.environmentObjects.add(rightWall);
            game.rightWalls.add(rightWall);
        }

        public void insertLeftWall(LeftWall leftWall) {
            game.environmentObjects.add(leftWall);
            game.leftWalls.add(leftWall);
        }


        public Game build() {


            return game;
        }


    }


}
