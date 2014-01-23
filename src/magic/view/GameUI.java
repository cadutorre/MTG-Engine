package magic.view;

import magic.*;
import magic.card.Card;
import magic.card.Instant;
import magic.card.Permanent;
import magic.card.creature.Creature;
import magic.controller.PlayerController;
import magic.effect.*;
import sun.awt.OrientableFlowLayout;

import javax.swing.*;
import java.awt.*;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

public class GameUI extends JFrame implements GameStateObserver, PlayerController {

    @Override
    public void effectExecuted(Effect<?> effect) {
        if (effect instanceof EnterBattlefield) {
            EnterBattlefield enter = (EnterBattlefield)effect;
            CardView cardView = getCardView(enter.getTarget());
            battlefields.get(enter.getTarget().getController()).addCard(cardView);
        } else if (effect instanceof DrawCard) {
            DrawCard draw = (DrawCard)effect;
            for (Card c : draw.getCardsDrawn()) {
                CardView cardView = getCardView(c);
                hands.get(c.getOwner()).addCard(cardView);
            }
        } else if (effect instanceof DamageCreatureEffect) {
            DamageCreatureEffect damage = (DamageCreatureEffect)effect;
            Creature target = damage.getTarget();
            battlefields.get(target.getController()).updateCard(target);
        } else if (effect instanceof CombatDamage) {
            CombatDamage damage = (CombatDamage)effect;
            if (damage.targetsCreature()) {
                Creature target = (Creature)damage.getTarget();
                battlefields.get(target.getController()).updateCard(target);
            } else {
                status.lifeChanged();
            }
        } else if (effect instanceof LoseLifeEffect) {
            status.lifeChanged();
        } else if (effect instanceof LeaveBattlefield) {
            Permanent target = ((LeaveBattlefield)effect).getTarget();
            battlefields.get(target.getController()).removeCard(target);
        } else if (effect instanceof TapPermanentEffect) {
            Permanent target = ((TapPermanentEffect)effect).getTarget();
            CardView view = cardViews.get(target);
            view.setTapped(true);
            battlefields.get(target.getController()).updateCard(target);
        } else if (effect instanceof UntapPermanentEffect) {
            Permanent target = ((UntapPermanentEffect)effect).getTarget();
            CardView view = cardViews.get(target);
            view.setTapped(false);
            battlefields.get(target.getController()).updateCard(target);
        }
    }

    @Override
    public void turnChanged(Player player) {
        status.setPlayerTurn(player);
    }

    @Override
    public void phaseChanged(Phase phase) {
        status.setPhase(phase);
    }

    @Override
    public void stepChanged(Step step) {
         status.setStep(step);
    }

    @Override
    public void stackChanged() {
        stack.update();
    }

    @Override
    public void cardPlayed(Player p, Card c) {
        hands.get(p).removeCard(c);
    }

    @Override
    public <T> void chooseTarget(TargetedEffect<T> effect) {
        List<T> legalTargets = effect.getLegalTargets(engine);
        Object[] targets = legalTargets.toArray();
        T target = (T)JOptionPane.showInputDialog(this, effect.toString(), "Choose a target", JOptionPane.QUESTION_MESSAGE, null, targets, null);

        effect.setTarget(target);
    }

    @Override
    public boolean promptOptionalEffect(Effect<?> effect) {
        return JOptionPane.showConfirmDialog(this, "Would you like to: " + effect.toString() + "?") == JOptionPane.OK_OPTION;
    }

    @Override
    public void declareAttackers(Player attackingPlayer, Combat combat) {
        while (true) {
            List<Creature> legalAttackers = combat.getLegalAttackers();

            if (legalAttackers.isEmpty())
                return;

            Object[] legalAttackerArray = legalAttackers.toArray();
            Creature attacker = (Creature)JOptionPane.showInputDialog(this, "Choose a Creature", "Declare Attackers", JOptionPane.QUESTION_MESSAGE, null, legalAttackerArray, null);
            if (attacker == null)
                return;
            combat.addAttacker(attacker);
        }
    }

    @Override
    public void declareBlockers(Player defendingPlayer, Combat combat) {
        while (true) {
            List<Creature> legalBlockers = new LinkedList<>();
            for (Creature c : defendingPlayer.getCreaturesControlled()) {
                if (!c.isTapped() && !combat.isBlocking(c))
                    legalBlockers.add(c);
            }

            if (legalBlockers.isEmpty())
                return;

            Object[] legalBlocksArray = legalBlockers.toArray();
            Creature blocker = (Creature)JOptionPane.showInputDialog(this, "Choose a Creature", "Declare Blockers", JOptionPane.QUESTION_MESSAGE, null, legalBlocksArray, null);
            if (blocker == null)
                return;

            Object[] attackers = combat.getAttackers().toArray();
            Creature attacker = (Creature)JOptionPane.showInputDialog(this, "Choose an Attacking Creature to Block", "Declare Blockers", JOptionPane.QUESTION_MESSAGE, null, attackers, null);

            combat.addBlocker(blocker, attacker);
        }
    }

