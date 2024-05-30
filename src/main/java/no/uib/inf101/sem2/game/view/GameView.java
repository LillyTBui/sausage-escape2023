package no.uib.inf101.sem2.game.view;

import no.uib.inf101.sem2.game.model.GameModel;
import no.uib.inf101.sem2.game.model.entities.enemies.Enemy;
import no.uib.inf101.sem2.game.model.states.GameState;

import javax.swing.JLabel;
import javax.swing.JPanel;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Rectangle;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;

/**
 * View of the game.
 * Draws all the objects of the game which includes {@link Player},
 * {@link Chef}, {@link EnemyFactory}, {@link TileManager}.
 * Draws all the scenes.
 * Extends {@link JPanel}.
 * 
 * @author Lilly Thi Bui
 */
public class GameView extends JPanel {
    private GameModel model;

    // Play button
    private JPanel playButton = new JPanel();
    private JLabel playLabel = new JLabel("PLAY");
    // Settings button
    private JPanel howToPlayButton = new JPanel();
    private JLabel howToPlayLabel = new JLabel("HOW TO PLAY");
    // Back button
    private JPanel backButton = new JPanel();
    private JLabel backLabel = new JLabel("BACK");

    public static int GAME_WIDTH = 600;
    public static int GAME_HEIGHT = 700;

    private String welcomeViewScene = "welcome";

    /**
     * Creates a {@link GameView} with given parameter.
     * 
     * @param model The {@link GameModel} of the game to get correct game objects
     */
    public GameView(GameModel model) {
        this.model = model;
        this.setPreferredSize(new Dimension(GAME_WIDTH, GAME_HEIGHT));
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        drawGame(g2);
    }

    /**
     * Draws the whole game based on {@link GameState} from {@link GameModel}.
     * 
     * @param g2 The Graphics2D component where we draw {@link GameView}.
     */
    private void drawGame(Graphics2D g2) {
        if (this.model.getGameState() == GameState.GAME_START) {
            if (this.welcomeViewScene == "welcome") {
                // draw welcome scene
                drawWelcomeScene(g2);
            } else if (this.welcomeViewScene == "how-to-play") {
                // draw how to play scene
                drawHowToPlayScene(g2);
            }
        } else if (this.model.getGameState() == GameState.ACTIVE_GAME) {
            // draws background
            this.setBackground(Color.decode("#87CEEB"));
            BufferedImage sky = Inf101Graphics.loadImageFromResources("/scenes/sky_background.png");
            BufferedImage sky2 = Inf101Graphics.loadImageFromResources("/scenes/sky_background_2.png");
            Inf101Graphics.drawImage(g2, sky, 0, 0, 2);
            Inf101Graphics.drawImage(g2, sky2, 200, 0, 2);
            Inf101Graphics.drawImage(g2, sky, 400, 0, 2);

            // draw game window based on game camera
            g2.translate(this.model.getGameCamera().getX(), this.model.getGameCamera().getY());
            this.model.getTileManager().render(g2);
            this.model.getPlayer().render(g2);
            this.model.getChef().render(g2);
            if (this.model.getEnemyList().size() > 0) {
                for (Enemy enemy : this.model.getEnemyList()) {
                    enemy.render(g2);
                }
            }
            g2.translate(-this.model.getGameCamera().getX(), -this.model.getGameCamera().getY());
            // always draw hearts in the upper left corner
            this.model.getPlayer().renderPlayerHearts(g2);
        } else if (this.model.getGameState() == GameState.GAME_PAUSE) {
            this.setBackground(Color.WHITE);
            drawPauseScene(g2);
        } else if (this.model.getGameState() == GameState.GAME_OVER) {
            this.setBackground(Color.WHITE);
            drawEndingScene(g2);
        } else if (this.model.getGameState() == GameState.GAME_WON) {
            this.setBackground(Color.WHITE);
            drawWinningScene(g2);
        }
    }

