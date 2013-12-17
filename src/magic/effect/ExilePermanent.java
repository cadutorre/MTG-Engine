package magic.effect;

import magic.Zone;
import magic.card.Permanent;
import magic.card.creature.Creature;

public class ExilePermanent implements Effect {

    public void accept(EffectExecutor executor) {
        executor.execute(this);
    }

    public boolean targetIsLegal() {
        return target.getZone() == Zone.BATTLEFIELD;
    }

    public void setTarget(Permanent creature) {
        target = creature;
    }

    public Permanent getTarget() {
        return target;
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
