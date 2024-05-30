package no.uib.inf101.sem2.game.model.entities.enemies;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

import no.uib.inf101.sem2.game.model.entities.Entity;
import no.uib.inf101.sem2.game.model.entities.tile.Tile;
import no.uib.inf101.sem2.game.model.entities.tile.TileManager;
import no.uib.inf101.sem2.game.model.states.ChefState;
import no.uib.inf101.sem2.game.view.Inf101Graphics;

/**
 * Chef sprite for the game.
 * Runs automatically horizontally after a certain amount of time to catch the
 * {@link Player}.
 * Extends {@link Entity}.
 * 
 * @author Lilly Thi bui
 */
public class Chef extends Entity {
    private BufferedImage[][] animations;
    private ChefState chefState = ChefState.IDLE;
    private int chefAction;

    // gravity
    private float gravitySpeed = 1;
    private float speedX = 0;
    private float speedY = 0;

    // moving chef
    private int timer = 0;
    private int startMovingAfterSec = 1000;
    private float chefSpeed = 1f;
    private boolean chefAlreadyStarted = false;
    private boolean moveChefLeft = false;
    private boolean moveChefRight = false;

    private TileManager tileManager;

    /**
     * Creates a new {@link Chef} with given values.
     * 
     * @param x      The x position
     * @param y      The y position
     * @param width  The width of the {@link Chef}
     * @param height The height of the {@link Chef}
     */
    public Chef(float x, float y, int width, int height) {
        super(x, y, width, height, 2);
        loadChefAnimations();
        this.tileManager = new TileManager();
    }

    /**
     * Calls all the methods to update position, collision detection and animations
     * of the {@link Chef}.
     */
    public void update() {
        updateHitBox();
        updatePosition();
        updateAnimationTick();
    }

    /**
     * Renders the {@link Chef} with correct animation image based on chef action.
     * 
     * @param g The Graphics component where we draw the {@link Chef}
     */
    public void render(Graphics g) {
        // get corresponding integer values based on state to get correct images
        if (this.chefState == ChefState.IDLE) {
            this.chefAction = 0;
        } else if (this.chefState == ChefState.RUNNING_LEFT) {
            this.chefAction = 1;
        } else if (this.chefState == ChefState.RUNNING_RIGHT) {
            this.chefAction = 2;
        }
        // draw chef
        g.drawImage(animations[chefAction][this.animationIndex], (int) x, (int) y, width, height, null);
    }

    /**
     * Updates {@link Chef}'s position based on gravity, moving direction and tile
     * collision.
     * Chef starts running after a certain amount of time.
     */
    private void updatePosition() {
        // chef continually has a gravity
        if (!checkCollision(this.speedX, this.speedY + this.gravitySpeed)) {
            this.x += this.speedX;
            this.y += this.speedY + this.gravitySpeed;
        }

        // start moving chef after a certain time
        if (!chefAlreadyStarted) {
            timer++;
            if (timer > startMovingAfterSec) {
                moveChefRight = true;
                chefAlreadyStarted = true;
            }
        }

        // moves chef in one direction and goes the other direction if he hits the wall
        if (moveChefRight) {
            float possibleX = 0;
            this.chefState = ChefState.RUNNING_RIGHT;
            possibleX += chefSpeed;
            // move chef if it is a valid move
            if (this.canMove(this.x + possibleX)) {
                if (!checkCollision(possibleX, 0)) {
                    this.x += possibleX;
                }
            } else {
                // change direction
                moveChefRight = false;
                moveChefLeft = true;
            }
        } else if (moveChefLeft) {
            float possibleX = 0;
            this.chefState = ChefState.RUNNING_LEFT;
            possibleX -= chefSpeed;
            // move chef if it is a valid move
            if (this.canMove(this.x + possibleX)) {
                if (!checkCollision(possibleX, 0)) {
                    this.x += possibleX;
                }
            } else {
                // change direction
                moveChefLeft = false;
                moveChefRight = true;
            }
        }
    }

    /**
     * Checks collision with platform tiles.
     * 
     * @param x The x position to check
     * @param y The y position to check
     * @return true if {@link Chef} is colliding with tiles, false otherwise
     */
    private boolean checkCollision(float x, float y) {
        // create a new possible hitbox
        Rectangle newHitBox = new Rectangle((int) (this.x + x), (int) (this.y + y), width, height);
        // check if the new hitbox intersects with the tiles
        for (Tile tile : this.tileManager.getTileList()) {
            if (tile.isSolid()) {
                if (newHitBox.intersects(tile.getHitBox())) {
                    return true;
                }
            }
        }
        return false;
    }

    /** Load all animation images. */
    private void loadChefAnimations() {
        animations = new BufferedImage[5][2];

        BufferedImage imgIdle1 = Inf101Graphics.loadImageFromResources("/enemies/chef_idle_1.png");
        BufferedImage imgIdle2 = Inf101Graphics.loadImageFromResources("/enemies/chef_idle_2.png");
        animations[0][0] = imgIdle1;
        animations[0][1] = imgIdle2;

        BufferedImage imgRunningLeft1 = Inf101Graphics.loadImageFromResources("/enemies/chef_running_left-1.png");
        BufferedImage imgRunningLeft2 = Inf101Graphics.loadImageFromResources("/enemies/chef_running_left-2.png");
        animations[1][0] = imgRunningLeft1;
        animations[1][1] = imgRunningLeft2;

        BufferedImage imgRunningRight1 = Inf101Graphics.loadImageFromResources("/enemies/chef_running_right-1.png");
        BufferedImage imgRunningRight2 = Inf101Graphics.loadImageFromResources("/enemies/chef_running_right-2.png");
        animations[2][0] = imgRunningRight1;
        animations[2][1] = imgRunningRight2;
    }
}