    /**
     * Draws the 'welcome' scene
     * 
     * @param g2 The Graphics2D component where we draw the scene
     */
    private void drawWelcomeScene(Graphics2D g2) {
        // Draw frontpage background
        BufferedImage image = Inf101Graphics.loadImageFromResources("/scenes/frontpage.png");
        Inf101Graphics.drawCenteredImage(g2, image, (double) GAME_WIDTH / 2, (double) GAME_HEIGHT / 2, 0.375);

        // Create a new layout
        this.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();

        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Create play button
        this.playButton.setBackground(new Color(0x539165));
        this.playButton.setVisible(true);
        Font font = new Font("Monospaced", Font.PLAIN, 24);
        this.playLabel.setForeground(Color.WHITE);
        this.playLabel.setFont(font);
        this.playButton.add(playLabel);

        // Add play button
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.insets = new Insets(0, 0, 20, 0);
        this.add(playButton, gbc);

        // Create how to play button
        this.howToPlayButton.setBackground(new Color(0x539165));
        this.howToPlayButton.setVisible(true);
        this.howToPlayLabel.setForeground(Color.WHITE);
        this.howToPlayLabel.setFont(font);
        this.howToPlayButton.add(howToPlayLabel);

        // Add how to play button
        gbc.gridx = 0;
        gbc.gridy = 1;
        this.add(howToPlayButton, gbc);
    }

    /**
     * Draws the 'how to play' scene
     * 
     * @param g2 The Graphics2D component where we draw the scene
     */
    private void drawHowToPlayScene(Graphics2D g2) {
        // how to play
        drawSimpleCenteredText("HOW TO PLAY", 1, 25, GAME_WIDTH, 60, g2);
        drawSimpleCenteredText("Run away from the hungry chef and get back to your home!", 0, 16, GAME_WIDTH, 150, g2);

        // information about enemies
        drawSimpleCenteredText("ENEMIES", 1, 20, GAME_WIDTH, 250, g2);
        drawSimpleCenteredText("You lose by getting caught by the chef!", 0, 16, GAME_WIDTH, 340, g2);

        // chef image
        BufferedImage chefImg = Inf101Graphics.loadImageFromResources("/enemies/chef_idle_1.png");
        Inf101Graphics.drawCenteredImage(g2, chefImg, GAME_WIDTH / 2, 230, 2);

        // other enemies
        drawSimpleCenteredText("You lose 1 life getting hit by other enemies", 0, 16, GAME_WIDTH, 600, g2);

        // vegetable images
        BufferedImage tomatoImg = Inf101Graphics.loadImageFromResources("/enemies/tomato.png");
        Inf101Graphics.drawCenteredImage(g2, tomatoImg, GAME_WIDTH / 2 - 70, 360, 1.5);
        BufferedImage carrotImg = Inf101Graphics.loadImageFromResources("/enemies/carrot.png");
        Inf101Graphics.drawCenteredImage(g2, carrotImg, GAME_WIDTH / 2, 360, 1.5);
        BufferedImage broccoliImg = Inf101Graphics.loadImageFromResources("/enemies/broccoli.png");
        Inf101Graphics.drawCenteredImage(g2, broccoliImg, GAME_WIDTH / 2 + 70, 360, 1.5);

        // player lives
        drawSimpleCenteredText("You have 3 lives", 0, 16, GAME_WIDTH, 850, g2);

        BufferedImage heartImg = Inf101Graphics.loadImageFromResources("/objects/heart.png");
        Inf101Graphics.drawCenteredImage(g2, heartImg, GAME_WIDTH / 2 - 40, 480, 1.5);
        Inf101Graphics.drawCenteredImage(g2, heartImg, GAME_WIDTH / 2, 480, 1.5);
        Inf101Graphics.drawCenteredImage(g2, heartImg, GAME_WIDTH / 2 + 40, 480, 1.5);

        // controls information
        drawSimpleCenteredText("CONTROLS", 1, 20, GAME_WIDTH, 1100, g2);

        // control images
        BufferedImage leftBtnImg = Inf101Graphics.loadImageFromResources("/objects/left-button.png");
        Inf101Graphics.drawCenteredImage(g2, leftBtnImg, GAME_WIDTH / 2 - 60, 610, 1.5);
        BufferedImage rightBtnImg = Inf101Graphics.loadImageFromResources("/objects/right-button.png");
        Inf101Graphics.drawCenteredImage(g2, rightBtnImg, GAME_WIDTH / 2, 610, 1.5);
        BufferedImage escBtnImg = Inf101Graphics.loadImageFromResources("/objects/esc-button.png");
        Inf101Graphics.drawCenteredImage(g2, escBtnImg, GAME_WIDTH / 2 + 60, 610, 1.5);

        // add back button
        this.setLayout(new BorderLayout());

        this.backButton.setBackground(Color.BLACK);
        this.backLabel.setForeground(Color.WHITE);
        this.backButton.setVisible(true);
        this.backButton.add(backLabel);

        this.add(backButton, BorderLayout.PAGE_END);

        Font font = new Font("Monospaced", Font.PLAIN, 24);
        this.backLabel.setFont(font);
    }

