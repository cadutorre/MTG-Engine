package magic.effect;

public class MultiEffect implements Effect {
    @Override
    public void accept(EffectExecutor executor) {
        executor.execute(this);
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
