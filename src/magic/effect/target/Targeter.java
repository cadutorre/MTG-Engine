package magic.effect.target;

import magic.Engine;

import java.util.List;

public interface Targeter<T> {
    public List<T> getLegalTargets(Engine engine);
    public boolean isTargetLegal(T target, Engine engine);
}
