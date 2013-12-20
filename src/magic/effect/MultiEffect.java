package magic.effect;

import magic.Engine;

public class MultiEffect<T> implements Effect<T> {

    public void setTarget(T target) {
        for (Effect<T> e : effects)
            e.setTarget(target);
    }

    public void execute(Engine engine) {
        for (Effect e : effects)
            e.execute(engine);
    }

    @Override
    public boolean isLegallyTargeted(Engine engine) {
        for (Effect e : effects) {
            if (e.isLegallyTargeted(engine))
                return true;
        }
        return false;
    }

    public MultiEffect(Effect<T>... effects) {
        this.effects = effects;
    }

    private Effect<T>[] effects;
}
