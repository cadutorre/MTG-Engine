package magic.card;

public abstract class Permanent extends Card {

    public boolean isTapped() {
        return tapped;
    }

    public void tap() {
        tapped = true;
    }

    public void untap() {
        tapped = false;
    }

    public Permanent(String name) {
        super(name);
    }

    private boolean tapped;
}
