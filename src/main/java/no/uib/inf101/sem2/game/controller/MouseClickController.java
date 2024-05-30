package no.uib.inf101.sem2.game.controller;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import no.uib.inf101.sem2.game.model.GameModel;
import no.uib.inf101.sem2.game.view.GameView;

/**
 * MouseClickController for the game.
 * Listens to all mouse clicks and handles correct information scenes.
 * 
 * @author Lilly Thi Bui
 */
public class MouseClickController implements MouseListener {
    private final GameView view;
    private final GameModel model;

    public MouseClickController(GameModel model, GameView view) {
        this.view = view;
        this.model = model;

        // add listener to all the buttons
        this.view.getPlayButton().addMouseListener(this);
        this.view.getHowToPlayButton().addMouseListener(this);
        this.view.getBackButton().addMouseListener(this);
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        // start the game if user clicks on play button
        if (e.getSource() == this.view.getPlayButton()) {
            this.model.startGame();
            this.view.getPlayButton().setVisible(false);
            this.view.getHowToPlayButton().setVisible(false);
            this.view.repaint();
        }
        // show 'how to play' scene if user clicks on how to play button
        if (e.getSource() == this.view.getHowToPlayButton()) {
            this.view.setWelcomeViewScene("how-to-play");
            this.view.getPlayButton().setVisible(false);
            this.view.getHowToPlayButton().setVisible(false);
            this.view.repaint();
        }
        // go back to welcome scene if user clicks on back button
        if (e.getSource() == this.view.getBackButton()) {
            this.view.setWelcomeViewScene("welcome");
            this.view.getBackButton().setVisible(false);
            this.view.repaint();
        }

    }

    @Override
    public void mouseEntered(MouseEvent e) {
        // ignore
    }

    @Override
    public void mouseExited(MouseEvent e) {
        // ignore
    }

    @Override
    public void mousePressed(MouseEvent e) {
        // ignore
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        // ignore
    }
}
