package magic.effect;

import magic.Engine;

/**
 * Wraps another Effect and execute as that Effect normally would, while also gathering test data.
 *
 * The TestEffect records whether it executes, and in what order it executes
 * (relative to other TestEffects).
 *
 * When using this class in a suite of JUnit tests, call beginTesting() to reset the order count
 * and ensure accurate data.
 */
public class TestEffect<T> extends Effect<T> {

    private static int operationOrder = 0;

    public String toPresentTense() {
        return "";
    }

    public static void beginTesting() {
        operationOrder = 0;
    }

    public void execute(Engine engine) {
        hasResolved = true;
        orderResolved = operationOrder++;
        testThisEffect.execute(engine);
    }

    public boolean hasResolved() {
        return hasResolved;
    }

    public int getResolutionOrder() {
        return orderResolved;
    }

    public void setTarget(T target) {
        testThisEffect.setTarget(target);
    }

    @Override
    public boolean isLegallyTargeted(Engine engine) {
        return testThisEffect.isLegallyTargeted(engine);
    }

    public TestEffect(Effect<T> testThisEffect) {
        this.testThisEffect = testThisEffect;
    }

    private int orderResolved;
    private boolean hasResolved;
    private Effect<T> testThisEffect;
}
