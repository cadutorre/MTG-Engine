package magic.effect;

import magic.card.Permanent;

public class EnterBattlefield implements Effect {

    public String toString() {
        return permanent + " enters the Battlefield under its owner's control";
    }

    public void accept(EffectExecutor executor) {
        executor.execute(this);
    }

    public boolean someTargetsLegal() {
        return true;
    }

    public Permanent getPermanent() {
        return permanent;
    }

    public EnterBattlefield(Permanent permanent) {
        this.permanent = permanent;
    }

    private Permanent permanent;
}
