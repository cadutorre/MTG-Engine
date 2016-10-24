package magic.controller;

import magic.Combat;
import magic.Player;
import magic.Stackable;
import magic.effect.Effect;
import magic.effect.TargetedEffect;

public class PassingAI implements PlayerController {
    @Override
    public Stackable offerPriority(Player player, boolean canPlaySorcery) {
        return null;
    }

    @Override
    public void declareAttackers(Player p, Combat c) {
    }

    @Override
    public void declareBlockers(Player p, Combat c) {
    }

    @Override
    public <T> void chooseTarget(TargetedEffect<T> effect) {
        // should never be reached
    }

    @Override
    public boolean promptOptionalEffect(Effect<?> effect) {
        return false;
    }
}
