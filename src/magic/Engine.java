package magic;

import magic.card.Card;
import magic.card.Instant;
import magic.card.creature.Creature;
import magic.effect.*;
import magic.effect.trigger.EffectReplacer;
import magic.effect.trigger.EffectTrigger;
import magic.event.EventListener;
import magic.event.GainPriority;

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
        for (EventListener l : listeners)
            l.notifyEvent(effect);

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

    public void playCard(Card c) {
        c.getOwner().leaveHand(c);
        if (c instanceof Creature) {
            Creature creature = (Creature)c;
            placeOnStack(new EnterBattlefield(creature));
        } else if (c instanceof Instant) {
            Instant instant = (Instant)c;
            placeOnStack(instant);
        }

        // Active player gains priority again
        for (EventListener l : listeners)
            l.notifyEvent(new GainPriority(activePlayer));
    }

    public void passPriority() {
        /* TODO either give priority to the next player, or execute the top of the stack
        once all players have passed; or end the phase if the stack is empty */
        executeTheStack();
    }

    /**
     * TODO - this should be private, but for now testing will be easier if we can initiate this
     */
    public void mainPhase(Player player) {
        currentPhase = Phase.FIRST_MAIN_PHASE;
        activePlayer = player;

        // The active Player gains Priority on their main phase
        for (EventListener l : listeners)
            l.notifyEvent(new GainPriority(player));
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

    public Player[] getPlayers() {
        return players;
    }

    public void addEffectListener(EventListener l) {
        listeners.add(l);
    }

    public void beginGame() {
        for (Player player : players)
            executeEffect(new DrawCard(player, 7));
    }

    public Engine(Player... players) {
        this.players = players;
        listeners = new LinkedList<>();
        replacers = new LinkedList<>();
        triggers = new LinkedList<>();
        theStack = new Stack<>();
        battlefield = new Battlefield();
    }

    private Phase currentPhase;
    private Player activePlayer;
    private Player[] players;
    private LinkedList<EventListener> listeners;
    private LinkedList<EffectReplacer> replacers;
    private LinkedList<EffectTrigger> triggers;
    private Stack<Effect> theStack;
    private Battlefield battlefield;
}
