package magic.effect;

import magic.Engine;
import magic.Zone;
import magic.card.Permanent;

public class EnterGraveyard implements Effect {

    public final Permanent target;
    public final Zone fromZone;

    public boolean someTargetsLegal() {
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

    public EnterGraveyard(Permanent target, Zone fromZone) {
        this.target = target;
        this.fromZone = fromZone;
    }
}
