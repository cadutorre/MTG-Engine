package magic.effect.trigger;

import magic.card.Permanent;
import magic.effect.Effect;

public abstract class EffectReplacer {

    public EffectPredicate getPredicate() {
        return predicate;
    }

    public Permanent getSource() {
        return source;
    }

    public abstract Effect getReplacement(Effect effect);

    public EffectReplacer(Permanent source, EffectPredicate predicate) {
        this.source = source;
        this.predicate = predicate;
    }

    private Permanent source;
    private EffectPredicate predicate;
}
