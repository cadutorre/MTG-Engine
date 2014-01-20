package magic;

import magic.card.Permanent;
import magic.effect.DrawCard;
import magic.effect.UntapPermanentEffect;

public enum Step {
    // Beginning Phase
    UNTAP {
        public void doStep(Engine engine) {
            engine.setStep(this);

            for (Permanent p : engine.getActivePlayer().getPermanentsControlled())
                engine.executeEffect(new UntapPermanentEffect(p));
        }
    },
    UPKEEP {
        public void doStep(Engine engine) {
            engine.setStep(this);
            engine.passPriority();
        }
    },
    DRAW {
        public void doStep(Engine engine) {
            engine.setStep(this);
            engine.executeEffect(new DrawCard(engine.getActivePlayer(), 1));
            engine.passPriority();
        }
    },

    // Combat Phase
    BEGINNING_COMBAT {
        public void doStep(Engine engine) {
            engine.setCombat(new Combat(engine));
            engine.setStep(this);

            engine.passPriority();
        }
    },
    DECLARE_ATTACKERS {
        public void doStep(Engine engine) {
            engine.setStep(this);

            engine.getController().declareAttackers(engine.getActivePlayer());
            engine.passPriority();
        }
    },
    DECLARE_BLOCKERS {
        public void doStep(Engine engine) {
            engine.setStep(this);
        }
    },
    COMBAT_DAMAGE {
        public void doStep(Engine engine) {
            engine.setStep(this);
        }
    },
    END_COMBAT {
        public void doStep(Engine engine) {
            engine.setStep(this);
        }
    },

    // End Phase
    END {
        public void doStep(Engine engine) {
            engine.setStep(this);
            engine.passPriority();
        }
    },
    CLEANUP {
        public void doStep(Engine engine) {
            engine.setStep(this);
            // TODO discard
            // TODO reset damage
        }
    };

    public abstract void doStep(Engine engine);
}
