package engine.physics.collision;

public interface Hitbox {

    boolean collides(SingularHitbox hitbox);

    boolean collides(ExtendedHitbox hitbox);

    boolean collides(Hitbox hitbox);
}
