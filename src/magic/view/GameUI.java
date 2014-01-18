package magic.view;

import magic.*;
import magic.card.Card;
import magic.card.Instant;
import magic.card.Spell;
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
        System.out.println("UI animating " + effect);
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
        }
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

    public Stackable offerPriority(Player player, boolean canPlaySorcery) {
        // Prompt the player to play a legal card, or pass priority

        List<Object> legalCardList = new LinkedList<>();
        for (Card c : player.getHand()) {
            // TODO check if the spell can be cast
            if (c.isInstantSpeed() || canPlaySorcery)
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

        status = new TurnStatusView();
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
