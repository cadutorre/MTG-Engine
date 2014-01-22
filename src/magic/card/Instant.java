package magic.card;

import magic.Engine;
import magic.Player;
import magic.Stackable;
import magic.card.creature.Creature;
import magic.effect.*;
import magic.effect.target.CreatureOnBattlefield;
import magic.effect.target.TargetChooser;
import magic.mana.ManaColor;
import magic.mana.ManaCost;

public class Instant extends Card implements Stackable, Spell {

    public static Instant getLightningBolt(Player owner) {
        Instant bolt = getLightningBolt();
        bolt.setOwner(owner);

        return bolt;
    }

    public static Instant getLightningBolt() {
        TargetedEffect<Creature> boltEffect = new TargetedEffect<>(new CreatureOnBattlefield(), new DamageCreatureEffect(3));
        Instant bolt = new Instant("Lightning Bolt", new ManaCost(ManaColor.RED), boltEffect);

        return bolt;
    }

    public static Instant getUnmake(Player owner) {
        Instant unmake = getUnmake();
        unmake.setOwner(owner);

        return unmake;
    }

    public static Instant getUnmake() {
        TargetedEffect<Creature> unmakeEffect = new TargetedEffect<>(new CreatureOnBattlefield(),
                new MultiEffect(new LeaveBattlefield(), new ExilePermanent()));
        Instant unmake = new Instant("Unmake", new ManaCost(), unmakeEffect);

        return unmake;
    }

    public Instant clone() {
        return null; // TODO implement a deep clone
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

    public Instant(String name, ManaCost cost, TargetedEffect<?>... effects) {
        super(name, cost, true);
        this.effects = effects;
        addCardType(CardType.INSTANT);
    }

    private TargetedEffect<?>[] effects;
}
