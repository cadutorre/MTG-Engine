package magic.effect;

import magic.Engine;
import magic.Zone;
import magic.card.creature.Creature;

public class DamageCreatureEffect implements Effect<Creature> {
    public final int amount;

    public boolean isLegallyTargeted(Engine engine) {
        return target.getZone() == Zone.BATTLEFIELD;
    }

    public Creature getTarget() {
        return target;
    }

    public void setTarget(Creature c) {
        target = c;
    }

    public void execute(Engine engine) {
        System.out.println(this);

        target.reduceToughness(amount);
    }

    public String toString() {
        return "Deal " + amount + " damage to " + (target == null ? "target creature" : target);
    }

    public DamageCreatureEffect(int amount) {
        this.amount = amount;
    }

    public DamageCreatureEffect(Creature target, int amount) {
        this.target = target;
        this.amount = amount;
    }

    private Creature target;
}
