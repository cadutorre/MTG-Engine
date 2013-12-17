package magic.effect;

import magic.Zone;
import magic.card.creature.Creature;

public class DamageCreatureEffect implements Effect {
    public final int amount;

    public boolean targetIsLegal() {
        return target.getZone() == Zone.BATTLEFIELD;
    }

    public Creature getTarget() {
        return target;
    }

    public void setTarget(Creature c) {
        target = c;
    }

    public void accept(EffectExecutor executor) {
        executor.execute(this);
    }

    public String toString() {
        return "Deal " + amount + " damage to " + (target == null ? "target creature" : target);
    }

    public DamageCreatureEffect(int amount) {
        this.amount = amount;
    }

    private Creature target;
}
