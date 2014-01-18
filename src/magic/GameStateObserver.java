package magic;

import magic.card.Card;
import magic.effect.Effect;

public interface GameStateObserver {

    public void cardPlayed(Player p, Card c);
    public void stackChanged();
    public void effectExecuted(Effect<?> e);
}
