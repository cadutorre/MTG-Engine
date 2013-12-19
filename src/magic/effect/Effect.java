package magic.effect;

import magic.Engine;

public interface Effect {
    public void execute(Engine engine);
    public boolean someTargetsLegal();
}
