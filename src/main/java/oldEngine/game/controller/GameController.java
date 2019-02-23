package oldEngine.game.controller;

import java.util.HashSet;
import java.util.Set;

public class GameController {

    private Set<ControllerButton> held;

    public GameController() {
        init();
    }

    private void init() {
        held = new HashSet<>();
    }


    public void release(Set<ControllerButton> buttons) {
        held.removeAll(buttons);
    }

    public void hold(Set<ControllerButton> buttons) {
        held.addAll(buttons);
    }

    public Set<ControllerButton> getHeld() {
        return held;
    }

    public void releaseAll() {
        held.clear();
    }


}
