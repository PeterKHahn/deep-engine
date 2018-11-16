package engine.mechanics;

import engine.physics.collision.Vector;

public class Effect {

    private Effect() {

    }

    public void apply() {
        // TODO fill this in with the correct entity.
    }

    public static EffectBuilder builder() {
        return new EffectBuilder();

    }

    private static class EffectBuilder {

        private Vector velocityChange;

        public void changeVelocity(Vector delta) {
            velocityChange = delta;
        }
    }
}
