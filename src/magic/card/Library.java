package magic.card;

import magic.Player;

import java.util.ArrayList;
import java.util.Collections;

public class Library {

    public Card draw() {
        return cards.remove(0);
    }

    public boolean isEmpty() {
        return cards.isEmpty();
    }

    public void shuffle() {
        Collections.shuffle(cards);
    }

    public Library(Deck deck, Player owner) {
        this.owner = owner;
        cards = new ArrayList<>();
        for (Card c : deck) {
            // TODO clone the card
            c.setOwner(owner);
            cards.add(c);
        }

        shuffle();
    }

    private ArrayList<Card> cards;
    private Player owner;
}
