package magic.test;

import magic.Engine;
import magic.Zone;
import magic.card.Permanent;
import magic.card.creature.Creature;
import magic.effect.*;
import magic.effect.trigger.EffectPredicate;
import magic.effect.trigger.EffectReplacer;
import magic.effect.trigger.EffectTrigger;
import magic.mana.ManaCost;

public class Main {
    public static void main(String... args) {
        Engine engine = new Engine();

        EffectReplacer exileNotDie = new EffectReplacer(EffectPredicate.CREATURE_DIE) {
            public Effect getReplacement(Effect effect) {
                return new ExilePermanent((Permanent)((EnterGraveyard)effect).getTarget());
            }
        };
        engine.addReplacement(exileNotDie);

        DamageCreatureEffect shock = new DamageCreatureEffect(2);

        Creature bear = new Creature("Bear", new ManaCost(), 2, 2);
        bear.setZone(Zone.BATTLEFIELD);
        shock.setTarget(bear);
        engine.placeOnStack(shock);
        engine.executeTheStack();

        final Creature ox = new Creature("Pillarfield Ox", new ManaCost(), 2, 4);
        ox.setZone(Zone.BATTLEFIELD);
        EffectTrigger oneOneWhenDamaged = new EffectTrigger(EffectPredicate.thisCreatureDealtDamage(ox)) {
            @Override
            public Effect getEffect() {
                return new PlaceOneOneCounter(ox, false);
            }
        };
        engine.addTrigger(oneOneWhenDamaged);

        Effect doubleShock = new MultiEffect(shock, shock);

        shock.setTarget(ox);
        engine.placeOnStack(doubleShock);
        engine.executeTheStack();
    }
}
