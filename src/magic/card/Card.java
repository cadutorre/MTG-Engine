package magic.card;

import magic.Engine;
import magic.Player;
import magic.Zone;

public abstract class Card {
    public final String name;

    public String toString() {
        return name;
    }

    public boolean isInstantSpeed() {
        return instantSpeed;
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

    public boolean canCast(Engine engine) {
        return true;
    }

    public Card(String name, boolean instantSpeed) {
        this.name = name;
        this.instantSpeed = instantSpeed;
        zone = Zone.HAND;
    }

    private Player owner;
    private Zone zone;
    private boolean instantSpeed;
}
