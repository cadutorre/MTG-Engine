package magic.view;

import magic.effect.Effect;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;

public class EventLog extends JPanel {

    public void addEffect(Effect<?> effect) {
        list.addElement(effect.toPresentTense());
    }

    public EventLog() {
        setBorder(new TitledBorder("Game Events"));

        list = new DefaultListModel<>();
        JList<String> listView = new JList<>(list);
        listView.setPreferredSize(new Dimension(300, 400));
        JScrollPane scroller = new JScrollPane(listView);
        add(scroller);
        setLayout(new FlowLayout());
    }

    private DefaultListModel<String> list;
}
