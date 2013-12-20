package magic.effect;

import magic.Engine;
import magic.Zone;
import magic.card.Card;
import magic.card.Permanent;

public class EnterGraveyard implements Effect<Card> {

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

    @Override
    public void setTarget(Card target) {
        this.target = target;
        fromZone = target.getZone();
    }

    public String toString() {
        return target + " enters the Graveyard from " + fromZone;
    }

    public EnterGraveyard(Card target) {
        this.target = target;
        fromZone = target.getZone();
    }

    private Card target;
    private Zone fromZone;
}
