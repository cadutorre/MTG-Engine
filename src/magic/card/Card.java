package magic.card;

import magic.Engine;
import magic.Player;
import magic.Stackable;
import magic.Zone;
import magic.mana.ManaCost;

public abstract class Card implements Stackable {
    public final String name;

    public abstract Card clone();

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

    public ManaCost getCost() {
        return cost;
    }

    public Card(String name, ManaCost cost, boolean instantSpeed) {
        this.name = name;
        this.cost = cost;
        this.instantSpeed = instantSpeed;
        zone = Zone.HAND;
    }

    private Player owner;
    private ManaCost cost;
    private Zone zone;
    private boolean instantSpeed;
}
