package magic.controller;

import magic.Combat;
import magic.Player;
import magic.Stackable;

public interface PlayerController {

    /**
     * Offers a Player's controller a chance to put a Spell or Ability on the stack.
     *
     * This PlayerController must provide the Spell or Ability with legal Targets,
     * and be sure that it can be activated legally
     */
    public Stackable offerPriority(Player player, boolean canPlaySorcery);

    public void declareAttackers(Player p, Combat c);

    public void declareBlockers(Player p, Combat c);
}
