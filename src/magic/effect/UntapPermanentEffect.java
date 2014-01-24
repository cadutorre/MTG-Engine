package magic.effect;

import magic.Engine;
import magic.Zone;
import magic.card.Permanent;

public class UntapPermanentEffect extends Effect<Permanent> {

    public String toPresentTense() {
        return target + " Untaps";
    }

    public boolean isLegallyTargeted(Engine engine) {
        return target.getZone() == Zone.BATTLEFIELD;
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
        super(p);
    }

    public UntapPermanentEffect() {
    }
}
