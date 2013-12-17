package magic.effect.trigger;

import magic.effect.Effect;

public abstract class EffectReplacer {

    public EffectPredicate getPredicate() {
        return predicate;
    }

    public abstract Effect getReplacement(Effect effect);

    public EffectReplacer(EffectPredicate predicate) {
        this.predicate = predicate;
    }

    private EffectPredicate predicate;
}
