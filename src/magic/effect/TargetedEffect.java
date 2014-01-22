package magic.effect;

import magic.Engine;
import magic.effect.target.Targeter;

import java.util.List;

public class TargetedEffect<T> extends Effect<T> {

    public String toString() {
        return effect.toString();
    }

    public List<T> getLegalTargets(Engine engine) {
        return targeter.getLegalTargets(engine);
    }

    public void setTarget(T target) {
        super.setTarget(target);
        effect.setTarget(target);
    }

    public boolean isLegallyTargeted(Engine engine) {
        return targeter.isTargetLegal(target, engine);
    }

    public void execute(Engine engine) {
        engine.executeEffect(effect);
    }

    public TargetedEffect(Targeter<T> targeter, Effect<T> effect) {
        this.targeter = targeter;
        this.effect = effect;
    }

    private Targeter<T> targeter;
    private Effect<T> effect;
}
