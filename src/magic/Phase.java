package magic;

public enum Phase {
    BEGINNING("Beginning Phase") {
        public void doPhase(Engine engine) {
            engine.setPhase(this);

            Step.UNTAP.doStep(engine);
            Step.UPKEEP.doStep(engine);
            Step.DRAW.doStep(engine);

            FIRST_MAIN_PHASE.doPhase(engine);
        }
    },
    FIRST_MAIN_PHASE("First Main Phase") {
        public void doPhase(Engine engine) {
            engine.setPhase(this);

            engine.passPriority();

            COMBAT_PHASE.doPhase(engine);
        }
    },
    COMBAT_PHASE("Combat Phase") {
        public void doPhase(Engine engine) {
            engine.setPhase(this);

            Step.BEGINNING_COMBAT.doStep(engine);
            Step.DECLARE_ATTACKERS.doStep(engine);
            Step.DECLARE_BLOCKERS.doStep(engine);
            Step.COMBAT_DAMAGE.doStep(engine);
            Step.END_COMBAT.doStep(engine);

            SECOND_MAIN_PHASE.doPhase(engine);
        }
    },
    SECOND_MAIN_PHASE("Second Main Phase") {
        public void doPhase(Engine engine) {
            engine.setPhase(this);

            engine.passPriority();

            ENDING.doPhase(engine);
        }
    },
    ENDING("Ending Phase") {
        public void doPhase(Engine engine) {
            engine.setPhase(this);

            Step.END.doStep(engine);
            Step.CLEANUP.doStep(engine);

            engine.setActivePlayer(engine.playerAfter(engine.getActivePlayer()));
            BEGINNING.doPhase(engine);
        }
    };

    public String toString() {
        return name;
    }

    public abstract void doPhase(Engine engine);

    Phase(String name) {
        this.name = name;
    }
    private String name;
}
