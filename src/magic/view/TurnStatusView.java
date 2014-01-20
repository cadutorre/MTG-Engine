package magic.view;

import magic.Phase;
import magic.Player;
import magic.Step;
import sun.awt.OrientableFlowLayout;

import javax.swing.*;

public class TurnStatusView extends JPanel {
    public void setPlayerTurn(Player p) {
         playerTurn.setText(p + "'s Turn");
    }

    public void setPhase(Phase phase) {
        this.phase.setText(phase.toString());
    }

    public void setStep(Step step) {
        this.step.setText(step.toString());
    }

    public TurnStatusView() {
        setLayout(new OrientableFlowLayout(OrientableFlowLayout.HORIZONTAL));
        playerTurn = new JLabel("");
        add(playerTurn);
        phase = new JLabel("");
        add(phase);
        step = new JLabel("");
        add(step);
    }

    private JLabel playerTurn;
    private JLabel phase;
    private JLabel step;
}
