package magic.test;

import magic.Engine;
import magic.Zone;
import magic.card.creature.Creature;
import magic.effect.DamageCreatureEffect;
import magic.effect.trigger.EffectPredicate;
import magic.effect.trigger.TestTrigger;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class TriggerTest {
    @Test
    public void testDamageTriggers() {
        Engine engine = new Engine();

        Creature ox = new Creature("Pillarfield Ox", 2, 4);
        ox.setZone(Zone.BATTLEFIELD);

        Creature bear = new Creature("Some Bear", 2, 2);
        bear.setZone(Zone.BATTLEFIELD);

        TestTrigger testWhenOxDamaged = new TestTrigger(EffectPredicate.thisCreatureDealtDamage(ox));
        engine.addTrigger(testWhenOxDamaged);
        TestTrigger testWhenBearDamaged = new TestTrigger(EffectPredicate.thisCreatureDealtDamage(bear));
        engine.addTrigger(testWhenBearDamaged);

        engine.placeOnStack(new DamageCreatureEffect(ox, 2));
        engine.placeOnStack(new DamageCreatureEffect(ox, 2));
        engine.placeOnStack(new DamageCreatureEffect(ox, 2));
        engine.placeOnStack(new DamageCreatureEffect(bear, 2));

        engine.executeTheStack();
        assertEquals("Two instances of damage dealt to Pillarfield Ox", 2, testWhenOxDamaged.getNumTimesTriggered());
        assertEquals("One instance of damage dealt to Some Bear", 1, testWhenBearDamaged.getNumTimesTriggered());
    }
}
