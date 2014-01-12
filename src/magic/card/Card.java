package magic.card;

import magic.Player;
import magic.Zone;

public abstract class Card {
    public final String name;

    public abstract Card clone();

    public String toString() {
        return name;
    }

    public Zone getZone() {
        return zone;
    }

    public void setZone(Zone zone) {
        this.zone = zone;
    }

    public void setOwner(Player player) {
        owner = player;
    }

    public Player getOwner() {
        return owner;
    }

    public Card(String name) {
        this.name = name;
        zone = Zone.HAND;
    }

    private Player owner;
    private Zone zone;
}
