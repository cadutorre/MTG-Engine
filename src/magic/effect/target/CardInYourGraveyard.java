package magic.effect.target;

import magic.Engine;
import magic.card.Card;

import java.util.List;

public class CardInYourGraveyard implements Targeter<Card> {

    @Override
    public List<Card> getLegalTargets(Engine engine) {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public boolean isTargetLegal(Card target, Engine engine) {
        return false;  //To change body of implemented methods use File | Settings | File Templates.
    }
}
