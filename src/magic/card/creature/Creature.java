package magic.card.creature;

import magic.card.Permanent;

public class Creature extends Permanent {

    public final int printedPower;
    public final int printedToughness;

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
        return name + " (" + getCurrentPower() + "/" + getCurrentToughness() + ")";
    }

    public Creature(String name, int power, int toughness) {
        super(name);
        printedPower = power;
        printedToughness = toughness;
        currentPower = power;
        currentToughness = toughness;
    }

    private int currentPower;
    private int currentToughness;
    private int oneOneCounters;
}
