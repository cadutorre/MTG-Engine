package magic;

public enum Phase {
    BEGINNING("Beginning Phase"),
    FIRST_MAIN_PHASE("First Main Phase"),
    COMBAT_PHASE("Combat Phase"),
    SECOND_MAIN_PHASE("Second Main Phase"),
    ENDING("Ending Phase");

    public String toString() {
        return name;
    }

    Phase(String name) {
        this.name = name;
    }
    private String name;
}
