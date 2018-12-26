package game.box;

public abstract class EnvironmentObject {


    public abstract void actOnCollide(CollisionEnvironment environment);

    public abstract boolean collide(CollisionEnvironment environment);


}
