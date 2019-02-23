package oldEngine.game.environment;

import oldEngine.game.entity.Entity;
import oldEngine.game.environment.environmentObject.*;

import java.util.HashSet;
import java.util.Set;

public class GameEnvironment {

    private Set<LeftWall> leftWalls;
    private Set<RightWall> rightWalls;
    private Set<Ceiling> ceilings;
    private Set<Floor> floors;

    private Set<EnvironmentObject> environmentObjects;


    private GameEnvironment() {
        init();
    }

    private void init() {
        leftWalls = new HashSet<>();
        rightWalls = new HashSet<>();
        ceilings = new HashSet<>();
        floors = new HashSet<>();
        environmentObjects = new HashSet<>();
    }

    public void actOn(Entity entity) {
        for (EnvironmentObject obj : environmentObjects) {
            obj.actOn(entity.getEnvironment());
        }
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

    public static GameEnvironmentBuilder builder() {
        return new GameEnvironmentBuilder();
    }


    public static class GameEnvironmentBuilder {

        private GameEnvironment gameEnvironment;

        private GameEnvironmentBuilder() {
            gameEnvironment = new GameEnvironment();
        }

        public GameEnvironmentBuilder addLeftWall(LeftWall leftWall) {
            gameEnvironment.leftWalls.add(leftWall);
            gameEnvironment.environmentObjects.add(leftWall);
            return this;
        }

        public GameEnvironmentBuilder addRightWall(RightWall rightWall) {
            gameEnvironment.rightWalls.add(rightWall);
            gameEnvironment.environmentObjects.add(rightWall);

            return this;
        }

        public GameEnvironmentBuilder addCeiling(Ceiling ceiling) {
            gameEnvironment.ceilings.add(ceiling);
            gameEnvironment.environmentObjects.add(ceiling);

            return this;
        }

        public GameEnvironmentBuilder addFloor(Floor floor) {
            gameEnvironment.floors.add(floor);
            gameEnvironment.environmentObjects.add(floor);

            return this;
        }


        public GameEnvironment build() {
            return gameEnvironment;
        }
    }

}
