package magic.effect.target;

import magic.effect.TargetedEffect;

public interface TargetChooser {
    public <T> void chooseTarget(TargetedEffect<T> effect);
}
