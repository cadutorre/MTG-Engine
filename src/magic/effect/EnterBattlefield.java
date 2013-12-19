package magic.effect;

import magic.Engine;
import magic.Zone;
import magic.card.Permanent;

public class EnterBattlefield implements Effect {

    public String toString() {
        return permanent + " enters the Battlefield under its owner's control";
    }

    public void execute(Engine engine) {
        System.out.println(this);
        permanent.setZone(Zone.BATTLEFIELD);
        permanent.getOwner().gainControl(permanent);
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
