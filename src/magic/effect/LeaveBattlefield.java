package magic.effect;

import magic.Engine;
import magic.card.creature.Creature;

public class LeaveBattlefield implements Effect {

    public final Creature target;

    public boolean someTargetsLegal() {
        return true;
    }

    public void execute(Engine engine) {
        System.out.println(this);
        engine.getBattlefield().leaveBattlefield(target);
    }

    public String toString() {
        return target + " leaves the Battlefield";
    }

    public LeaveBattlefield(Creature target) {
        this.target = target;
    }
}
