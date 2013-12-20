package magic.effect.trigger;

import magic.Zone;
import magic.card.creature.Creature;
import magic.effect.DamageCreatureEffect;
import magic.effect.Effect;
import magic.effect.EnterGraveyard;

public abstract class EffectPredicate {
    public abstract boolean predicate(Effect e);

    public static final EffectPredicate CREATURE_DIE = new EffectPredicate() {
        public boolean predicate(Effect e) {
            if (e instanceof EnterGraveyard) {
                EnterGraveyard enter = (EnterGraveyard)e;
                return enter.getZoneEnteredFrom() == Zone.BATTLEFIELD
                    && enter.getTarget() instanceof Creature;
            }

            return false;
        }
    };

    public static EffectPredicate thisCreatureDealtDamage(final Creature creature) {
        return new EffectPredicate() {
            @Override
            public boolean predicate(Effect e) {
                return e instanceof DamageCreatureEffect
                    && ((DamageCreatureEffect)e).getTarget() == creature;
            }
        };
    }
}
