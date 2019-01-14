package game.physics.collision.hitbox;

import game.environment.Point;

public class HurtBox {

    private Box box; // TODO fill


    private double damage;


    private HurtBox() {

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

    public static HurtBoxBuilder builder() {
        return new HurtBoxBuilder();
    }


    public static class HurtBoxBuilder {

        private HurtBox hurtBox;
        private Point center;
        private double radius;

        private HurtBoxBuilder() {
            this.hurtBox = new HurtBox();
        }

        public HurtBoxBuilder setCenter(Point center) {
            this.center = center;
            return this;
        }

        public HurtBoxBuilder setRadius(double radius) {
            this.radius = radius;
            return this;
        }


        public HurtBox build() {
            hurtBox.box = new Box(center, radius);
            return hurtBox;
        }
    }
}
