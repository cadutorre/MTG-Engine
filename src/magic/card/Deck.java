package magic.card;

import magic.card.creature.Creature;
import magic.card.creature.CreatureFactory;
import magic.mana.ManaColor;
import magic.mana.ManaCost;

import java.util.Iterator;
import java.util.LinkedList;

public class Deck implements Iterable<Card> {

    public static Deck getTestDeck() {
        Deck deck = new Deck();
        for (int i = 0; i<4; ++i) {
            deck.add(CreatureFactory.getCreature("Grizzly Bears"));
            deck.add(CreatureFactory.getCreature("Pillarfield Ox"));
            deck.add(CreatureFactory.getCreature("Serra Angel"));
            deck.add(CreatureFactory.getCreature("Memnite"));
            deck.add(Instant.getLightningBolt());
            deck.add(Instant.getUnmake());
        }
        return deck;
    }

    public void add(Card c) {
        cards.add(c);
    }

    public Iterator<Card> iterator() {
        return cards.iterator();
    }

    public Deck() {
        cards = new LinkedList<>();
    }

    private LinkedList<Card> cards;
}
