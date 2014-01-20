package magic.test;

import magic.Engine;
import magic.Zone;
import magic.card.creature.Creature;
import magic.effect.DamageCreatureEffect;
import magic.effect.TestEffect;
import magic.mana.ManaCost;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class EngineTest {
    /**
     * This tests that the stack operates in proper stack order,
     * which is "First In, Last Out".
     *
     * If a series of Effects are put on the stack and then executed,
     * the first Effect to be put on the stack should be the last to resolve.
     */
    @Test
    public void testStackOrder() {
        TestEffect.beginTesting();
        Engine engine = new Engine();

        Creature ox = new Creature("Pillarfield Ox", new ManaCost(), 2, 4);
        ox.setZone(Zone.BATTLEFIELD);

        DamageCreatureEffect shockA = new DamageCreatureEffect(2);
        shockA.setTarget(ox);
        TestEffect testShockA = new TestEffect(shockA);

        DamageCreatureEffect shockB = new DamageCreatureEffect(2);
        shockB.setTarget(ox);
        TestEffect testShockB = new TestEffect(shockB);

        engine.placeOnStack(testShockA);
        engine.placeOnStack(testShockB);
        engine.executeTheStack();

        assertTrue("First shock resolved", testShockA.hasResolved());
        assertTrue("Second shock resolved", testShockB.hasResolved());
        assertEquals("Second shock resolved first", 0, testShockB.getResolutionOrder());
        assertEquals("First shock resolved last", 1, testShockA.getResolutionOrder());
    }

    /**
     * Tests that a Creature enters the Graveyard zone after being dealt lethal damage
     */
    @Test
    public void lethalDamageGraveyard() {
        TestEffect.beginTesting();
        Engine engine = new Engine();

        Creature ox = new Creature("Pillarfield Ox", new ManaCost(), 2, 4);
        ox.setZone(Zone.BATTLEFIELD);

        DamageCreatureEffect intoTheMaw = new DamageCreatureEffect(13);
        intoTheMaw.setTarget(ox);

        engine.placeOnStack(intoTheMaw);
        engine.executeTheStack();

        assertEquals("Creature is in the Graveyard", Zone.GRAVEYARD, ox.getZone());
    }

    /**
     * Test that a Creature in a Graveyard zone cannot be dealt damage.
     * If a damage Effect legally targets a Creature but that Creature enters the Graveyard
     * before that Effect can resolve, it will not resolve.
     */
    @Test
    public void graveyardTargeting() {
        TestEffect.beginTesting();
        Engine engine = new Engine();

        Creature ox = new Creature("Pillarfield Ox", new ManaCost(), 2, 4);
        ox.setZone(Zone.BATTLEFIELD);

        DamageCreatureEffect intoTheMaw = new DamageCreatureEffect(13);
        intoTheMaw.setTarget(ox);
        TestEffect testIntoTheMaw = new TestEffect(intoTheMaw);

        engine.placeOnStack(testIntoTheMaw);
        ox.setZone(Zone.GRAVEYARD);
        engine.executeTheStack();

        assertFalse("Pillarfield Ox is not a valid target", intoTheMaw.isLegallyTargeted(engine));
        assertFalse("Into the Maw did not execute", testIntoTheMaw.hasResolved());
    }
}
