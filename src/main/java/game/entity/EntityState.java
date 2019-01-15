package game.entity;

import game.environment.EnvironmentCollisionBox;
import game.physics.collision.hitbox.HitBox;
import game.physics.collision.hitbox.HurtBox;

public class EntityState {

    private int tick;
    private double damage;
    private EnvironmentCollisionBox ecb;

    private HitBox hitBox;
    private HurtBox hurtBox;

    private boolean grounded;

    private EntityState() {
    }

    public static EntityStateBuilder builder() {
        return new EntityStateBuilder();
    }

    public int getTick() {
        return tick;
    }

    public EnvironmentCollisionBox getEcb() {
        return ecb;
    }

    public HitBox getHitBox() {
        return hitBox;
    }

    public HurtBox getHurtBox() {
        return hurtBox;
    }

    public boolean getGrounded() {
        return grounded;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Grounded: " + grounded);
        return sb.toString();
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

        public EntityStateBuilder hitBox(HitBox hitBox) {
            state.hitBox = hitBox;
            return this;
        }

        public EntityStateBuilder hurtBox(HurtBox hurtBox) {
            state.hurtBox = hurtBox;
            return this;
        }

        public EntityStateBuilder grounded(boolean grounded) {
            state.grounded = grounded;
            return this;
        }

        public EntityStateBuilder damage(double damage) {
            state.damage = damage;
            return this;
        }

        public EntityState build() {
            return state;
        }


    }


}
