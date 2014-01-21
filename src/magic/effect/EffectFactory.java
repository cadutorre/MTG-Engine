package magic.effect;

import magic.Player;
import magic.Stackable;
import magic.card.Card;
import magic.card.Permanent;

/**
 * Creates an Effect in the context of a Triggered Ability.
 *
 * At the time an ability triggers, the targets of the triggered effects are relative to
 * both the card that originated the ability, and the effect that triggered the ability.
 *
 */
public abstract class EffectFactory {
    public static EffectFactory youLoseLife(final int amount) {
        return new EffectFactory() {
            public Effect<Player> getEffect(Card source, Stackable triggeringStackable) {
                return new LoseLifeEffect(((Permanent)source).getController(), amount);
            }
        };
    }

    public abstract Effect<?> getEffect(Card source, Stackable triggeringStackable);
}
