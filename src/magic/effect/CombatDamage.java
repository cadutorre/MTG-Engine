package magic.effect;

import magic.Engine;
import magic.Player;
import magic.Zone;
import magic.card.creature.Creature;

public class CombatDamage extends Effect<Object> {
    public final int amount;

    @Override
    public String toPresentTense() {
        return source + " Deals " + amount + " Combat Damage to " + getTarget();
    }

    public boolean targetsCreature() {
        return playerTarget == null;
    }

    public boolean isLegallyTargeted(Engine engine) {
        return creatureTarget == null || creatureTarget.getZone() == Zone.BATTLEFIELD;
    }

    @Override
    public Object getTarget() {
        return creatureTarget == null ? playerTarget : creatureTarget;
    }

    @Override
    public void setTarget(Object o) {
        if (o instanceof Player)
            playerTarget = (Player)o;
        else if (o instanceof Creature)
            creatureTarget = (Creature)o;
    }

    public void execute(Engine engine) {
        System.out.println(this);

        if (creatureTarget != null)
            creatureTarget.reduceToughness(amount);
        else
            playerTarget.adjustLife(-amount);
    }

    public String toString() {
        return amount + " Combat Damage to " + (creatureTarget == null ? playerTarget : creatureTarget);
    }

    public CombatDamage(Creature source, Creature target, int amount) {
        this.source = source;
        creatureTarget = target;
        this.amount = amount;
    }

    public CombatDamage(Creature source, Player target, int amount) {
        this.source = source;
        playerTarget = target;
        this.amount = amount;
    }

    private Creature source;
    private Creature creatureTarget;
    private Player playerTarget;
}
