package magic.test;

import magic.Engine;
import magic.Player;
import magic.card.Instant;
import magic.card.creature.Creature;
import magic.effect.target.TestTargeter;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class SpellTest {
    @Test
    public void testDamageInstant() {
        Engine engine = new Engine();
        Player playerOne = new Player("Player One");
        TestTargeter testTargets = new TestTargeter();
        Instant lightning = Instant.getLightningBolt(playerOne);
        Creature ox = new Creature("Pillarfield Ox", 2, 4);

        assertFalse("Card in hand is not a valid target", lightning.canCast(engine));

        engine.getBattlefield().enterBattlefield(ox);
        assertTrue("Creature can be targeted", lightning.canCast(engine));

        // Simulate user targeting Ox when casting
        testTargets.setTarget(lightning.getEffects()[0], ox);
        lightning.chooseTargets(testTargets);

        engine.placeOnStack(lightning);
        engine.executeTheStack();
        assertEquals("Creature took the damage", 1, ox.getCurrentToughness());
    }
}
