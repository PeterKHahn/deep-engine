package oldEngine.game;

import com.google.common.collect.ImmutableSet;
import oldEngine.game.entity.EntityState;

import java.util.Set;

public class DynamicGameState {

    private Set<EntityState> entityStates;

    private DynamicGameState() {
    }

    public Set<EntityState> getEntityStates() {
        return entityStates;
    }

    public static GameStateBuilder builder() {
        return new GameStateBuilder();
    }

    public static class GameStateBuilder {

        private DynamicGameState state;


        private GameStateBuilder() {
            state = new DynamicGameState();
        }

        public GameStateBuilder addEntities(Set<EntityState> entityStates) {
            state.entityStates = ImmutableSet.copyOf(entityStates);
            return this;

        }

        public DynamicGameState build() {
            return state;
        }
    }
}
