package magic.effect.trigger;

import magic.Engine;
import magic.Stackable;
import magic.Step;
import magic.Zone;
import magic.card.Permanent;
import magic.card.creature.Creature;
import magic.effect.CreatureAttacks;
import magic.effect.DamageCreatureEffect;
import magic.effect.EnterGraveyard;
import magic.effect.StepChanged;

public abstract class EffectPredicate {

    public static final EffectPredicate CREATURE_DIE = new EffectPredicate() {
        public boolean predicate(Permanent p, Stackable s, Engine engine) {
            if (s instanceof EnterGraveyard) {
                EnterGraveyard enter = (EnterGraveyard) s;
                return enter.getZoneEnteredFrom() == Zone.BATTLEFIELD
                    && enter.getTarget() instanceof Creature;
            }

            return false;
        }
    };

    public static EffectPredicate YOUR_UPKEEP = new EffectPredicate() {
        public boolean predicate(Permanent p, Stackable s, Engine engine) {
            return (s instanceof StepChanged && ((StepChanged)s).step == Step.UPKEEP && p.getController() == engine.getActivePlayer());
        }
    };

    public static EffectPredicate thisCreatureDealtDamage() {
        return new EffectPredicate() {
            public boolean predicate(Permanent p, Stackable s, Engine engine) {
                return s instanceof DamageCreatureEffect
                    && ((DamageCreatureEffect) s).getTarget() == p;
            }
        };
    }

    public static EffectPredicate thisCreatureAttacks() {
        return new EffectPredicate() {
            public boolean predicate(Permanent p, Stackable s, Engine engine) {
                return s instanceof CreatureAttacks
                        && ((CreatureAttacks) s).getTarget() == p;
            }
        };
    }

    public abstract boolean predicate(Permanent p, Stackable s, Engine engine);
}
