package game.environment.environmentObject;

import game.environment.CollisionEnvironment;
import game.environment.Vector;

public abstract class EnvironmentObject {


    public abstract void actOn(CollisionEnvironment environment);

    public abstract Vector p1();

    public abstract Vector p2();


}
