package magic;

/**
 * Represents something that can be put on the GameController Stack.
 *
 * This means instances of Spells and Abilities, as well as Triggered Effects
 */
public interface Stackable {
    public boolean isLegallyTargeted(Engine engine);
    public void execute(Engine engine);

}
