package magic.effect;

import magic.Engine;
import magic.Zone;
import magic.card.Permanent;

public class UntapPermanentEffect implements Effect<Permanent> {

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

        target.untap();
    }

    public String toString() {
        return target == null ?
                "Untap Target Permanent"
                : target + " Untaps";
    }

    public UntapPermanentEffect(Permanent p) {
        target = p;
    }

    public UntapPermanentEffect() {
    }

    private Permanent target;
}
