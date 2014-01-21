package magic.effect;

import magic.Engine;
import magic.Zone;
import magic.card.creature.Creature;

public class CreatureAttacks implements Effect<Creature> {

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
    }

    public String toString() {
        return target + " Attacks";
    }

    public CreatureAttacks() {
    }

    public CreatureAttacks(Creature target) {
        this.target = target;
    }

    private Creature target;
}
