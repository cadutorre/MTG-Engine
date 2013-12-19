package magic.effect.trigger;

import magic.effect.Effect;
import magic.effect.EffectExecutor;

public class TestTrigger extends EffectTrigger {

    public Effect getEffect() {
        ++numTriggered;
        return new Effect() {
            public String toString() {
                return "[Testing Effect]";
            }
            @Override
            public void accept(EffectExecutor executor) {
                ++numExecuted;
            }

            @Override
            public boolean someTargetsLegal() {
                return true;
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
