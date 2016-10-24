package magic;

public enum Zone {
    BATTLEFIELD("the Battlefield"),
    EXILE("Exile"),
    GRAVEYARD("the Graveyard"),
    CASTING("casting"),
    HAND("Hand");

    public String toString() {
        return name;
    }

    Zone(String name) {
        this.name = name;
    }

    private String name;
}
