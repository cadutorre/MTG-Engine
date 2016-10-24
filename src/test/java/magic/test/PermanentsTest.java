package magic.test;

import magic.Engine;
import magic.Player;
import magic.Zone;
import magic.card.creature.Creature;
import magic.effect.EnterBattlefield;
import magic.effect.EnterGraveyard;
import magic.effect.LeaveBattlefield;
import magic.mana.ManaCost;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class PermanentsTest {

    /**
     * Test that when a Permanent enters the battlefield it is under its owner's control
     */
    @Test
    public void testControl() {
        Player playerOne = new Player("Player 1");
        Engine engine = new Engine();

        Creature pillarfieldOx = new Creature("Pillarfield Ox", new ManaCost(), 2, 4);
        pillarfieldOx.setOwner(playerOne);
        EnterBattlefield oxEnters = new EnterBattlefield();
        oxEnters.setTarget(pillarfieldOx);

        engine.executeEffect(oxEnters);

        assertEquals("Creature is under Player's control", playerOne, pillarfieldOx.getController());
        assertTrue("Player controls the Creature (as Creature)", playerOne.getCreaturesControlled().contains(pillarfieldOx));
        assertTrue("Player controls the Creature (as Permanent)", playerOne.getPermanentsControlled().contains(pillarfieldOx));
    }

    @Test
    public void testZones() {
        Engine engine = new Engine();
        Creature ox = new Creature("Pillarfield Ox", new ManaCost(), 2, 4);
        ox.setOwner(new Player("Player One"));
        EnterBattlefield oxEnters = new EnterBattlefield();
        oxEnters.setTarget(ox);
        engine.executeEffect(oxEnters);

        assertEquals("Creature is in the Battlefield Zone", Zone.BATTLEFIELD, ox.getZone());
        assertTrue("Battlefield contains the Creature (as Creature)", engine.getBattlefield().getCreatures().contains(ox));
        assertTrue("Battlefield contains the Creature (as Permanent)", engine.getBattlefield().getPermanents().contains(ox));

        engine.executeEffect(new LeaveBattlefield(ox));
        engine.executeEffect(new EnterGraveyard(ox));

        assertEquals("Creature is in the Graveyard Zone", Zone.GRAVEYARD, ox.getZone());
        assertFalse("Battlefield doesn't contain the Creature (as Creature)", engine.getBattlefield().getCreatures().contains(ox));
        assertFalse("Battlefield doesn't contain the Creature (as Permanent)", engine.getBattlefield().getPermanents().contains(ox));
    }
}
