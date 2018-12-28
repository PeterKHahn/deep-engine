package game;

import com.google.common.collect.ImmutableSet;
import game.entity.EntityState;

import java.util.Set;

public class DynamicGameState {

    private Set<EntityState> entityStates;


    private DynamicGameState() {

    }

    public static GameStateBuilder builder() {
        return new GameStateBuilder();
    }

    public static class GameStateBuilder {

        private DynamicGameState state;


        private GameStateBuilder() {
            state = new DynamicGameState();
        }

        public void addEntities(Set<EntityState> entityStates) {
            state.entityStates = ImmutableSet.copyOf(entityStates);

        }

        public DynamicGameState build() {
            return state;
        }
    }
}
