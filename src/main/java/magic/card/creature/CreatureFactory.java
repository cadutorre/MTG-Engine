package magic.card.creature;

import magic.card.CardType;
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
        Creature silentSentinal = new Creature("Silent Sentinal", new ManaCost(5, ManaColor.WHITE, ManaColor.WHITE), 4, 6);
        silentSentinal.addKeyword("Flying");
        silentSentinal.addKeyword("Haste");
        silentSentinal.addTriggeredAbility(new TriggeredAbility(EffectPredicate.THIS_CREATURE_ATTACKS, EffectFactory.MAY_RETURN_ENCHANTMENT));
        addPrototype(silentSentinal);

        Creature fateUnraveler = new Creature("Fate Unraveler", new ManaCost(3, ManaColor.BLACK), 3, 4);
        fateUnraveler.addCardType(CardType.ENCHANTMENT);
        fateUnraveler.addTriggeredAbility(new TriggeredAbility(EffectPredicate.OPPONENT_DRAWS_CARD, EffectFactory.thisCardDealsDamageToThatPlayer(1)));
        addPrototype(fateUnraveler);
    }

    public static Creature getCreature(String name) {
        return prototypes.get(name).clone();
    }

    private static void addPrototype(Creature c) {
        prototypes.put(c.name, c);
    }
}
