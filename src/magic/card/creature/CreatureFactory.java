package magic.card.creature;

import magic.effect.EffectFactory;
import magic.effect.trigger.EffectPredicate;
import magic.effect.trigger.TriggeredAbility;
import magic.mana.ManaColor;
import magic.mana.ManaCost;

import java.util.HashMap;

public class CreatureFactory {
    private static HashMap<String, Creature> prototypes = new HashMap<>();

    static {
        // Random cards
        addPrototype(new Creature("Grizzly Bears", new ManaCost(1, ManaColor.GREEN), 2, 2));
        addPrototype(new Creature("Pillarfield Ox", new ManaCost(3, ManaColor.WHITE), 2, 4));
        addPrototype(new Creature("Serra Angel", new ManaCost(3, ManaColor.WHITE, ManaColor.WHITE), 4, 4));
        addPrototype(new Creature("Memnite", new ManaCost(), 0, 1));

        Creature moroii = new Creature("Moroii", new ManaCost(2, ManaColor.BLACK, ManaColor.BLUE), 4, 4);
        moroii.addKeyword("Flying");
        moroii.addTriggeredAbility(new TriggeredAbility(EffectPredicate.YOUR_UPKEEP, EffectFactory.youLoseLife(1)));
        addPrototype(moroii);

        // Born of the Gods cards!
        final Creature silentSentinal = new Creature("Silent Sentinal", new ManaCost(5, ManaColor.WHITE, ManaColor.WHITE), 4, 6);
        silentSentinal.addKeyword("Flying");
        // TODO this little complicated trigger here
        addPrototype(silentSentinal);
    }

    public static Creature getCreature(String name) {
        return prototypes.get(name).clone();
    }

    private static void addPrototype(Creature c) {
        prototypes.put(c.name, c);
    }
}
