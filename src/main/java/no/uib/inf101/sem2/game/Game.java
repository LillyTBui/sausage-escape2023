package no.uib.inf101.sem2.game;

import no.uib.inf101.sem2.game.controller.KeyBoardController;
import no.uib.inf101.sem2.game.controller.MouseClickController;
import no.uib.inf101.sem2.game.model.GameModel;
import no.uib.inf101.sem2.game.view.GameView;
import no.uib.inf101.sem2.game.view.GameWindow;

/**
 * Game class.
 * Connects {@link GameWindow}, {@link GameModel}, {@link GameView} and
 * controllers to create the game.
 * 
 * @author Lilly Thi Bui
 */
public class Game {
    private GameModel model;
    private GameView view;

    /** Creates {@link Game}. */
    public Game() {
        this.model = new GameModel();
        this.view = new GameView(this.model);
        new GameWindow(this.view);

        // Controllers of the game
        new MouseClickController(model, view);
        new KeyBoardController(model, view);
    }
}
