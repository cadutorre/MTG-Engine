package magic.view;

import magic.Engine;
import magic.Player;
import magic.card.Card;
import magic.card.creature.Creature;
import magic.effect.*;
import magic.effect.target.TargetChooser;

import javax.swing.*;
import java.awt.*;
import java.util.HashMap;
import java.util.List;

public class GameUI extends JFrame implements TargetChooser, EffectListener {

    @Override
    public void notifyEffect(Effect effect) {
        System.out.println("UI animating " + effect);
        if (effect instanceof EnterBattlefield) {
            EnterBattlefield enter = (EnterBattlefield)effect;
            CardView cardView = CardView.create(enter.getTarget());
            cardViews.put(enter.getTarget(), cardView);
            battlefields.get(enter.getTarget().getController()).addCard(cardView);
        } else if (effect instanceof DamageCreatureEffect) {
        } else if (effect instanceof DrawCard) {
            DrawCard draw = (DrawCard)effect;
            for (Card c : draw.getCardsDrawn()) {
                CardView cardView = CardView.create(c);
                hands.get(c.getOwner()).addCard(cardView);
            }
        }
    }

    @Override
    public <T> void chooseTarget(TargetedEffect<T> effect) {
        List<T> legalTargets = effect.getLegalTargets(engine);
        Object[] targets = legalTargets.toArray();
        T target = (T)JOptionPane.showInputDialog(this, effect.toString(), "Choose a target", JOptionPane.QUESTION_MESSAGE, null, targets, null);

        effect.setTarget(target);
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
        battlefields = new HashMap<>();
        cardViews = new HashMap<>();
        hands = new HashMap<>();

        getContentPane().setLayout(new FlowLayout());

        for (Player p : engine.getPlayers()) {
            CardList hand = new CardList(p + "'s Hand");
            hand.setPreferredSize(new Dimension(750, CardView.HEIGHT + 70));
            //hand.setSize(new Dimension(750, CardView.HEIGHT + 50));
            getContentPane().add(hand);
            hands.put(p, hand);

            CardList creatures = new CardList(p + "'s Creatures");
            creatures.setPreferredSize(new Dimension(750, CardView.HEIGHT + 70));
            //creatures.setSize(new Dimension(750, CardView.HEIGHT + 50));
            getContentPane().add(creatures);
            battlefields.put(p, creatures);
        }

        setDefaultCloseOperation(EXIT_ON_CLOSE);

        setExtendedState(MAXIMIZED_BOTH);
        setSize(800, 600);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private HashMap<Player, CardList> battlefields;
    private HashMap<Card, CardView> cardViews;
    private HashMap<Player, CardList> hands;
    private Engine engine;
}