    /**
     * Draws the 'pause' scene
     * 
     * @param g2 The Graphics2D component where we draw the scene
     */
    private void drawPauseScene(Graphics g2) {
        // Draw pause background
        BufferedImage pauseImg = Inf101Graphics.loadImageFromResources("/scenes/pause_scene.png");
        Inf101Graphics.drawCenteredImage(g2, pauseImg, (double) GAME_WIDTH / 2,
                (double) GAME_HEIGHT / 2 - 50, 0.36);

        // Draw pause text
        drawSimpleCenteredText("PAUSE", 1, 24, GAME_WIDTH, 200, g2);

        // Draw how to play again text
        drawSimpleCenteredText("Press down button to start playing again", 0, 16, GAME_WIDTH, GAME_HEIGHT + 600, g2);
    }

    /**
     * Draws the 'winning' scene
     * 
     * @param g2 The Graphics2D component where we draw the scene
     */
    private void drawWinningScene(Graphics2D g2) {
        // Draw ending winning background
        BufferedImage winningImg = Inf101Graphics.loadImageFromResources("/scenes/ending_scene_winning.png");
        Inf101Graphics.drawCenteredImage(g2, winningImg, (double) GAME_WIDTH / 2,
                (double) GAME_HEIGHT / 2 - 50, 0.4);

        // Draw game won text
        drawSimpleCenteredText("YOU WON", 1, 24, GAME_WIDTH, 200, g2);

        // Draw how to play again text
        drawSimpleCenteredText("Press space button to play again!", 0, 16, GAME_WIDTH, GAME_HEIGHT + 600, g2);
    }

    /**
     * Draws the 'losing' scene
     * 
     * @param g2 The Graphics2D component where we draw the scene
     */
    private void drawEndingScene(Graphics2D g2) {
        // Draw ending background
        BufferedImage deadImg = Inf101Graphics.loadImageFromResources("/scenes/ending_scene_dead.png");
        Inf101Graphics.drawCenteredImage(g2, deadImg, (double) GAME_WIDTH / 2 + 20,
                (double) GAME_HEIGHT / 2 - 50, 0.4);

        // Draw game over text
        drawSimpleCenteredText("GAME OVER", 1, 24, GAME_WIDTH, 200, g2);

        // Draw how to play again text
        drawSimpleCenteredText("Press space button to play again!", 0, 16, GAME_WIDTH, GAME_HEIGHT + 600, g2);
    }

    /**
     * Helper method to draw simple centered text
     * 
     * @param text      The text to be drawn
     * @param fontStyle The font style to be used for the text
     * @param textSize  The size of text
     * @param xPosition The x position of the text box
     * @param yPosition The y position of the text box
     * @param g2        The Graphics2D component where we draw the text
     */
    private void drawSimpleCenteredText(String text, int fontStyle, int textSize, int xPosition, int yPosition,
            Graphics g2) {
        Font font = new Font("Monospaced", fontStyle, textSize);
        g2.setColor(Color.BLACK);
        g2.setFont(font);
        Rectangle2D textBox = new Rectangle.Double(0, 0, xPosition, yPosition);
        Inf101Graphics.drawCenteredString(g2, text, textBox);
    }

    /** @return play button of {@link GameView}. */
    public JPanel getPlayButton() {
        return this.playButton;
    }

    /** @return how to play button of {@link GameView}. */
    public JPanel getHowToPlayButton() {
        return this.howToPlayButton;
    }

    /** @return back button of {@link GameView}. */
    public JPanel getBackButton() {
        return this.backButton;
    }

    /** @return welcomeViewScene of {@link GameView}. */
    public String getGameViewScene() {
        return this.welcomeViewScene;
    }

    /**
     * Sets welcomeViewScene of {@link GameView}.
     * 
     * @param scene The new scene
     */
    public void setWelcomeViewScene(String scene) {
        this.welcomeViewScene = scene;
    }
}
