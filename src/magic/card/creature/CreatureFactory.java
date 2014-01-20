package magic.card.creature;

import magic.mana.ManaColor;
import magic.mana.ManaCost;

import java.util.HashMap;

public class CreatureFactory {
    private static HashMap<String, Creature> prototypes = new HashMap<>();

    static {
        addPrototype(new Creature("Grizzly Bears", new ManaCost(1, ManaColor.GREEN), 2, 2));
        addPrototype(new Creature("Pillarfield Ox", new ManaCost(3, ManaColor.WHITE), 2, 4));
        addPrototype(new Creature("Serra Angel", new ManaCost(3, ManaColor.WHITE, ManaColor.WHITE), 4, 4));
        addPrototype(new Creature("Memnite", new ManaCost(), 0, 1));
    }

    public static Creature getCreature(String name) {
        return prototypes.get(name).clone();
    }

    private static void addPrototype(Creature c) {
        prototypes.put(c.name, c);
    }
}
