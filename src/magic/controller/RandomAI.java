package magic.controller;

import magic.Combat;
import magic.Engine;
import magic.Player;
import magic.Stackable;
import magic.card.Card;
import magic.card.Instant;
import magic.card.creature.Creature;
import magic.effect.Effect;
import magic.effect.TargetedEffect;

import java.util.LinkedList;
import java.util.List;

public class RandomAI implements PlayerController {
    @Override
    public Stackable offerPriority(Player player, boolean canPlaySorcery) {
        List<Card> legalCardList = new LinkedList<>();
        for (Card c : player.getHand()) {
            if (c.canCast(engine) && (c.isInstantSpeed() || canPlaySorcery))
                legalCardList.add(c);
        }

        if (legalCardList.isEmpty())
            return null;

        if (Math.random() > .25) {
            Card card = AIUtil.randomIndex(legalCardList);

            if (card instanceof Instant)
                ((Instant)card).chooseTargets(this);
            return card;
        } else {
            return null;
        }
    }

    @Override
    public void declareAttackers(Player p, Combat c) {
        List<Creature> attackers = c.getLegalAttackers();
        if (attackers.isEmpty())
            return;

        for (Creature a : attackers) {
            if (Math.random() > .5)
                c.addAttacker(a);
        }
    }

    @Override
    public void declareBlockers(Player p, Combat c) {
    }

    @Override
    public <T> void chooseTarget(TargetedEffect<T> effect) {
        effect.setTarget(AIUtil.randomIndex(effect.getLegalTargets(engine)));
    }

    @Override
    public boolean promptOptionalEffect(Effect<?> effect) {
        return false;
    }

    public RandomAI(Engine engine) {
        this.engine = engine;
    }

    private Engine engine;
}
