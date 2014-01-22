package magic.effect;

import magic.Engine;
import magic.card.Card;

public class OptionalTargetedEffect<T> extends Effect<T> {

    public String toString() {
        return "Optional: " + effect;
    }

    @Override
    public boolean isLegallyTargeted(Engine engine) {
        // The player will be given the option only if the effect itself can actually occur
        return !effect.getLegalTargets(engine).isEmpty();
    }

    @Override
    public void execute(Engine engine) {
        if (engine.getController().promptOptionalEffect(source.getController(), effect)) {
            engine.getController().chooseTargets(source.getController(), effect);
            engine.executeEffect(effect);
        }
    }

    public OptionalTargetedEffect(Card source, TargetedEffect<T> effect) {
        this.effect = effect;
        this.source = source;
    }

    private TargetedEffect<T> effect;
    private Card source;
}
