package magic.effect.trigger;

import magic.Stackable;
import magic.Zone;
import magic.card.creature.Creature;
import magic.effect.DamageCreatureEffect;
import magic.effect.EnterGraveyard;

public abstract class EffectPredicate {
    public abstract boolean predicate(Stackable s);

    public static final EffectPredicate CREATURE_DIE = new EffectPredicate() {
        public boolean predicate(Stackable s) {
            if (s instanceof EnterGraveyard) {
                EnterGraveyard enter = (EnterGraveyard) s;
                return enter.getZoneEnteredFrom() == Zone.BATTLEFIELD
                    && enter.getTarget() instanceof Creature;
            }

            return false;
        }
    };

    public static EffectPredicate thisCreatureDealtDamage(final Creature creature) {
        return new EffectPredicate() {
            @Override
            public boolean predicate(Stackable s) {
                return s instanceof DamageCreatureEffect
                    && ((DamageCreatureEffect) s).getTarget() == creature;
            }
        };
    }
}
