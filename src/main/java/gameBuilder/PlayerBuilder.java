package gameBuilder;

import entity.EntityBuilder;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.BodyType;
import org.jbox2d.dynamics.World;

import java.util.LinkedList;
import java.util.List;

public class PlayerBuilder extends EntityBuilder {

    private List<Box> boxes;

    public PlayerBuilder(World world) {
        super(world);
        init();
        initBoxes();
    }

    private void init() {
        boxes = new LinkedList<>();
    }

    private void initBoxes() {
        boxes.add(new Box(6, 8, new Vec2(0, 0), 0));
        boxes.add(new Box(6, 2, new Vec2(10, 8), 0));
        boxes.add(new Box(6, 2, new Vec2(10, -8), 0));

    }

    @Override
    public float density() {
        return 5.0f;
    }

    @Override
    public float friction() {
        return 0.0f;
    }

    @Override
    public BodyType bodyType() {
        return BodyType.DYNAMIC;
    }

    @Override
    public Vec2 initialPosition() {
        return new Vec2(0, 0);
    }

    @Override
    public List<Box> boxes() {
        return boxes;
    }
}
