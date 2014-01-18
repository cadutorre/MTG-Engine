package magic.effect;

import magic.Stackable;

public interface Effect<T> extends Stackable {
    public void setTarget(T target);
}
