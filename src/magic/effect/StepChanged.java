package magic.effect;

import magic.Engine;
import magic.Step;

public class StepChanged extends  Effect<Step> {

    public String toPresentTense() {
        return target == null ? "" : target.toString();
    }

    @Override
    public boolean isLegallyTargeted(Engine engine) {
        return true;
    }

    @Override
    public void execute(Engine engine) {
    }

    public StepChanged(Step s) {
        super(s);
    }
}
