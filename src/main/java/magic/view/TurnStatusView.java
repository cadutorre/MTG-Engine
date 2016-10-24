package magic.view;

import java.awt.FlowLayout;

import javax.swing.JLabel;
import javax.swing.JPanel;

import magic.Engine;
import magic.Phase;
import magic.Player;
import magic.Step;

public class TurnStatusView extends JPanel {

	private static final long serialVersionUID = 5376372340502592708L;
	
	public void setPlayerTurn(Player p) {
		playerTurn.setText(p + "'s Turn");
	}

	public void setPhase(Phase phase) {
		this.phase.setText(phase.toString());
	}

	public void setStep(Step step) {
		this.step.setText(step == null ? "" : step.toString());
	}

	public void lifeChanged() {
		String lifeString = "";
		for (Player p : engine.getPlayers())
			lifeString += p + ": " + p.getLife() + " Life     ";

		playerLife.setText(lifeString);
	}

	public TurnStatusView(Engine engine) {
		this.engine = engine;
		setLayout(new FlowLayout());

		playerLife = new JLabel("");
		add(playerLife);

		playerTurn = new JLabel("");
		add(playerTurn);

		phase = new JLabel("");
		add(phase);

		step = new JLabel("");
		add(step);

		lifeChanged();
	}

	private Engine engine;
	private JLabel playerLife;
	private JLabel playerTurn;
	private JLabel phase;
	private JLabel step;
}
