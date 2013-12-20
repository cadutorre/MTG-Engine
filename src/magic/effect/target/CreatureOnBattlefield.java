package magic.effect.target;

import magic.Engine;
import magic.Zone;
import magic.card.creature.Creature;

import java.util.List;

public class CreatureOnBattlefield implements Targeter<Creature> {

    @Override
    public List<Creature> getLegalTargets(Engine engine) {
        return engine.getBattlefield().getCreatures();
    }

    @Override
    public boolean isTargetLegal(Creature target, Engine engine) {
        return target.getZone() == Zone.BATTLEFIELD;
    }
}
