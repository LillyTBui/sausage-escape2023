package no.uib.inf101.sem2.game.view;

import javax.swing.JFrame;

/**
 * Window of the game.
 * Sets the frame of the game.
 * 
 * @author Lilly Thi Bui
 */
public class GameWindow {
    private final String WINDOW_TITLE = "SAUSAGE ESCAPE";
    private GameView view;

    /**
     * Creates a {@link GameWindow} with given parameter.
     * 
     * @param view The {@link GameView} to be inside the {@link GameWindow}
     */
    public GameWindow(GameView view) {
        this.view = view;

        JFrame frame = new JFrame(WINDOW_TITLE);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setContentPane(this.view);
        frame.pack();
        frame.setVisible(true);
        frame.setResizable(false);
    }
}
