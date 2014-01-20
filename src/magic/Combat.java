package magic;

import magic.card.creature.Creature;
import magic.effect.TapPermanentEffect;

import java.util.HashMap;
import java.util.HashSet;

public class Combat {

    public void addAttacker(Creature c) {
        attackers.add(c);

        engine.executeEffect(new TapPermanentEffect(c));
    }

    public boolean isAttacking(Creature c) {
        return attackers.contains(c);
    }

    public void addBlocker(Creature blocker, Creature attacker) {
        blockers.put(blocker, attacker);
    }

    public Combat(Engine engine) {
        this.engine = engine;
        attackers = new HashSet<>();
        blockers = new HashMap<>();
    }

    private HashSet<Creature> attackers;
    private HashMap<Creature, Creature> blockers;

    private Engine engine;
}
