package magic;

import magic.card.Permanent;
import magic.card.creature.Creature;

import java.util.LinkedList;
import java.util.List;

public class Player {

    public String toString() {
        return name;
    }

    public int getLife() {
        return lifeTotal;
    }

    public void adjustLife(int adjustment) {
        lifeTotal += adjustment;
    }

    public List<Creature> getCreaturesControlled() {
        return creatures;
    }

    public List<Permanent> getPermanentsControlled() {
        return permanents;
    }

    public void gainControl(Permanent p) {
        System.out.println(this + " gaining control of " + p);
        p.setController(this);
        permanents.add(p);
        if (p instanceof Creature)
            creatures.add((Creature)p);
    }

    public void loseControl(Permanent p) {
        if (p instanceof Creature)
            creatures.remove(p);
        permanents.remove(p);
    }

    public Player(String name) {
        this(name, 20);
    }

    public Player(String name, int startingLife) {
        this.name = name;
        lifeTotal = startingLife;

        permanents = new LinkedList<>();
        creatures = new LinkedList<>();
    }

    private List<Permanent> permanents;
    private List<Creature> creatures;

    private String name;
    private int lifeTotal;
}