    public Stackable offerPriority(Player player, boolean canPlaySorcery) {
        System.out.println(player + " gets priority - " + canPlaySorcery);
        List<Card> legalCardList = new LinkedList<>();
        for (Card c : player.getHand()) {
            if (c.canCast(engine) && (c.isInstantSpeed() || canPlaySorcery))
                 legalCardList.add(c);
        }

        if (legalCardList.isEmpty())
            return null;

        priority.gainPriority(player, canPlaySorcery);

        // TODO to move the game along faster, use the three-second timer any time they have no Sorceries in hand
        if (canPlaySorcery)
            waitForCard(legalCardList);
        else
            waitForCard(legalCardList, 3);

        if (cardClicked != null && cardClicked instanceof Instant)
            chooseTargets((Instant) cardClicked);

        priority.losePriority();

        return cardClicked;
    }

    protected void cardClicked(Card c) {
        cardClicked = c;
    }

    protected void pass() {
        passed = true;
    }

    public GameUI(Engine engine) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (UnsupportedLookAndFeelException e) {
            e.printStackTrace();
        }

        this.engine = engine;
        stack = new StackView(engine);
        battlefields = new HashMap<>();
        cardViews = new HashMap<>();
        hands = new HashMap<>();

        getContentPane().setLayout(new BorderLayout());

        // Left side panel, with stack and priority
        JPanel left = new JPanel();
        left.setLayout(new OrientableFlowLayout(OrientableFlowLayout.VERTICAL));
        left.add(stack);
        priority = new PriorityIndicator(this);
        left.add(priority);
        getContentPane().add(left, BorderLayout.WEST);

        JPanel center = new JPanel();
        center.setLayout(new FlowLayout());
        getContentPane().add(center, BorderLayout.CENTER);
        for (Player p : engine.getPlayers()) {
            CardList hand = new CardList(p + "'s Hand", this);
            hand.setPreferredSize(new Dimension(750, CardView.HEIGHT + 70));
            center.add(hand);
            hands.put(p, hand);

            CardList creatures = new CardList(p + "'s Creatures", this);
            creatures.setPreferredSize(new Dimension(750, CardView.HEIGHT + 70));
            center.add(creatures);
            battlefields.put(p, creatures);
        }

        status = new TurnStatusView(engine);
        getContentPane().add(status, BorderLayout.NORTH);

        setDefaultCloseOperation(EXIT_ON_CLOSE);

        setExtendedState(MAXIMIZED_BOTH);
        setSize(800, 600);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private CardView getCardView(Card c) {
        if (!cardViews.containsKey(c))
            cardViews.put(c, CardView.create(c));

        return cardViews.get(c);
    }

    /**
     * This is the fall-back in case the threading is too hard for me to handle.
     */
    private Card modalForCard(List<Card> legalCardList) {
        Object[] legalCards = legalCardList.toArray();
        String prompt = "Choose a Spell to Cast";
        return (Card)JOptionPane.showInputDialog(this, prompt, "Play a Spell", JOptionPane.QUESTION_MESSAGE, null, legalCards, null);
    }

    private void highlightOptions(List<Card> options) {
        for (CardView v : cardViews.values())
            v.setEnabled(false);

        for (Card c : options)
            cardViews.get(c).setEnabled(true);

        for (CardList l : battlefields.values())
            l.updateCards();
        for (CardList l : hands.values())
            l.updateCards();
    }

    private Card waitForCard(List<Card> options, int seconds) {
        highlightOptions(options);
        cardClicked = null;
        passed = false;
        int increment = 500;
        int count = seconds*1000/increment;
        for (int i = 0; i<count; ++i) {
            priority.displayCountdown(seconds-(i*increment/1000));
            try {
                Thread.sleep(increment);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            if (passed == true) {
                cardClicked = null;
                return null;
            }
            if (cardClicked != null) {
                if (!options.contains(cardClicked))
                    cardClicked = null;
                else
                    return cardClicked;
            }
        }
        return null;
    }

    private Card waitForCard(List<Card> options) {
        highlightOptions(options);
        cardClicked = null;
        passed = false;
        while(true) {
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            if (passed == true) {
                cardClicked = null;
                return null;
            }
            if (cardClicked != null) {
                if (!options.contains(cardClicked))
                    cardClicked = null;
                else
                    return cardClicked;
            }
        }
    }

    private void chooseTargets(Instant instant) {
        instant.chooseTargets(this);
    }

    private StackView stack;
    private HashMap<Player, CardList> battlefields;
    private HashMap<Card, CardView> cardViews;
    private HashMap<Player, CardList> hands;
    private TurnStatusView status;
    private PriorityIndicator priority;

    private Card cardClicked;
    private boolean passed;

    private Engine engine;
}
