package magic;

import magic.card.Permanent;
import magic.card.creature.Creature;
import magic.effect.*;
import magic.effect.trigger.EffectReplacer;
import magic.effect.trigger.EffectTrigger;

import java.util.LinkedList;
import java.util.Stack;

public class Engine extends EffectExecutor {

    public void placeOnStack(Effect effect) {
        theStack.push(effect);
    }

    /**
     * Executes the top Effect on the Stack it is empty.
     * Triggered Effects will be added to the Stack as they are triggered.
     * This is only a testing method, because it does not allow for priority passing and putting
     * instant-speed items on the stack.
     */
    public void executeTheStack() {
        while (!theStack.isEmpty()) {
            Effect top = theStack.pop();

            if (top.someTargetsLegal()) {
                executeEffect(top);
            } else {
                System.out.println("Not resolving: " + top);
            }

            // TODO do state-based action happen here?
        }
    }

    public void addReplacement(EffectReplacer replacer) {
        replacers.add(replacer);
    }

    public void addTrigger(EffectTrigger trigger) {
        triggers.add(trigger);
    }

    public Engine() {
        replacers = new LinkedList<>();
        triggers = new LinkedList<>();
        theStack = new Stack<>();
    }

    protected void execute(DamageCreatureEffect effect) {
        System.out.println(effect);

        Creature target = effect.getTarget();
        target.reduceToughness(effect.amount);

        if (target.getCurrentToughness() <= 0) {
            System.out.println(" - lethal damage");
            // kill the creature
            executeEffect(new LeaveBattlefield(target));
            executeEffect(new EnterGraveyard(target, Zone.BATTLEFIELD));
        } else {
            System.out.println(" - new toughness: " + target.getCurrentToughness());
        }
    }

    protected void execute(LeaveBattlefield leave) {
        System.out.println(leave);
    }

    protected void execute(EnterGraveyard enter) {
        System.out.println(enter);

        enter.target.setZone(Zone.GRAVEYARD);
        // TODO put the creature in its owner's Graveyard
    }

    protected void execute(ExilePermanent exile) {
        System.out.println(exile);

        exile.getTarget().setZone(Zone.EXILE);
        // TODO put the creature in the exile zone
    }

    protected void execute(PlaceOneOneCounter place) {
        System.out.println(place);
        place.getTarget().placeOneOneCounters(place.counter);
    }

    protected void execute(MultiEffect effect) {
        for (Effect e : effect.getEffects()) {
            if (e.someTargetsLegal())
                executeEffect(e);
        }
    }

    @Override
    protected void execute(EnterBattlefield effect) {
        System.out.println(effect);
        Permanent p = effect.getPermanent();
        p.setZone(Zone.BATTLEFIELD);
        p.getOwner().gainControl(p);
    }

    private void executeEffect(Effect effect) {
        // See if there is a replacement for this effect
        for (EffectReplacer r : replacers) {
            if (r.getPredicate().predicate(effect)) {
                Effect replacement = r.getReplacement(effect);
                executeEffect(replacement);
                return;
            }
        }

        // Execute the effect
        effect.accept(this);

        // Identify any triggers from this effect
        LinkedList<Effect> triggeredEffects = new LinkedList<>();
        for (EffectTrigger trigger : triggers) {
            if (trigger.getPredicate().predicate(effect)) {
                triggeredEffects.add(trigger.getEffect());
            }
        }

        if (!triggeredEffects.isEmpty()) {
            System.out.println("Triggered effects:");
            for (Effect e : triggeredEffects) {
                System.out.println("\t" + e);
                placeOnStack(e);
            }
        }
    }

    private LinkedList<EffectReplacer> replacers;
    private LinkedList<EffectTrigger> triggers;
    private Stack<Effect> theStack;
}
