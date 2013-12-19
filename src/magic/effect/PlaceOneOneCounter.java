package magic.effect;

import magic.Engine;
import magic.Zone;
import magic.card.creature.Creature;

public class PlaceOneOneCounter implements Effect {

    @Override
    public void execute(Engine engine) {
        System.out.println(this);
        target.placeOneOneCounters(counter);
    }

    public boolean someTargetsLegal() {
        return target.getZone() == Zone.BATTLEFIELD;
    }

    public Creature getTarget() {
        return target;
    }

    public void setTarget(Creature target) {
        this.target = target;
    }

    public String toString() {
        return "Place a " +
                (counter == 1 ? "+1/+1" : "-1/-1")
                + " counter on "
                + (target == null ? "target creature" : target);
    }

    public PlaceOneOneCounter(boolean plus) {
        counter = plus ? 1 : -1;
    }

    public PlaceOneOneCounter(Creature target) {
        this(target, true);
    }

    public PlaceOneOneCounter(Creature target, boolean plus) {
        this(plus);
        this.target = target;
    }

    private Creature target;
    private int counter;
}
