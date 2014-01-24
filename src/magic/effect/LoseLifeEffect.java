package magic.effect;

import magic.Engine;
import magic.Player;

/**
 * Direct life loss, not combat damage.
 */
public class LoseLifeEffect extends Effect<Player> {
    public final int amount;

    public String toPresentTense() {
        return target + " Loses " + amount + " Life";
    }

    public boolean isLegallyTargeted(Engine engine) {
        return true;
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
        super(target);
        this.amount = amount;
    }
}
