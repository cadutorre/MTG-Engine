package magic.effect;

import magic.Engine;
import magic.Zone;
import magic.card.Permanent;

public class ExilePermanent extends Effect<Permanent> {

    public void execute(Engine engine) {
        System.out.println(this);

        engine.executeEffect(new LeaveBattlefield(target));
        target.setZone(Zone.EXILE);
        // TODO put the creature in the exile zone
    }

    public boolean isLegallyTargeted(Engine engine) {
        return target.getZone() == Zone.BATTLEFIELD;
    }

    public String toString() {
        return "Exile " + (target == null ? "target permanent" : target);
    }

    public ExilePermanent(Permanent target) {
        this.target = target;
    }

    public ExilePermanent() {
    }

    private Permanent target;
}
