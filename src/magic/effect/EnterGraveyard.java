package magic.effect;

import magic.Zone;
import magic.card.Permanent;

public class EnterGraveyard implements Effect {

    public final Permanent target;
    public final Zone fromZone;

    public boolean targetIsLegal() {
        return true;
    }


    public void accept(EffectExecutor executor) {
        executor.execute(this);
    }

    public String toString() {
        return target + " enters the Graveyard";
    }

    public EnterGraveyard(Permanent target, Zone fromZone) {
        this.target = target;
        this.fromZone = fromZone;
    }
}
