package no.uib.inf101.sem2.game.controller;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import no.uib.inf101.sem2.game.model.GameModel;
import no.uib.inf101.sem2.game.model.states.GameState;
import no.uib.inf101.sem2.game.view.GameView;

import java.awt.event.ActionEvent;
import javax.swing.Timer;

/**
 * KeyBoardController for the game.
 * Listens to all keypresses and handles clock tick events from timer.
 * 
 * @author Lilly Thi Bui
 */
public class KeyBoardController implements KeyListener {
    private final GameView view;
    private final GameModel model;

    private Timer timer;

    public KeyBoardController(GameModel model, GameView view) {
        this.view = view;
        this.model = model;

        this.timer = new Timer(this.model.getTimerDelay(), this::clockTick);

        // Capture keyboard input when the view is in focus and send it here
        this.view.setFocusable(true);
        this.view.addKeyListener(this);

        // start timer
        this.timer.start();
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (this.model.getGameState() == GameState.ACTIVE_GAME) {
            // move player to the left
            if (e.getKeyCode() == KeyEvent.VK_LEFT) {
                this.model.getPlayer().isMovingLeft(true);
            }
            // move player to the right
            else if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
                this.model.getPlayer().isMovingRight(true);
            }
            // pause game
            else if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
                this.model.setGameState(GameState.GAME_PAUSE);
            }
            this.view.repaint();
        } else if (this.model.getGameState() == GameState.GAME_PAUSE) {
            // start game again by pressing down
            if (e.getKeyCode() == KeyEvent.VK_DOWN) {
                this.model.startGame();
            }
            this.view.repaint();
        } else if (this.model.getGameState() == GameState.GAME_OVER) {
            // press space button to go back to welcome screen
            if (e.getKeyCode() == KeyEvent.VK_SPACE) {
                this.model.newGame();
                this.view.repaint();
            }
        } else if (this.model.getGameState() == GameState.GAME_WON) {
            // press space button to go back to welcome screen
            if (e.getKeyCode() == KeyEvent.VK_SPACE) {
                this.model.newGame();
                this.view.repaint();
            }
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        if (this.model.getGameState() == GameState.ACTIVE_GAME) {
            // stop player from moving to the left
            if (e.getKeyCode() == KeyEvent.VK_LEFT) {
                this.model.getPlayer().isMovingLeft(false);
            }
            // stop player from moving to the right
            else if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
                this.model.getPlayer().isMovingRight(false);
            }
            this.view.repaint();
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {
        // ignore
    }

    /**
     * Update {@link GameModel} every time the timer goes while the game is active.
     * 
     * @param event The ActionEvent that is created when an event occurs
     */
    private void clockTick(ActionEvent event) {
        if (this.model.getGameState() == GameState.ACTIVE_GAME) {
            this.model.update();
            setCorrectDelay();
            this.view.repaint();
        }
    }

    /**
     * Get correct delay from GameModel and update Timer object with this value.
     */
    private void setCorrectDelay() {
        int delay = this.model.getTimerDelay();
        this.timer.setDelay(delay);
        this.timer.setInitialDelay(delay);
    }
}
