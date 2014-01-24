package magic.effect;

import magic.Engine;

public class MultiEffect<T> extends Effect<T> {

    @Override
    public String toPresentTense() {
        return "";
    }

    @Override
    public void setTarget(T target) {
        super.setTarget(target);
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
        super();
        this.effects = effects;
    }

    private Effect<T>[] effects;
}
