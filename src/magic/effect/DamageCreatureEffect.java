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

        if (target.getCurrentToughness() <= 0) {
            System.out.println(" - lethal damage");

            // kill the creature
            // TODO - Maybe this shouldn't always happen immediately - for example, after a Combat Phase
            engine.executeEffect(new LeaveBattlefield(target));
            engine.executeEffect(new EnterGraveyard(target));
        } else {
            System.out.println(" - new toughness: " + target.getCurrentToughness());
        }
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
