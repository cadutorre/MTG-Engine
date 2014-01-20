package magic;

import magic.card.Card;
import magic.controller.GameController;
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
     * as in state-based, turn-based, or special actions.
     *
     */
    public void executeEffect(Stackable stackable) {
        if (stackable instanceof Effect) {
            Effect effect = (Effect<?>)stackable;
            // See if there is a replacement for this effect
            for (EffectReplacer r : replacers) {
                if (r.getPredicate().predicate(stackable)) {
                    Effect replacement = r.getReplacement(effect);
                    executeEffect(replacement);
                    return;
                }
            }
        }

        // Execute the effect
        stackable.execute(this);

        if (stackable instanceof Effect) {
            Effect effect = (Effect<?>)stackable;
            for (GameStateObserver o : observers)
                o.effectExecuted(effect);
        }

        // Identify any triggers from this effect
        LinkedList<Effect> triggeredEffects = new LinkedList<>();
        for (EffectTrigger trigger : triggers) {
            if (trigger.getPredicate().predicate(stackable)) {
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

    public void placeOnStack(Stackable stackable) {
        theStack.push(stackable);

        for (GameStateObserver o : observers)
            o.stackChanged();
    }

    /**
     * This method should only be called in Unit Tests
     */
    public void executeTheStack() {
        while (!theStack.isEmpty())
            popTheStack();;
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

    public Player getActivePlayer() {
        return activePlayer;
    }

    public void setActivePlayer(Player p) {
        activePlayer = p;

        for (GameStateObserver o : observers)
            o.turnChanged(p);
    }

    public Player playerAfter(Player p) {
        for (int i = 0; i<players.length-1; ++i) {
            if (p == players[i])
                return players[i+1];
        }
        return players[0];
    }

    public void setPhase(Phase phase) {
        currentPhase = phase;
        for (GameStateObserver o : observers)
            o.phaseChanged(phase);
    }

    public void setStep(Step step) {
        currentStep = step;
        for (GameStateObserver o : observers)
            o.stepChanged(step);
    }

    public void setCombat(Combat combat) {
        this.combat = combat;
    }

    public Combat getCombat() {
        return combat;
    }

    public Stack<Stackable> getTheStack() {
        return theStack;
    }

    public void addStateObserver(GameStateObserver observer) {
        observers.add(observer);
    }

    public void beginGame() {
        for (Player p : players)
            executeEffect(new DrawCard(p, 7));

        setActivePlayer(players[0]);
        Phase.BEGINNING.doPhase(this);
    }

    public void setController(GameController controller) {
        this.controller = controller;
    }

    public GameController getController() {
        return controller;
    }

    /**
     * Performs the entire process of passing Priority between Players, and executing the Stack
     * where appropriate.
     *
     * Starting with the Active Player, each Player receives Priorty in turn. At that time the Player may
     * place as many Stackables on the Stack as they wish, until they pass Priority to the next Player.
     *
     * When all Players pass Priority in a row, the Stack is popped - or, if the stack is empty, the process
     * is complete.
     */
    public void passPriority() {
        // proceeding in APNAP order, offer priority to each player
        int numPasses = 0; // the number of players that have passed in a row

        while (true) {
            Player priorityPlayer = activePlayer;
            while (numPasses < players.length) {
                // Give this player a chance to put spells and abilities on the stack
                if (offerPriority(priorityPlayer)) {
                    numPasses = 0; // a Player accepted Priority
                    while (offerPriority(priorityPlayer));
                }

                ++ numPasses;
                priorityPlayer = playerAfter(priorityPlayer);
            }

            if (!theStack.isEmpty()) {
                popTheStack();
            } else {
                System.out.println("All Players passed priority when the Stack was empty");
                break;
            }
        }
    }

    public Engine(Player... players) {
        this.players = players;
        observers = new LinkedList<>();
        replacers = new LinkedList<>();
        triggers = new LinkedList<>();
        theStack = new Stack<>();
        battlefield = new Battlefield();
    }

    /**
     * Give a Player a chance to put a Spell or Ability onto the Stack.
     * If the Player does not pass priority the Stack will be updated by that Player
     * @return whether the Player accepts Priority and plays a Spell or Ability
     */
    private boolean offerPriority(Player p) {
        boolean canPlaySorcerySpeed =
                theStack.isEmpty()
             && p == activePlayer
             && (currentPhase == Phase.FIRST_MAIN_PHASE || currentPhase == Phase.SECOND_MAIN_PHASE);

        Stackable s = controller.offerPriority(p, canPlaySorcerySpeed);

        if (s == null) // Player declines to activate anything
            return false;

        // Note that a Card would only show up here if it was played from the player's Hand...
        // If a spell or ability allows a player to play card from elsewhere, that would be
        // a nested Effect to some other Card or Effect
        if (s instanceof Card) {
            p.leaveHand((Card)s);
            for (GameStateObserver o : observers)
                o.cardPlayed(p, (Card)s);
        }

        placeOnStack(s);
        return true;
    }

    private void popTheStack() {
        Stackable top = theStack.pop();

        for (GameStateObserver o : observers)
            o.stackChanged();

        // TODO - if a spell or ability cannot resolve because it is illegally targeted, is it still replaced by replacement effects?

        if (top.isLegallyTargeted(this)) {
            executeEffect(top);
        } else {
            System.out.println("Not resolving: " + top);
        }

        // TODO do state-based action happen here?
    }

    private GameController controller;

    private Phase currentPhase;
    private Step currentStep;
    private Combat combat;
    private Player activePlayer;
    private Player[] players;
    private LinkedList<GameStateObserver> observers;
    private LinkedList<EffectReplacer> replacers;
    private LinkedList<EffectTrigger> triggers;
    private Stack<Stackable> theStack;
    private Battlefield battlefield;
}
