package magic.effect;

import magic.Engine;
import magic.Step;

public class StepChanged implements Effect<Step> {
    public final Step step;

    @Override
    public void setTarget(Step target) {
    }

    @Override
    public boolean isLegallyTargeted(Engine engine) {
        return true;
    }

    @Override
    public void execute(Engine engine) {
    }

    public StepChanged(Step s) {
        step = s;
    }
}
