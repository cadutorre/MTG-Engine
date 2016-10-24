package magic.effect.trigger;

import magic.Engine;
import magic.Stackable;
import magic.Step;
import magic.Zone;
import magic.card.Permanent;
import magic.card.creature.Creature;
import magic.effect.*;

public abstract class EffectPredicate {

    public static final EffectPredicate CREATURE_DIE = new EffectPredicate() {
        public boolean predicate(Permanent source, Stackable s, Engine engine) {
            if (s instanceof EnterGraveyard) {
                EnterGraveyard enter = (EnterGraveyard) s;
                return enter.getZoneEnteredFrom() == Zone.BATTLEFIELD
                    && enter.getTarget() instanceof Creature;
            }

            return false;
        }
    };

    public static EffectPredicate YOUR_UPKEEP = new EffectPredicate() {
        public boolean predicate(Permanent source, Stackable s, Engine engine) {
            return (s instanceof StepChanged && ((StepChanged)s).getTarget() == Step.UPKEEP && source.getController() == engine.getActivePlayer());
        }
    };

    public static EffectPredicate THIS_CREATURE_DEALT_DAMAGE = new EffectPredicate() {
        public boolean predicate(Permanent source, Stackable s, Engine engine) {
            return s instanceof DamageCreatureEffect
                    && ((DamageCreatureEffect) s).getTarget() == source;
        }
    };

    public static EffectPredicate THIS_CREATURE_ATTACKS = new EffectPredicate() {
        public boolean predicate(Permanent source, Stackable s, Engine engine) {
            return s instanceof CreatureAttacks
                    && ((CreatureAttacks) s).getTarget() == source;
        }
    };

    /**
     * Matches a DrawCard Effect when the target is the Controller of the source card
     */
    public static EffectPredicate OPPONENT_DRAWS_CARD = new EffectPredicate() {
        public boolean predicate(Permanent source, Stackable s, Engine engine) {
            return s instanceof DrawCard
                    && ((DrawCard)s).getTarget() != source.getController();
        }
    };

    /**
     *
     * @param source The Permanent this predicate is "printed" on - so, the context for the meaning of "You", etc
     * @param s The Stackable to be predicated
     * @param engine
     */
    public abstract boolean predicate(Permanent source, Stackable s, Engine engine);
}
