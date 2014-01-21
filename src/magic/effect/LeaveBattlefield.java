package magic.effect;

import magic.Engine;
import magic.card.Permanent;

public class LeaveBattlefield implements Effect<Permanent> {

    public boolean isLegallyTargeted(Engine engine) {
        return true;
    }

    public void setTarget(Permanent p) {
        target = p;
    }

    public Permanent getTarget() {
        return target;
    };

    public void execute(Engine engine) {
        System.out.println(this);
        engine.getBattlefield().leaveBattlefield(target);
        target.leaveBattlefield(engine);
    }

    public String toString() {
        return target + " leaves the Battlefield";
    }

    public LeaveBattlefield(Permanent target) {
        this.target = target;
    }

    public LeaveBattlefield() {
    }

    private Permanent target;
}
