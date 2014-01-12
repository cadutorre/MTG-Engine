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
        this.engine = engine;
        battlefields = new HashMap<>();
        cardViews = new HashMap<>();

        getContentPane().setLayout(new FlowLayout());

        for (Player p : engine.getPlayers()) {
            CardList pCreatures = new CardList(p + "'s Creatures");
            pCreatures.setPreferredSize(new Dimension(750, CardView.HEIGHT + 50));
            pCreatures.setSize(new Dimension(750, CardView.HEIGHT + 50));
            getContentPane().add(pCreatures);
            battlefields.put(p, pCreatures);
        }

        setDefaultCloseOperation(EXIT_ON_CLOSE);

        setSize(800, 600);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private HashMap<Player, CardList> battlefields;
    private HashMap<Card, CardView> cardViews;
    private Engine engine;
}
