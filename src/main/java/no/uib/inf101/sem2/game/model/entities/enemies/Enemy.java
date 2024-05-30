package no.uib.inf101.sem2.game.model.entities.enemies;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import no.uib.inf101.sem2.game.model.entities.Entity;
import static no.uib.inf101.sem2.game.view.GameView.GAME_WIDTH;

/**
 * Enemy sprite for the game.
 * Falls down from the sky and can hit {@link Player}.
 * Extends {@link Entity}.
 * 
 * @author Lilly Thi bui
 */
public class Enemy extends Entity {
    private BufferedImage img;
    private float enemySpeed = 0.5f;

    /**
     * Creates a new {@link Enemy} with given values.
     * 
     * @param x      The x position
     * @param y      The y position
     * @param width  The width of {@link Enemy}
     * @param height The height of {@link Enemy}
     * @param img    The image of {@link Enemy}
     */
    public Enemy(float x, float y, int width, int height, BufferedImage img) {
        super(x, y, width, height);
        this.img = img;
    }

    /**
     * Calls all the methods to update {@link Enemy} movements and collision
     * detection.
     */
    public void update() {
        updateHitBox();
        moveEnemy();
    }

    /**
     * Renders {@link Enemy}.
     * 
     * @param g The Graphics component where we draw the enemy
     */
    public void render(Graphics g) {
        g.drawImage(img, (int) x, (int) y, width, height, null);
    }

    /**
     * Moves {@link Enemy} automatically vertically.
     */
    private void moveEnemy() {
        this.y += enemySpeed;
    }

    /**
     * Places {@link Enemy} at a random position. The x position is calculated
     * randomly.
     * 
     * @param y The current {@link Enemy} y position
     */
    public void placeAtRandomPosition(float y) {
        int randomXPosition = (int) (Math.random() * (GAME_WIDTH - this.height));
        this.x = randomXPosition;
        this.y = y;
    }

    /** @return The current {@link Enemy} y position. */
    public float getEnemyYPosition() {
        return this.y;
    }

    /** @return The {@link Enemy} image. */
    public BufferedImage getImage() {
        return this.img;
    }
}
