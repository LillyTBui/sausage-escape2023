package no.uib.inf101.sem2.game.model.entities.player;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

import no.uib.inf101.sem2.game.model.GameModel;
import no.uib.inf101.sem2.game.model.entities.Entity;
import no.uib.inf101.sem2.game.model.entities.tile.Tile;
import no.uib.inf101.sem2.game.model.entities.tile.TileManager;
import no.uib.inf101.sem2.game.model.states.GameState;
import no.uib.inf101.sem2.game.model.states.PlayerState;
import no.uib.inf101.sem2.game.view.Inf101Graphics;

/**
 * Player sprite for the game.
 * Can run left and right.
 * Extends {@link Entity}.
 * 
 * @author Lilly Thi bui
 */
public class Player extends Entity {
    private TileManager tileManager;
    private GameModel model;
    private BufferedImage[][] animations;
    private PlayerState playerState = PlayerState.IDLE;
    private int playerAction;
    private int playerLives = 3;

    // player hearts
    private BufferedImage[] playerHearts;
    private int hearts = this.playerLives;
    private boolean drawAllHearts = true;
    private boolean heartBlink = false;
    private boolean showHeart = false;
    private int showHeartCounter = 0;
    private int hideHeartCounter = 0;
    private int howManyBlinks = 3;

    // speed and direction
    private float playerSpeed = 1;
    private boolean isMovingLeft = false;
    private boolean isMovingRight = false;

    // gravity
    private float gravitySpeed = 1;
    private float speedX = 0;
    private float speedY = 0;

    /**
     * Creates a new {@link Player} with given values.
     * 
     * @param x      The x position
     * @param y      The y position
     * @param width  The width of the {@link Player}
     * @param height The height of the {@link Player}
     * @param model  The {@link GameModel} of the game
     */
    public Player(float x, float y, int width, int height, GameModel model) {
        super(x, y, width, height, 2);
        loadPlayerAnimations();
        loadPlayerHearts();
        this.tileManager = new TileManager();
        this.model = model;
    }

    /**
     * Calls all the methods to update position, collision detection and animations
     * of the {@link Player}.
     */
    public void update() {
        updatePosition();
        updateHitBox();
        updateHeartAnimation();
        updateAnimationTick();
    }

    /**
     * Renders the {@link Player} with correct animation image based on player
     * action.
     * 
     * @param g The Graphics component where we draw the {@link Player}
     */
    public void render(Graphics g) {
        // get corresponding integer values based on state to get correct images
        if (this.playerState == PlayerState.IDLE) {
            this.playerAction = 0;
        } else if (this.playerState == PlayerState.RUNNING_LEFT) {
            this.playerAction = 1;
        } else if (this.playerState == PlayerState.RUNNING_RIGHT) {
            this.playerAction = 2;
        }
        // draw player
        g.drawImage(animations[playerAction][this.animationIndex], (int) this.x, (int) this.y, width, height, null);
    }

    /**
     * Renders {@link Player}'s hearts. When {@link Player} loses lives the hearts
     * will blink sometimes before disappearing.
     * 
     * @param g The Graphics component where we draw {@link Player}'s hearts
     */
    public void renderPlayerHearts(Graphics g) {
        // draw current player hearts based on life
        for (int i = 0; i < this.hearts; i++) {
            if (i == this.hearts - 1) {
                if (!this.showHeart) {
                    /*
                     * SOURCE CODE:
                     * Author: Java2s.com
                     * Date: 6.04.2023
                     * Title: If the buffered image supports transparency: BufferedImage "2D
                     * Graphics" Java Tutorial
                     * URL: http://www.java2s.com/Tutorial/Java/0261__2D-Graphics/
                     * Ifthebufferedimagesupportstransparency.html
                     */
                    BufferedImage bimage = new BufferedImage(200, 200,
                            BufferedImage.TYPE_BYTE_INDEXED);
                    Graphics2D g2d = bimage.createGraphics();
                    Color transparent = new Color(0, 0, 0, 0);
                    g2d.setColor(transparent);
                    g2d.setComposite(AlphaComposite.Src);
                    g2d.drawImage(playerHearts[i], 30 * i, 5, null);
                } else {
                    g.drawImage(playerHearts[i], 30 * i, 5, null);
                }
            } else {
                g.drawImage(playerHearts[i], 30 * i, 5, null);
            }
            // draws all the hearts before player gets hit
            if (drawAllHearts) {
                g.drawImage(playerHearts[i], 30 * i, 5, null);
            }
        }
    }

