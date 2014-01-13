package magic.view;


import magic.Engine;
import magic.effect.Effect;

import javax.swing.*;
import javax.swing.border.TitledBorder;

public class StackView extends JPanel {

    public void update() {
        stack.clear();
        for (Effect e : engine.getTheStack())
            stack.addElement(e);
    }

    public StackView(Engine engine) {
        this.engine = engine;
        stack = new DefaultListModel<>();
        setBorder(new TitledBorder("The Stack"));
        JList<Effect> stackList = new JList<>(stack);
        JScrollPane scroller = new JScrollPane(stackList);
        add(scroller);
    }

    private DefaultListModel<Effect> stack;
    private Engine engine;
}
