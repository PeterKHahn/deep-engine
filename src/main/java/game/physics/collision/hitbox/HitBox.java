package game.physics.collision.hitbox;

import game.environment.Point;

public class HitBox {

    private Box box;

    private double damage;


    private HitBox() {

    }

    public boolean collides(HurtBox hurtBox) {
        return box.collides(hurtBox.getBox());
    }

    public Box getBox() {
        return box;
    }

    public double getDamage() {
        return damage;
    }

    public static HitBoxBuilder builder() {
        return new HitBoxBuilder();
    }


    public static class HitBoxBuilder {

        private HitBox hitBox;
        private Point center;
        private double radius;

        private HitBoxBuilder() {
            this.hitBox = new HitBox();
        }

        public HitBoxBuilder setCenter(Point center) {
            this.center = center;
            return this;
        }

        public HitBoxBuilder setRadius(double radius) {
            this.radius = radius;
            return this;
        }

        public HitBoxBuilder setDamage(double damage) {
            hitBox.damage = damage;
            return this;
        }

        public HitBox build() {
            hitBox.box = new Box(center, radius);
            return hitBox;
        }
    }
}
