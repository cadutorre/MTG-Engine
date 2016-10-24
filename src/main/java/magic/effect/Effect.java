package magic.effect;

import magic.Stackable;

public abstract class Effect<T> implements Stackable {

    public abstract String toPresentTense();

    public void setTarget(T target) {
        this.target = target;
    }

    public T getTarget() {
        return target;
    }

    public Effect(T target) {
        this.target = target;
    }

    public Effect() {
    }

    protected T target;
}
