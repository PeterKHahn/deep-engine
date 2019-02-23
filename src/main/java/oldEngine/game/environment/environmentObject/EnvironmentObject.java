package oldEngine.game.environment.environmentObject;

import oldEngine.game.environment.CollisionEnvironment;
import oldEngine.game.environment.Vector;

public abstract class EnvironmentObject {


    public abstract void actOn(CollisionEnvironment environment);

    public abstract Vector p1();

    public abstract Vector p2();


}
