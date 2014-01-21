package magic.effect;

import magic.Engine;
import magic.Player;
import magic.Zone;
import magic.card.creature.Creature;

/**
 * Direct life loss, not combat damage.
 */
public class LoseLifeEffect implements Effect<Player> {
    public final int amount;

    public boolean isLegallyTargeted(Engine engine) {
        return true;
    }

    public Player getTarget() {
        return target;
    }

    public void setTarget(Player player) {
        target = player;
    }

    public void execute(Engine engine) {
        System.out.println(this);

        target.adjustLife(-amount);
    }

    public String toString() {
        return (target == null ? "Target Player" : target) + " Loses " + amount + " Life";
    }

    public LoseLifeEffect(int amount) {
        this.amount = amount;
    }

    public LoseLifeEffect(Player target, int amount) {
        this.target = target;
        this.amount = amount;
    }

    private Player target;
}
