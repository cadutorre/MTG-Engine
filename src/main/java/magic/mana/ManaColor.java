package magic.mana;

public enum ManaColor {

    COLORLESS(java.awt.Color.LIGHT_GRAY),
    WHITE(java.awt.Color.WHITE),
    BLUE(java.awt.Color.BLUE),
    BLACK(java.awt.Color.BLACK),
    RED(java.awt.Color.RED),
    GREEN(java.awt.Color.GREEN);

    public static ManaColor[] COLORS = {WHITE, BLUE, BLACK, RED, GREEN};

    public final java.awt.Color color;

    ManaColor(java.awt.Color color) {
        this.color = color;
    }
}
