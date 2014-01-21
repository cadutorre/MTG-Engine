package magic.effect.trigger;

import magic.Engine;
import magic.Stackable;
import magic.card.Permanent;
import magic.effect.Effect;
import magic.effect.EffectFactory;

public class TriggeredAbility {

    public boolean isTriggered(Stackable stackable, Engine engine) {
        return predicate.predicate(owner, stackable, engine);
    }

    public Effect getTriggeredEffect(Stackable stackable) {
        return factory.getEffect(owner, stackable);
    }

    /**
     * Clones the Triggered Ability
     *
     * The Card-copy that will own the Triggered Ability is provided because any Predicates
     * and Effects should be in terms of the copied Card.
     *
     */
    public TriggeredAbility clone(Permanent owner) {
        TriggeredAbility clone = new TriggeredAbility(predicate, factory);
        clone.owner = owner;
        return clone;
    }

    public TriggeredAbility(EffectPredicate predicate, EffectFactory factory) {
        this.predicate = predicate;
        this.factory = factory;
    }

    private Permanent owner;
    private EffectPredicate predicate;
    private EffectFactory factory;
}
