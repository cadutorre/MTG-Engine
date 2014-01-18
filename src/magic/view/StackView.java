package magic.view;


import magic.Engine;
import magic.Stackable;
import magic.card.Card;
import magic.card.Spell;
import magic.effect.Effect;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;

public class StackView extends JPanel {

    public void update() {
        stack.clear();
        for (Stackable s : engine.getTheStack())
            stack.addElement(s);
    }

    public StackView(Engine engine) {
        this.engine = engine;
        stack = new DefaultListModel<>();
        setBorder(new TitledBorder("The Stack"));
        JList<Stackable> stackList = new JList<>(stack);
        JScrollPane scroller = new JScrollPane(stackList);
        add(scroller);
    }

    private class StackableRenderer implements ListCellRenderer<Stackable> {
        @Override
        public Component getListCellRendererComponent(JList<? extends Stackable> jList, Stackable stackable, int i, boolean b, boolean b2) {
            if (stackable instanceof Effect<?>) {
                Effect e = (Effect<?>)stackable;
                JLabel l = new JLabel(e.toString());
                l.setForeground(Color.GRAY);
                return l;
            } else if (stackable instanceof Card) {
                Card card = (Card)stackable;
                return new JLabel(card.name);
            }
            return null;
        }
    }

    private DefaultListModel<Stackable> stack;
    private Engine engine;
}
