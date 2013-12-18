package magic.effect;

/**
 * Wraps another Effect and execute as that Effect normally would, while also gathering test data.
 *
 * The TestEffect records whether it executes, and in what order it executes
 * (relative to other TestEffects).
 *
 * When using this class in a suite of JUnit tests, call beginTesting() to reset the order count
 * and ensure accurate data.
 */
public class TestEffect implements Effect {

    private static int operationOrder = 0;

    public static void beginTesting() {
        operationOrder = 0;
    }

    public void accept(EffectExecutor executor) {
        hasResolved = true;
        orderResolved = operationOrder++;
        testThisEffect.accept(executor);
    }

    public boolean hasResolved() {
        return hasResolved;
    }

    public int getResolutionOrder() {
        return orderResolved;
    }

    @Override
    public boolean someTargetsLegal() {
        return testThisEffect.someTargetsLegal();
    }

    public TestEffect(Effect testThisEffect) {
        this.testThisEffect = testThisEffect;
    }

    private int orderResolved;
    private boolean hasResolved;
    private Effect testThisEffect;
}
