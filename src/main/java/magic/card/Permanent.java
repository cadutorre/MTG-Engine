package magic.card;

import magic.Engine;
import magic.Player;
import magic.effect.trigger.TriggeredAbility;
import magic.mana.ManaCost;

import java.util.LinkedList;
import java.util.List;

public abstract class Permanent extends Card {

    public boolean isTapped() {
        return tapped;
    }

    public void tap() {
        tapped = true;
    }

    public void untap() {
        tapped = false;
    }

    /**
     * This Permanent must add its Triggered Abilities to the Engine
     * It must remove them when it leaves the Battlefield
     */
    public void enterBattlefield(Engine engine) {
        for (TriggeredAbility trigger : triggeredAbilities)
            engine.addTrigger(trigger);
    }

    /**
     * Removes all Triggered Abilities this Permanent had
     */
    public void leaveBattlefield(Engine engine) {
        for (TriggeredAbility trigger : triggeredAbilities)
            engine.removeTrigger(trigger);
    }

    /**
     * Adds a *printed* Triggered Ability
     * This will not immediately attach the Trigger to the Engine
     */
    public void addTriggeredAbility(TriggeredAbility trigger) {
        triggeredAbilities.add(trigger);
    }

    public List<TriggeredAbility> getTriggers() {
        return triggeredAbilities;
    }

    public Permanent(String name, ManaCost cost) {
        super(name, cost, false);

        triggeredAbilities = new LinkedList<>();
    }

    private boolean tapped;
    private LinkedList<TriggeredAbility> triggeredAbilities;
}
