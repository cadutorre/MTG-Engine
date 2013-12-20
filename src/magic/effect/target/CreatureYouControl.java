package magic.effect.target;

import magic.Engine;
import magic.Player;
import magic.Zone;
import magic.card.creature.Creature;

import java.util.List;

public class CreatureYouControl implements Targeter<Creature> {
    @Override
    public List<Creature> getLegalTargets(Engine engine) {
        return controller.getCreaturesControlled();
    }

    @Override
    public boolean isTargetLegal(Creature target, Engine engine) {
        return target.getZone() == Zone.BATTLEFIELD && target.getController() == controller;
    }

    /**
     * The "Controller" of a spell or ability is synonymous with "You" in the text
     */
    public void setController(Player controller) {
        this.controller = controller;
    }

    private Player controller;
}
