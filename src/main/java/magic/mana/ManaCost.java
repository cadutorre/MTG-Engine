package magic.mana;

public class ManaCost {

    public boolean containsColor(ManaColor c) {
        return costs[c.ordinal()] > 0;
    }

    public int getColorAmount(ManaColor c) {
        return costs[c.ordinal()];
    }

    public ManaCost(int colorless, ManaColor... colors) {
        costs = new int[ManaColor.values().length];
        for (ManaColor c : colors)
            ++costs[c.ordinal()];
        costs[ManaColor.COLORLESS.ordinal()] = colorless;
    }
    public ManaCost(ManaColor... colors) {
        this(0, colors);
    }

    public int[] costs;
}
