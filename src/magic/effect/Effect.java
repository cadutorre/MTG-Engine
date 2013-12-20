package magic.effect;

import magic.Engine;

public interface Effect<T> {
    public boolean isLegallyTargeted(Engine engine);
    public void setTarget(T target);
    public void execute(Engine engine);
}
