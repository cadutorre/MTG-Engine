package magic.effect;

import magic.Engine;
import magic.effect.target.Targeter;

import java.util.List;

public class TargetedEffect<T> extends Effect<T> {

    public String toPresentTense() {
        return effect.toPresentTense();
    }

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

    /**
     * Using "? super T" because the Effect can be more general than the target.
     *
     * For example, the Targeter might specify creatures on the battlefield, or creatures controlled
     * by a certain player, while the effect is ExilePermanent - anything that can be done
     * to a creature can be done to a permanent, hence the contravariance.
     */
    public TargetedEffect(Targeter<T> targeter, Effect<? super T> effect) {
        this.targeter = targeter;
        this.effect = effect;
    }

    private Targeter<T> targeter;
    private Effect<? super T> effect;
}
