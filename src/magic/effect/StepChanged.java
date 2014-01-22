package magic.effect;

import magic.Engine;
import magic.Step;

public class StepChanged extends  Effect<Step> {

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
