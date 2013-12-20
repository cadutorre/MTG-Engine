package magic;

import magic.effect.*;
import magic.effect.trigger.EffectReplacer;
import magic.effect.trigger.EffectTrigger;

import java.util.LinkedList;
import java.util.Stack;

public class Engine {

    /**
     * Executes an effect immediately, without using the stack.
     *
     * Only call this function when you're sure the action should happen immediately,
     * as in state-based or turn-based actions.
     *
     */
    public void executeEffect(Effect effect) {
        // See if there is a replacement for this effect
        for (EffectReplacer r : replacers) {
            if (r.getPredicate().predicate(effect)) {
                Effect replacement = r.getReplacement(effect);
                executeEffect(replacement);
                return;
            }
        }

        // Execute the effect
        effect.execute(this);

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

    public void placeOnStack(Effect effect) {
        theStack.push(effect);
    }

    /**
     * Executes the top Effect on the Stack until it is empty.
     * Triggered Effects will be added to the Stack as they are triggered.
     * This is only a testing method, because it does not allow for priority passing and putting
     * instant-speed items on the stack.
     */
    public void executeTheStack() {
        while (!theStack.isEmpty()) {
            Effect top = theStack.pop();

            // TODO - if a spell or ability cannot resolve because it is illegally targeted, is it still replaced by replacement effects?

            if (top.isLegallyTargeted(this)) {
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

    public Battlefield getBattlefield() {
        return battlefield;
    }

    public Engine() {
        replacers = new LinkedList<>();
        triggers = new LinkedList<>();
        theStack = new Stack<>();
        battlefield = new Battlefield();
    }

    private LinkedList<EffectReplacer> replacers;
    private LinkedList<EffectTrigger> triggers;
    private Stack<Effect> theStack;
    private Battlefield battlefield;
}
