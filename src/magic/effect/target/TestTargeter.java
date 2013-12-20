package magic.effect.target;

import magic.effect.Effect;

import java.util.HashMap;

public class TestTargeter implements TargetChooser {
    @Override
    public void chooseTarget(Effect effect) {
       effect.setTarget(targets.get(effect));
    }

    public void setTarget(Effect effect, Object target) {
        targets.put(effect, target);
    }

    public TestTargeter() {
        targets = new HashMap<>();
    }

    private HashMap<Effect, Object> targets;
}
