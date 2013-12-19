package magic.test;

import magic.Engine;
import magic.Player;
import magic.Zone;
import magic.card.creature.Creature;
import magic.effect.EnterBattlefield;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class PermanentsTest {

    /**
     * Test that when a Permanent enters the battlefield it is under its owner's control
     */
    @Test
    public void testControl() {
        Player playerOne = new Player("Player 1");
        Engine engine = new Engine();

        Creature pillarfieldOx = new Creature("Pillarfield Ox", 2, 4);
        pillarfieldOx.setOwner(playerOne);

        engine.placeOnStack(new EnterBattlefield(pillarfieldOx));
        engine.executeTheStack();

        assertEquals("Creature is in the Battlefield Zone", Zone.BATTLEFIELD, pillarfieldOx.getZone());
        assertEquals("Creature is under Player's control", playerOne, pillarfieldOx.getController());
        assertTrue("Player controls the Creature (as Creature)", playerOne.getCreaturesControlled().contains(pillarfieldOx));
        assertTrue("Player controls the Creature (as Permanent)", playerOne.getPermanentsControlled().contains(pillarfieldOx));
    }
}
