package magic.view;

import magic.Phase;
import magic.Player;
import sun.awt.OrientableFlowLayout;

import javax.swing.*;
import java.awt.*;

public class TurnStatusView extends JPanel {
    public void setPlayerTurn(Player p) {
         playerTurn.setText(p + "'s Turn");
    }

    public void setPhase(Phase phase) {
        this.phase.setText(phase.toString());
    }

    public TurnStatusView() {
        setLayout(new OrientableFlowLayout(OrientableFlowLayout.HORIZONTAL));
        playerTurn = new JLabel("");
        add(playerTurn);
        phase = new JLabel("");
        add(phase);
    }

    private JLabel playerTurn;
    private JLabel phase;
}
