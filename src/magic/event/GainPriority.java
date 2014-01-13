package magic.event;

import magic.Player;

public class GainPriority implements GameEvent {
    public final Player player;

    public String toString() {
        return player  + " gains Priority";
    }

    public GainPriority(Player player) {
        this.player = player;
    }
}
