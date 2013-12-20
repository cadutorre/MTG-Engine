package magic.effect;

import magic.Engine;
import magic.card.Permanent;

public class EnterBattlefield implements Effect<Permanent> {

    public String toString() {
        return permanent + " enters the Battlefield under its owner's control";
    }

    public void execute(Engine engine) {
        System.out.println(this);
        engine.getBattlefield().enterBattlefield(permanent);
        permanent.getOwner().gainControl(permanent);
    }

    public boolean isLegallyTargeted(Engine engine) {
        return true;
    }

    public void setTarget(Permanent p) {
        permanent = p;
    }

    private Permanent permanent;
}
