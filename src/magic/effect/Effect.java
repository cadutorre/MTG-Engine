package magic.effect;

import magic.Engine;
import magic.event.GameEvent;

public interface Effect<T> extends GameEvent {
    public boolean isLegallyTargeted(Engine engine);
    public void setTarget(T target);
    public void execute(Engine engine);
}
