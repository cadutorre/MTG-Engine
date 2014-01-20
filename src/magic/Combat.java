package magic;

import magic.card.creature.Creature;
import magic.effect.CombatDamage;
import magic.effect.TapPermanentEffect;

import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;

public class Combat {

    public void addAttacker(Creature c) {
        attackers.add(c);
        blocks.put(c, new LinkedList<Creature>());

        engine.executeEffect(new TapPermanentEffect(c));
    }

    public HashSet<Creature> getAttackers() {
        return attackers;
    }

    /**
     * All Creatures the Attacking Player controls that can participate in this Combat.
     * The Creatures must be untapped, and be unaffected by Summoning Sickness
     */
    public List<Creature> getLegalAttackers() {
        LinkedList<Creature> legalAttackers = new LinkedList<>();
        for (Creature c : engine.getActivePlayer().getCreaturesControlled()) {
            if (!c.isTapped() && !c.hasSummoningSickness())
                legalAttackers.add(c);
        }
        return legalAttackers;
    }

    public boolean isAttacking(Creature c) {
        return attackers.contains(c);
    }

    public boolean isBlocking(Creature c) {
        return blockers.contains(c);
    }

    public void addBlocker(Creature blocker, Creature attacker) {
        blockers.add(blocker);
        blocks.get(attacker).add(blocker);
    }

    public boolean isBlocked(Creature c) {
        return !blocks.get(c).isEmpty();
    }

    public void doCombatDamage() {
        for (Creature attacker : attackers) {
            Player defender = engine.playerAfter(attacker.getController());
            if (isBlocked(attacker)) {
                int damageLeft = attacker.getCurrentPower(); // Damge the Attacker will distribute among the Blockers
                for (Creature blocker : blocks.get(attacker)) {
                    if (damageLeft > 0) {
                        if (blocker == blocks.get(attacker).getLast()) {
                            engine.executeEffect(new CombatDamage(blocker, damageLeft));
                        } else {
                            int toughness = blocker.getCurrentToughness();
                            engine.executeEffect(new CombatDamage(blocker, Math.min(toughness, damageLeft)));
                            damageLeft -= toughness;
                        }
                    }
                    engine.executeEffect(new CombatDamage(attacker, blocker.getCurrentPower()));
                }
            } else {
                // No blockers, deal damage to defending Player
                engine.executeEffect(new CombatDamage(defender, attacker.getCurrentPower()));
            }
        }
    }

    public Combat(Engine engine) {
        this.engine = engine;
        attackers = new HashSet<>();
        blockers = new HashSet<>();
        blocks = new HashMap<>();
    }

    private HashSet<Creature> attackers;
    private HashSet<Creature> blockers;
    private HashMap<Creature, LinkedList<Creature>> blocks;

    private Engine engine;
}
