package game.environment;

public abstract class EnvironmentObject {


    public abstract void actOn(CollisionEnvironment environment);

    public abstract Point p1();

    public abstract Point p2();


}
