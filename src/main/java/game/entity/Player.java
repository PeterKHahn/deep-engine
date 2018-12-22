package game.entity;

import game.action.ControllerButton;

import java.util.Set;

public interface Player extends DynamicEntity {


    void act(Set<ControllerButton> buttons);
}
