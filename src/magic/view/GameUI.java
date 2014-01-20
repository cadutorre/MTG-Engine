package magic.view;

import magic.*;
import magic.card.Card;
import magic.card.Instant;
import magic.card.Permanent;
import magic.card.Spell;
import magic.card.creature.Creature;
import magic.controller.PlayerController;
import magic.effect.*;
import magic.effect.target.TargetChooser;

import javax.swing.*;
import java.awt.*;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

public class GameUI extends JFrame implements TargetChooser, GameStateObserver, PlayerController {

    @Override
    public void effectExecuted(Effect<?> effect) {
        if (effect instanceof EnterBattlefield) {
            EnterBattlefield enter = (EnterBattlefield)effect;
            CardView cardView = CardView.create(enter.getTarget());
            cardViews.put(enter.getTarget(), cardView);
            battlefields.get(enter.getTarget().getController()).addCard(cardView);
        } else if (effect instanceof DrawCard) {
            DrawCard draw = (DrawCard)effect;
            for (Card c : draw.getCardsDrawn()) {
                CardView cardView = CardView.create(c);
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

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
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
    public void declareAttackers(Player attackingPlayer, Combat combat) {
        while (true) {
            List<Creature> legalAttackers = new LinkedList<>();
            for (Creature c : attackingPlayer.getCreaturesControlled()) {
                if (!c.isTapped() && !combat.isAttacking(c))
                    legalAttackers.add(c);
            }

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
        // Prompt the player to play a legal card, or pass priority

        List<Object> legalCardList = new LinkedList<>();
        for (Card c : player.getHand()) {
            if (c.canCast(engine) && (c.isInstantSpeed() || canPlaySorcery))
                 legalCardList.add(c);
        }

        if (legalCardList.isEmpty())
            return null;

        Object[] legalCards = legalCardList.toArray();
        String prompt = canPlaySorcery ? "Choose a Spell to Cast" : "Choose an Instant-Speed Spell to Cast";
        Spell spell = (Spell)JOptionPane.showInputDialog(this, prompt, player + " has Priority", JOptionPane.QUESTION_MESSAGE, null, legalCards, null);

        if (spell != null && spell instanceof Instant)
            chooseTargets((Instant)spell);

        return spell;
    }

    public GameUI(Engine engine) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (ClassNotFoundException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        } catch (InstantiationException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        } catch (IllegalAccessException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        } catch (UnsupportedLookAndFeelException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }

        this.engine = engine;
        stack = new StackView(engine);
        battlefields = new HashMap<>();
        cardViews = new HashMap<>();
        hands = new HashMap<>();

        getContentPane().setLayout(new BorderLayout());
        getContentPane().add(stack, BorderLayout.WEST);

        JPanel center = new JPanel();
        center.setLayout(new FlowLayout());
        getContentPane().add(center, BorderLayout.CENTER);
        for (Player p : engine.getPlayers()) {
            CardList hand = new CardList(p + "'s Hand");
            hand.setPreferredSize(new Dimension(750, CardView.HEIGHT + 70));
            center.add(hand);
            hands.put(p, hand);

            CardList creatures = new CardList(p + "'s Creatures");
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

    private void chooseTargets(Instant instant) {
        instant.chooseTargets(this);
    }

    private StackView stack;
    private HashMap<Player, CardList> battlefields;
    private HashMap<Card, CardView> cardViews;
    private HashMap<Player, CardList> hands;
    private TurnStatusView status;
    private Engine engine;
}
