package game.entity;

public interface DynamicEntity extends Entity {
    void tick();

    boolean collides(Entity e);

}
