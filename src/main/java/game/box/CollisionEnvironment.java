package game.box;

public class CollisionEnvironment {

    public final EnvironmentCollisionBox previousEcb;

    public final EnvironmentCollisionBox ecb;

    public final EnvironmentCollisionBox projectedEcb;

    public CollisionEnvironment(EnvironmentCollisionBox ecb) {
        this.previousEcb = ecb;
        this.ecb = ecb;
        this.projectedEcb = ecb;
    }


}
