package magic;


import magic.card.Instant;
import magic.card.creature.Creature;
import magic.effect.EnterBattlefield;
import magic.effect.target.TargetChooser;
import magic.view.GameUI;

public class Game {

    public static void main(String... args) {
        Player playerOne = new Player("Player One");
        Player playerTwo = new Player("Player Two");
        Engine engine = new Engine(playerOne, playerTwo);
        GameUI ui = new GameUI(engine);
        Game game = new Game(engine, ui);
        engine.addEffectListener(ui);

        // These are jut for testing
        engine.executeEffect(new EnterBattlefield(Creature.getPillarfieldOx(playerOne)));
        engine.executeEffect(new EnterBattlefield(Creature.getPillarfieldOx(playerOne)));
        engine.executeEffect(new EnterBattlefield(Creature.getPillarfieldOx(playerTwo)));

        Instant lightningBolt = Instant.getLightningBolt(playerOne);
        game.playInstant(lightningBolt);
    }

    public void playInstant(Instant instant) {
        instant.chooseTargets(chooser);
        engine.placeOnStack(instant);
        engine.executeTheStack();
    }

    public Game(Engine engine, TargetChooser chooser) {
        this.engine = engine;
        this.chooser = chooser;
    }

    private Engine engine;
    private TargetChooser chooser;
}
