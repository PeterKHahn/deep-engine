package gameBuilder;

import org.jbox2d.common.Vec2;

public class EntityState {

    private Vec2 position;
    private Vec2 velocity;
    private float angle;
    private float angularVelocity;
    public int test = 5;


    public static EntityStateBuilder builder() {
        return new EntityStateBuilder();
    }


    public static class EntityStateBuilder {

        private Vec2 position;
        private Vec2 velocity;
        private float angle;
        private float angularVelocity;


        private EntityStateBuilder() {

        }


        public EntityStateBuilder position(Vec2 position) {
            this.position = position;
            return this;
        }

        public EntityStateBuilder angle(float angle) {

            this.angle = angle;
            return this;
        }

        public EntityStateBuilder linearVelocity(Vec2 velocity) {
            this.velocity = velocity;
            return this;
        }

        public EntityStateBuilder angularVelocity(float angularVelocity) {

            this.angularVelocity = angularVelocity;
            return this;
        }


        public EntityState build() {
            EntityState state = new EntityState();
            state.position = this.position;
            state.velocity = this.velocity;
            state.angle = this.angle;
            state.angularVelocity = this.angularVelocity;

            return state;
        }


    }
}
