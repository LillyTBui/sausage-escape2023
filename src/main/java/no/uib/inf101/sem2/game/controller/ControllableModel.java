package no.uib.inf101.sem2.game.controller;

import no.uib.inf101.sem2.game.model.entities.player.Player;
import no.uib.inf101.sem2.game.model.states.GameState;

/**
 * Methods needed to play the game.
 * Includes controlling the {@link GameState} to start the game and show the
 * correct game scene, update the GameModel, control the {@link Player} and
 * get correct timer delay for the timer.
 * 
 * @author Lilly Thi bui
 */
public interface ControllableModel {
    /** @return the {@link GameState} of the model */
    public GameState getGameState();

    /**
     * set the {@link GameState} of the model with the given parameter.
     * 
     * @param state The new GameState
     */
    public void setGameState(GameState state);

    /** @return the {@link Player} of the model. */
    public Player getPlayer();

    /** start the game. */
    public void startGame();

    /** start a new game. */
    public void newGame();

    /**
     * Returns how many milliseconds it should be between each timer firing.
     * 
     * @return The delay in milliseconds
     */
    public int getTimerDelay();

    /**
     * Method which gets called every time the timer is fired.
     * Updates the game model.
     */
    public void update();
}
