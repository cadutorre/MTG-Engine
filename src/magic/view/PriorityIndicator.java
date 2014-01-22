package magic.view;

import magic.Player;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class PriorityIndicator extends JPanel {

    public void gainPriority(Player p, boolean sorcery) {
        hasPriority.setEnabled(true);
        cancel.setEnabled(true);
        hasPriority.setText(p + (sorcery ? " may play Sorceries" : " may play Instants"));
    }

    public void losePriority() {
        hasPriority.setEnabled(false);
        cancel.setEnabled(false);
    }

    public PriorityIndicator(final GameUI ui) {
        this.ui = ui;
        setBorder(new TitledBorder("Priority"));

        hasPriority = new JLabel();
        cancel = new JButton("Pass");
        hasPriority.setEnabled(false);
        cancel.setEnabled(false);
        cancel.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent actionEvent) {
                ui.pass();
            }
        });

        add(hasPriority);
        add(cancel);

        setPreferredSize(new Dimension(200, 100));
    }

    private JLabel hasPriority;
    private JButton cancel;
    private GameUI ui;
}
