package magic.card;

import magic.Engine;
import magic.Stackable;

public interface Spell extends Stackable {
    public void execute(Engine engine);
}
