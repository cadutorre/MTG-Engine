package magic.effect;

import magic.Engine;

public class MultiEffect implements Effect {

    public void execute(Engine engine) {
        for (Effect e : effects)
            e.execute(engine);
    }

    @Override
    public boolean someTargetsLegal() {
        for (Effect e : effects) {
            if (e.someTargetsLegal())
                return true;
        }
        return false;
    }

    public Effect[] getEffects() {
        return effects;
    }

    public MultiEffect(Effect... effects) {
        this.effects = effects;
    }

    private Effect[] effects;
}
