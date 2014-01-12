package magic.effect;

import magic.Engine;
import magic.card.Permanent;

public class EnterBattlefield implements Effect<Permanent> {

    public String toString() {
        return permanent + " enters the Battlefield under its owner's control";
    }

    public void execute(Engine engine) {
        System.out.println(this);
        permanent.getOwner().gainControl(permanent);
        engine.getBattlefield().enterBattlefield(permanent);
    }

    public boolean isLegallyTargeted(Engine engine) {
        return true;
    }

    public Permanent getTarget() {
        return permanent;
    }

    public void setTarget(Permanent p) {
        permanent = p;
    }

    public EnterBattlefield(Permanent p) {
        permanent = p;
    }

    public EnterBattlefield() {
    }

    private Permanent permanent;
}
