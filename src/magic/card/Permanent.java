package magic.card;

import magic.Player;
import magic.mana.ManaCost;

public abstract class Permanent extends Card {

    public boolean isTapped() {
        return tapped;
    }

    public void tap() {
        tapped = true;
    }

    public void untap() {
        tapped = false;
    }

    public void setController(Player player) {
        controller = player;
    }

    public Player getController() {
        return controller;
    }

    public Permanent(String name, ManaCost cost) {
        super(name, cost, false);
    }

    private Player controller;
    private boolean tapped;
}
