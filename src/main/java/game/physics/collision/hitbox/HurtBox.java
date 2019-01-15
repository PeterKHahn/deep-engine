package game.physics.collision.hitbox;

import game.environment.Vector;

public class HurtBox extends Box {


    private HurtBox(Vector origin, Vector offset, double radius) {
        super(origin, offset, radius);

    }

    public static HurtBoxBuilder builder() {
        return new HurtBoxBuilder();
    }

    public static HurtBoxBuilder(HurtBox of) {
        return new HurtBoxBuilder(of);
    }


    public static class HurtBoxBuilder {
        private Vector origin;
        private Vector offset;
        private double radius;

        private HurtBoxBuilder() {

        }

        private HurtBoxBuilder(HurtBox of) {
            this.origin = of.origin;
            this.offset = of.offset;
            this.radius = of.radius;
        }

        public HurtBoxBuilder setOrigin(Vector origin) {
            this.origin = origin;
            return this;
        }

        public HurtBoxBuilder setOffset(Vector offset) {
            this.offset = offset;
            return this;
        }

        public HurtBoxBuilder setRadius(double radius) {
            this.radius = radius;
            return this;
        }

        public HurtBox build() {
            return new HurtBox(origin, offset, radius);
        }
    }


}
