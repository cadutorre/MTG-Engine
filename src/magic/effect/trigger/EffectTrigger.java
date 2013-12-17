package magic.effect.trigger;

import magic.effect.Effect;

public abstract class EffectTrigger {
    public EffectPredicate getPredicate() {
        return predicate;
    }

    public abstract Effect getEffect();

    public EffectTrigger(EffectPredicate predicate) {
        this.predicate = predicate;
    }

    private EffectPredicate predicate;
}
