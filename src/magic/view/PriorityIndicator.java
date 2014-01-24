package magic.view;

import magic.Player;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class PriorityIndicator extends JPanel {

    public void displayCountdown(int seconds) {
        timer.setText(seconds + "s");
    }

    public void declareAttackers(Player p) {
        cancel.setEnabled(true);
        border.setTitle("Declare Attackers");
        repaint();
    }

    public void declareBlockers(Player p) {
        cancel.setEnabled(true);
        border.setTitle("Declare Blockers");
        repaint();
    }

    public void gainPriority(Player p, boolean sorcery) {
        border.setTitle("Play a " + (sorcery ? "Spell" : "Instant"));
        repaint();
        cancel.setEnabled(true);
    }

    public void done() {
        border.setTitle("No Action");
        repaint();
        cancel.setEnabled(false);
        timer.setText("0");
        timer.setEnabled(false);
    }

    public PriorityIndicator(final GameUI ui) {
        this.ui = ui;
        border = new TitledBorder("");
        setBorder(border);

        cancel = new JButton("Pass");
        timer = new JLabel("");

        cancel.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent actionEvent) {
                ui.cancel();
            }
        });

        add(timer);
        add(cancel);

        done();

        setPreferredSize(new Dimension(200, 100));
    }

    private TitledBorder border;
    private JButton cancel;
    private JLabel timer;
    private GameUI ui;
}
