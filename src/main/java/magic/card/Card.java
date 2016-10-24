package magic.card;

import magic.Engine;
import magic.Player;
import magic.Stackable;
import magic.Zone;
import magic.mana.ManaCost;

import java.util.LinkedList;

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

    /**
     * TODO I just learned that the controller is whoever casts the card.
     * The controller should be kept consistent with this fact
     */
    public void setController(Player player) {
        controller = player;
    }

    public Player getController() {
        return controller;
    }


    public boolean canCast(Engine engine) {
        return true;
    }

    public ManaCost getCost() {
        return cost;
    }

    public void addCardType(CardType type) {
        cardTypes.add(type);
    }

    public boolean hasCardType(CardType type) {
        return cardTypes.contains(type);
    }

    public Card(String name, ManaCost cost, boolean instantSpeed) {
        this.name = name;
        this.cost = cost;
        this.instantSpeed = instantSpeed;
        zone = Zone.HAND;
        cardTypes = new LinkedList<>();
    }

    private Player owner;
    private Player controller;
    private ManaCost cost;
    private Zone zone;
    private boolean instantSpeed;
    private LinkedList<CardType> cardTypes;
}
