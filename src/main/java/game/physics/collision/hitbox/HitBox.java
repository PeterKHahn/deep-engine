package game.physics.collision.hitbox;

import game.environment.Vector;

public class HitBox extends Box {


    public final double damage;


    private HitBox(Vector origin, Vector offset, double radius, double damage) {
        super(origin, offset, radius);
        this.damage = damage;

    }


    public static HitBoxBuilder builder() {
        return new HitBoxBuilder();
    }

    public static HitBoxBuilder builder(HitBox of) {
        return new HitBoxBuilder(of);
    }


    public static class HitBoxBuilder {

        private Vector origin;
        private Vector offset;
        private double radius;
        private double damage;


        private HitBoxBuilder() {
            this.origin = Vector.ORIGIN;
            this.offset = Vector.ORIGIN;
            this.radius = 0;
            this.damage = 0;
        }

        private HitBoxBuilder(HitBox of) {
            if (of == null) return;

            this.origin = of.origin;
            this.offset = of.offset;
            this.radius = of.radius;
            this.damage = of.damage;
        }

        public HitBoxBuilder setOrigin(Vector origin) {
            this.origin = origin;
            return this;
        }

        public HitBoxBuilder setOffset(Vector offset) {
            this.offset = offset;
            return this;
        }

        public HitBoxBuilder setRadius(double radius) {
            this.radius = radius;
            return this;
        }

        public HitBoxBuilder setDamage(double damage) {
            this.damage = damage;
            return this;
        }

        public HitBox build() {
            return new HitBox(origin, offset, radius, damage);
        }
    }
}
