package magic;

import magic.card.Permanent;
import magic.card.creature.Creature;

import java.util.LinkedList;
import java.util.List;

public class Battlefield {
    public List<Creature> getCreatures() {
        return creatures;
    }

    public List<Permanent> getPermanents() {
        return permanents;
    }

    public void enterBattlefield(Permanent permanent) {
        permanent.setZone(Zone.BATTLEFIELD);
        permanents.add(permanent);
        if (permanent instanceof Creature)
            creatures.add((Creature)permanent);
    }

    public void leaveBattlefield(Permanent permanent) {
        permanents.remove(permanent);
        if (permanent instanceof Creature)
            creatures.remove(permanent);
    }

    public Battlefield() {
        permanents = new LinkedList<>();
        creatures = new LinkedList<>();
    }

    private List<Creature> creatures;
    private List<Permanent> permanents;
}
