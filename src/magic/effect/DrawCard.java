package magic.effect;

import magic.Engine;
import magic.Player;
import magic.card.Card;

public class DrawCard implements Effect<Player> {

    public String toString() {
        return (target == null ? "Target Player" : target) + " draws " + (num == 1 ? "a card" : num + " cards");
    }

    public boolean isLegallyTargeted(Engine engine) {
        return true;
    }

    public void setTarget(Player target) {
        this.target = target;
    }

    public void execute(Engine engine) {
        cardsDrawn = target.draw(num);
    }

    public Card[] getCardsDrawn() {
        return cardsDrawn;
    }

    public DrawCard(Player target, int num) {
        this.target = target;
        this.num = num;
    }

    public DrawCard(int num) {
        this.num = num;
    }

    private int num;
    private Player target;
    public Card[] cardsDrawn;
}
