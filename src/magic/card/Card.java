package magic.card;

import magic.Zone;

public abstract class Card {
    public final String name;

    public String toString() {
        return name;
    }

    public Zone getZone() {
        return zone;
    }

    public void setZone(Zone zone) {
        this.zone = zone;
    }

    public Card(String name) {
        this.name = name;
        zone = Zone.HAND;
    }

    private Zone zone;
}
