package magic.card.creature;

import magic.Engine;
import magic.Player;
import magic.card.Permanent;
import magic.card.Spell;
import magic.effect.EnterBattlefield;
import magic.mana.ManaColor;
import magic.mana.ManaCost;

import java.util.HashSet;

public class Creature extends Permanent implements Spell {

    public static Creature getPillarfieldOx(Player owner) {
        Creature c = new Creature("Pillarfield Ox", new ManaCost(3, ManaColor.WHITE), 2, 4);
        c.setOwner(owner);
        return c;
    }

    public final int printedPower;
    public final int printedToughness;

    public boolean hasKeyword(String keyword) {
        return keywords.contains(keyword);
    }

    public void setSummoningSickness(boolean has) {
        hasSummoningSickness = !hasKeyword("Haste") && has;
    }

    public boolean hasSummoningSickness() {
        return hasSummoningSickness;
    }

    @Override
    public boolean isLegallyTargeted(Engine engine) {
        return true;  // Trivially, there are never targets
    }

    @Override
    public void execute(Engine engine) {
        engine.executeEffect(new EnterBattlefield(this));
    }

    public void modifyPowerToughness(int power, int toughness) {
        currentPower += power;
        currentToughness += toughness;
    }

    public int getCurrentPower() {
        return currentPower + oneOneCounters;
    }

    public int getCurrentToughness() {
        return currentToughness + oneOneCounters;
    }

    public void reduceToughness(int amount) {
        currentToughness = Math.max(0, currentToughness - amount);
    }

    public void resetPowerToughness() {
        currentToughness = printedToughness;
        currentPower = printedPower;
    }

    public int getOneOneCounters() {
        return oneOneCounters;
    }

    public void placeOneOneCounters(int counters) {
        oneOneCounters += counters;
    }

    public String toString() {
        return (getController() == null ? "" : (getController() + "'s "))
                + name + " (" + getCurrentPower() + "/" + getCurrentToughness() + ")";
    }

    public Creature(String name, ManaCost cost, int power, int toughness) {
        super(name, cost);
        printedPower = power;
        printedToughness = toughness;
        currentPower = power;
        currentToughness = toughness;
        keywords = new HashSet<>();
    }

    private int currentPower;
    private int currentToughness;
    private int oneOneCounters;
    private HashSet<String> keywords;
    private boolean hasSummoningSickness;
}
