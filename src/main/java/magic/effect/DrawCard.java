package magic.effect;

import magic.Engine;
import magic.Player;
import magic.card.Card;

public class DrawCard extends Effect<Player> {
    public Card[] cardsDrawn;

    @Override
    public String toPresentTense() {
        return target + " Draws " + (num == 1 ? " a Card" : num + " Cards");
    }

    public String toString() {
        return (target == null ? "Target Player" : target) + " draws " + (num == 1 ? "a card" : num + " cards");
    }

    public boolean isLegallyTargeted(Engine engine) {
        return true;
    }

    public void execute(Engine engine) {
        cardsDrawn = target.draw(num);
    }

    public Card[] getCardsDrawn() {
        return cardsDrawn;
    }

    public DrawCard(Player target, int num) {
        super(target);
        this.num = num;
    }

    private int num;
}
