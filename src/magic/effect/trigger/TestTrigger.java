package magic.effect.trigger;

import magic.Engine;
import magic.effect.Effect;

public class TestTrigger extends EffectTrigger {

    public Effect getEffect() {
        ++numTriggered;
        return new Effect() {
            public String toString() {
                return "[Testing Effect]";
            }
            @Override
            public void execute(Engine engine) {
                ++numExecuted;
            }

            @Override
            public boolean isLegallyTargeted(Engine engine) {
                return true;
            }

            @Override
            public void setTarget(Object target) {
            }
        };
    }

    public int numTriggersExecuted() {
        return numExecuted;
    }

    public TestTrigger(EffectPredicate predicate) {
        super(predicate);
    }

    private int numExecuted;
    private int numTriggered;
}
