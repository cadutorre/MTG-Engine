package magic.effect;

import magic.Engine;
import magic.Zone;
import magic.card.creature.Creature;

public class DamageCreatureEffect extends Effect<Creature> {
    public final int amount;

    @Override
    public String toPresentTense() {
        return target + " is Dealt " + amount + " Damage";
    }

    public boolean isLegallyTargeted(Engine engine) {
        return target.getZone() == Zone.BATTLEFIELD;
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
}
