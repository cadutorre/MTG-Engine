package magic;


import magic.card.Deck;
import magic.card.Library;
import magic.effect.target.TargetChooser;
import magic.view.GameUI;

public class Game {

    public static void main(String... args) {
        Player playerOne = new Player("Player One");
        playerOne.setLibrary(new Library(Deck.getTestDeck(), playerOne));
        Player playerTwo = new Player("Player Two");
        playerTwo.setLibrary(new Library(Deck.getTestDeck(), playerTwo));

        Engine engine = new Engine(playerOne, playerTwo);
        GameUI ui = new GameUI(engine);
        new Game(engine, ui);
        engine.addEffectListener(ui);

        engine.beginGame();

        engine.mainPhase(playerOne);
    }

    public Game(Engine engine, TargetChooser chooser) {
        this.engine = engine;
        this.chooser = chooser;
    }

    private Engine engine;
    private TargetChooser chooser;
}
