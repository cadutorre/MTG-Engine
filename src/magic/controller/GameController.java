package magic.controller;


import magic.Engine;
import magic.Player;
import magic.Stackable;
import magic.card.Deck;
import magic.card.Library;
import magic.effect.target.TargetChooser;
import magic.view.GameUI;

import java.util.HashMap;

public class GameController {

    public static void main(String... args) {
        Player playerOne = new Player("Player One");
        playerOne.setLibrary(new Library(Deck.getTestDeck(), playerOne));
        Player playerTwo = new Player("Player Two");
        playerTwo.setLibrary(new Library(Deck.getTestDeck(), playerTwo));

        Engine engine = new Engine(playerOne, playerTwo);
        GameUI ui = new GameUI(engine);

        GameController game = new GameController(engine, ui);
        game.setPlayerController(playerOne, ui);
        game.setPlayerController(playerTwo, ui);

        engine.setController(game);

        engine.beginGame();
    }

    public Stackable offerPriority(Player p, boolean canPlaySorcery) {
        return playerControllers.get(p).offerPriority(p, canPlaySorcery);
    }

    public void setPlayerController(Player p, PlayerController c) {
        playerControllers.put(p, c);
    }

    public GameController(Engine engine, GameUI ui) {
        this.engine = engine;
        this.ui = ui;

        engine.addStateObserver(ui);

        playerControllers = new HashMap();
    }

    private Engine engine;
    private GameUI ui;
    private HashMap<Player, PlayerController> playerControllers;
}
