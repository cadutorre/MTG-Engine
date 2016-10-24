package magic.effect;

import magic.Engine;
import magic.Zone;
import magic.card.Card;
import magic.card.Permanent;

public class EnterGraveyard extends Effect<Card> {

    @Override
    public String toPresentTense() {
        return target + " Enters the Graveyard";
    }

    public Card getTarget() {
        return target;
    }

    public Zone getZoneEnteredFrom() {
        return fromZone;
    }

    public boolean isLegallyTargeted(Engine engine) {
        return true;
    }

    public void execute(Engine engine) {
        // TODO - should this trigger its own LeaveBattlefield effect?
        System.out.println(this);
        target.setZone(Zone.GRAVEYARD);
    }

    public String toString() {
        return target + " enters the Graveyard from " + fromZone;
    }

    public EnterGraveyard(Card target) {
        super(target);
        fromZone = target.getZone();
    }

    private Zone fromZone;
}
