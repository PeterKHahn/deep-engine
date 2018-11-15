package engine.physics.collision;

public class ExtendedHitbox implements Hitbox {


    private final Hitbox startHitbox;
    private final Hitbox endHitbox;

    public ExtendedHitbox(Hitbox startHitbox, Hitbox endHitbox) {

        this.startHitbox = startHitbox;
        this.endHitbox = endHitbox;
    }


    @Override
    public boolean collides(SingularHitbox hitbox) {
        return startHitbox.collides(hitbox)
                || endHitbox.collides(hitbox);
    }

    @Override
    public boolean collides(ExtendedHitbox hitbox) {
        return false;
    }

    @Override
    public boolean collides(Hitbox hitbox) {
        return hitbox.collides(this);
    }
}
