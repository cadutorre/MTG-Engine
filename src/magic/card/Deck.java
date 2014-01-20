package magic.card;

import magic.card.creature.Creature;

import java.util.Iterator;
import java.util.LinkedList;

public class Deck implements Iterable<Card> {

    public static Deck getTestDeck() {
        Deck deck = new Deck();
        for (int i = 0; i<4; ++i) {
            deck.add(new Creature("Grizzly Bears", 2, 2));
            deck.add(new Creature("Pillarfield Ox", 2, 4));
            deck.add(new Creature("Serra Angel", 4, 4));
            deck.add(new Creature("Memnite", 0, 1));
            //deck.add(Instant.getLightningBolt());
            //deck.add(Instant.getUnmake());
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
