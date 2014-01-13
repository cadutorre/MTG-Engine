package magic.view;

import magic.Engine;
import magic.Player;
import magic.card.Card;
import magic.effect.*;
import magic.effect.target.TargetChooser;
import magic.event.EventListener;
import magic.event.GainPriority;
import magic.event.GameEvent;
import magic.event.StackUpdate;

import javax.swing.*;
import java.awt.*;
import java.util.HashMap;
import java.util.List;

public class GameUI extends JFrame implements TargetChooser, EventListener {

    @Override
    public void notifyEvent(GameEvent event) {
        System.out.println("UI animating " + event);
        if (event instanceof EnterBattlefield) {
            EnterBattlefield enter = (EnterBattlefield)event;
            CardView cardView = CardView.create(enter.getTarget());
            cardViews.put(enter.getTarget(), cardView);
            battlefields.get(enter.getTarget().getController()).addCard(cardView);
        } else if (event instanceof DrawCard) {
            DrawCard draw = (DrawCard)event;
            for (Card c : draw.getCardsDrawn()) {
                CardView cardView = CardView.create(c);
                hands.get(c.getOwner()).addCard(cardView);
            }
        } else if (event instanceof GainPriority) {
            Player priority = ((GainPriority)event).player;
            gainPriority(priority);
        } else if (event instanceof StackUpdate) {
            stack.update();
        }
    }

    @Override
    public <T> void chooseTarget(TargetedEffect<T> effect) {
        List<T> legalTargets = effect.getLegalTargets(engine);
        Object[] targets = legalTargets.toArray();
        T target = (T)JOptionPane.showInputDialog(this, effect.toString(), "Choose a target", JOptionPane.QUESTION_MESSAGE, null, targets, null);

        effect.setTarget(target);
    }

    public void gainPriority(Player player) {
        // Prompt the player to play a legal card, or pass priority

        Object[] legalCards = player.getHand().toArray();
        Card card = (Card)JOptionPane.showInputDialog(this, "Choose a Card to Play", "You have Priority", JOptionPane.QUESTION_MESSAGE, null, legalCards, null);
        if (card == null) { // When the user hits Cancel
            engine.passPriority();
        } else {
            hands.get(player).removeCard(card);
            engine.playCard(card);
        }
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

        setDefaultCloseOperation(EXIT_ON_CLOSE);

        setExtendedState(MAXIMIZED_BOTH);
        setSize(800, 600);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private StackView stack;
    private HashMap<Player, CardList> battlefields;
    private HashMap<Card, CardView> cardViews;
    private HashMap<Player, CardList> hands;
    private Engine engine;
}
