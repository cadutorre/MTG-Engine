package magic.effect;

import magic.Engine;
import magic.Zone;
import magic.card.Permanent;

public class TapPermanentEffect implements Effect<Permanent> {

    public boolean isLegallyTargeted(Engine engine) {
        return target.getZone() == Zone.BATTLEFIELD;
    }

    public Permanent getTarget() {
        return target;
    }

    public void setTarget(Permanent p) {
        target = p;
    }

    public void execute(Engine engine) {
        System.out.println(this);

        target.tap();
    }

    public String toString() {
        return target == null ?
                "Tap Target Permanent"
                : target + " taps";
    }

    public TapPermanentEffect(Permanent p) {
        target = p;
    }

    public TapPermanentEffect() {
    }

    private Permanent target;
}