    /**
     * Updates {@link Player} position based on gravity, user control and tile
     * collision.
     */
    private void updatePosition() {
        // player continually has a gravity
        if (!checkCollision(this.speedX, this.speedY + this.gravitySpeed)) {
            this.x += this.speedX;
            this.y += this.speedY + this.gravitySpeed;
        }

        // move player according to user control
        float possibleX = 0;
        float possibleY = 0;

        if (isMovingLeft) {
            possibleX = -playerSpeed;
            this.playerState = PlayerState.RUNNING_LEFT;
        } else if (isMovingRight) {
            possibleX = playerSpeed;
            this.playerState = PlayerState.RUNNING_RIGHT;
        } else {
            this.playerState = PlayerState.IDLE;
        }

        // check if possible move is valid before setting the values
        if (this.canMove(this.x + possibleX)) {
            if (!checkCollision(possibleX, possibleY)) {
                this.x += possibleX;
                this.y += possibleY;
            }
        }
    }

    /**
     * Checks collision with platform tiles and checks if {@link Player} reaches his
     * house. If {@link Player} reaches the house the user wins and
     * {@link GameModel} is updated.
     * 
     * @param x The x position to check
     * @param y The y position to check
     * @return true if {@link Player} is colliding with tiles, false otherwise
     */
    public boolean checkCollision(float x, float y) {
        Rectangle newHitBox = new Rectangle((int) (this.x + x), (int) (this.y + y), width, height);
        for (Tile tile : this.tileManager.getTileList()) {
            if (tile.isSolid()) {
                if (newHitBox.intersects(tile.getHitBox())) {
                    return true;
                }
            } else if (tile.isDogHouse()) {
                if (newHitBox.intersects(tile.getHitBox())) {
                    this.model.setGameState(GameState.GAME_WON);
                }
            }
        }
        return false;
    }

    /** Starts heart animation when {@link Player} loses one life. */
    public void loseOneLife() {
        this.heartBlink = true;
        this.drawAllHearts = false;
    }

    /** @return {@link PlayerState} of the player. */
    public PlayerState getPlayerState() {
        return this.playerState;
    }

    /** @return {@link Player} lives. */
    public int getPlayerLives() {
        return this.playerLives;
    }

    /**
     * Set player lives.
     * 
     * @param lives How many lives {@link Player} has
     */
    public void setPlayerLives(int lives) {
        this.playerLives = lives;
    }

    /**
     * Set {@link Player} left direction to true or false.
     * 
     * @param left true if player moves to the left, false otherwise
     */
    public void isMovingLeft(boolean left) {
        this.isMovingLeft = left;
    }

    /**
     * Set {@link Player} right direction to true or false.
     * 
     * @param right true if player moves to the right, false otherwise
     */
    public void isMovingRight(boolean right) {
        this.isMovingRight = right;
    }

    /**
     * Updates heart animation to blink sometimes before disappearing.
     */
    private void updateHeartAnimation() {
        if (heartBlink) {
            if (this.howManyBlinks >= 0) {
                showHeartCounter++;
                showHeart = true;
                if (showHeartCounter > 33) {
                    showHeart = false;
                    hideHeartCounter++;
                    if (hideHeartCounter > 30) {
                        showHeartCounter = 0;
                        showHeart = true;
                        hideHeartCounter = 0;
                        this.howManyBlinks--;
                    }
                }
            } else {
                heartBlink = false;
                this.howManyBlinks = 3;
                this.hearts--;
            }
        }
    }

    /** Load all player animation images. */
    private void loadPlayerAnimations() {
        animations = new BufferedImage[5][2];

        BufferedImage imgSitting1 = Inf101Graphics.loadImageFromResources("/player/dog_sitting_1.png");
        BufferedImage imgSitting2 = Inf101Graphics.loadImageFromResources("/player/dog_sitting_2.png");
        animations[0][0] = imgSitting1;
        animations[0][1] = imgSitting2;

        BufferedImage imgRunningLeft1 = Inf101Graphics.loadImageFromResources("/player/dog_running_left_1.png");
        BufferedImage imgRunningLeft2 = Inf101Graphics.loadImageFromResources("/player/dog_running_left_2.png");
        animations[1][0] = imgRunningLeft1;
        animations[1][1] = imgRunningLeft2;

        BufferedImage imgRunningRight1 = Inf101Graphics.loadImageFromResources("/player/dog_running_right_1.png");
        BufferedImage imgRunningRight2 = Inf101Graphics.loadImageFromResources("/player/dog_running_right_2.png");
        animations[2][0] = imgRunningRight1;
        animations[2][1] = imgRunningRight2;
    }

    /**
     * Load all hearts based on {@link Player} lives.
     */
    private void loadPlayerHearts() {
        playerHearts = new BufferedImage[playerLives];

        for (int i = 0; i < playerLives; i++) {
            BufferedImage heartImg = Inf101Graphics.loadImageFromResources("/objects/heart.png");
            playerHearts[i] = heartImg;
        }
    }
}
