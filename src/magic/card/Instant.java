package magic.card;

import magic.Engine;
import magic.Player;
import magic.Stackable;
import magic.card.creature.Creature;
import magic.effect.*;
import magic.effect.target.CreatureOnBattlefield;
import magic.effect.target.TargetChooser;

public class Instant extends Card implements Stackable {

    public static Instant getLightningBolt(Player owner) {
        TargetedEffect<Creature> boltEffect = new TargetedEffect<>(new CreatureOnBattlefield(), new DamageCreatureEffect(3));
        Instant bolt = new Instant("Lightning Bolt", boltEffect);
        bolt.setOwner(owner);

        return bolt;
    }

    public static Instant getUnmake(Player owner) {
        TargetedEffect<Creature> unmakeEffect = new TargetedEffect<>(new CreatureOnBattlefield(),
                new MultiEffect(new LeaveBattlefield(), new ExilePermanent()));
        Instant unmake = new Instant("Unmake", unmakeEffect);
        unmake.setOwner(owner);

        return unmake;
    }

    @Override
    public boolean isLegallyTargeted(Engine engine) {
        for (TargetedEffect<?> e : effects) {
            if (e.isLegallyTargeted(engine))
                return true;
        }
        return false;
    }

    public void execute(Engine engine) {
        for (TargetedEffect<?> e : effects)
            engine.executeEffect(e);

        // TODO send this card to the Graveyard
    }

    public void chooseTargets(TargetChooser chooser) {
        for (TargetedEffect<?> effect : effects)
            chooser.chooseTarget(effect);
    }

    public void setTarget(Object target) {
    }

    /**
     * An Instant can only be cast if there are legal targets for all of its Effects
     */
    public boolean canCast(Engine engine) {
        for (TargetedEffect<?> effect : effects) {
            if (effect.getLegalTargets(engine).isEmpty())
                return false;
        }
        return true;
    }

    public TargetedEffect[] getEffects() {
        return effects;
    }

    public Instant(String name, TargetedEffect<?>... effects) {
        super(name);
        this.effects = effects;
    }

    private TargetedEffect<?>[] effects;
}
