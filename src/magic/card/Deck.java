package magic.card;

import magic.card.creature.Creature;
import magic.mana.ManaColor;
import magic.mana.ManaCost;

import java.util.Iterator;
import java.util.LinkedList;

public class Deck implements Iterable<Card> {

    public static Deck getTestDeck() {
        Deck deck = new Deck();
        for (int i = 0; i<4; ++i) {
            deck.add(new Creature("Grizzly Bears", new ManaCost(1, ManaColor.GREEN), 2, 2));
            deck.add(new Creature("Pillarfield Ox", new ManaCost(3, ManaColor.WHITE), 2, 4));
            deck.add(new Creature("Serra Angel", new ManaCost(3, ManaColor.WHITE, ManaColor.WHITE), 4, 4));
            deck.add(new Creature("Memnite", new ManaCost(), 0, 1));
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
