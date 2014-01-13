package magic;

import magic.card.Card;
import magic.card.Library;
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

    public void setLibrary(Library library) {
        this.library = library;
    }

    public Card[] draw(int cards) {
        Card[] drawn = new Card[cards];
        for (int i = 0; i<cards; ++i) {
            Card c = library.draw();
            drawn[i] = c;
            hand.add(c);
        }
        return drawn;
    }

    public List<Card> getHand() {
        return hand;
    }

    public void leaveHand(Card c) {
        hand.remove(c);
    }

    public Player(String name) {
        this(name, 20);
    }

    public Player(String name, int startingLife) {
        this.name = name;
        lifeTotal = startingLife;

        hand = new LinkedList();
        permanents = new LinkedList<>();
        creatures = new LinkedList<>();
    }

    private List<Permanent> permanents;
    private List<Creature> creatures;
    private List<Card> hand;
    private Library library;

    private String name;
    private int lifeTotal;
}
