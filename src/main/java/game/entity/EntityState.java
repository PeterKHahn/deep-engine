package game.entity;

import game.environment.EnvironmentCollisionBox;

public class EntityState {

    private int tick;
    private EnvironmentCollisionBox ecb;

    private EntityState() {
    }

    public static EntityStateBuilder builder() {
        return new EntityStateBuilder();
    }


    // TODO ADD HITBOXES
    // TODO ADD STATUS

    // TODO ADD IDs



    public static class EntityStateBuilder {

        private EntityState state;

        private EntityStateBuilder() {
            this.state = new EntityState();
        }

        public EntityStateBuilder tick(int tick) {
            state.tick = tick;
            return this;
        }

        public EntityStateBuilder ecb(EnvironmentCollisionBox ecb) {
            state.ecb = ecb;
            return this;
        }

        public EntityState build() {
            return state;
        }


    }


}
