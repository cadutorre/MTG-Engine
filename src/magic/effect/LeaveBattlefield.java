package magic.effect;

import magic.card.creature.Creature;

public class LeaveBattlefield implements Effect {

    public final Creature target;

    public boolean someTargetsLegal() {
        return true;
    }

    public void accept(EffectExecutor executor) {
        executor.execute(this);
    }

    public String toString() {
        return target + " leaves the Battlefield";
    }

    public LeaveBattlefield(Creature target) {
        this.target = target;
    }
}
