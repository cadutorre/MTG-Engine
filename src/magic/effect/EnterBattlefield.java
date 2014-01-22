package magic.effect;

import magic.Engine;
import magic.card.Permanent;
import magic.card.creature.Creature;

public class EnterBattlefield extends Effect<Permanent> {

    public String toString() {
        return target + " enters the Battlefield under its owner's control";
    }

    public void execute(Engine engine) {
        System.out.println(this);
        if (target instanceof Creature)
            ((Creature)target).setSummoningSickness(true);
        target.getOwner().gainControl(target);
        engine.getBattlefield().enterBattlefield(target);
        target.enterBattlefield(engine);
    }

    public boolean isLegallyTargeted(Engine engine) {
        return true;
    }

    public EnterBattlefield(Permanent p) {
        super(p);
    }

    public EnterBattlefield() {
    }
}
