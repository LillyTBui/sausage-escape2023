package no.uib.inf101.sem2.game.model.entities;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;

import static no.uib.inf101.sem2.game.view.GameView.GAME_WIDTH;

/**
 * General entity object.
 * Has some properties like x, y, width, height and hitbox.
 * 
 * @author Lilly Thi bui
 */
public class Entity {
    protected float x, y;
    protected int width, height;
    protected Rectangle hitBox;

    // animations
    protected int animationTick, animationIndex, animationSpeed = 60;
    protected int maxSpriteImage = 0;

    /**
     * Creates a new {@link Entity} with given values.
     * 
     * @param x      The x position
     * @param y      The y position
     * @param width  The width of the {@link Entity}
     * @param height The height of the {@link Entity}
     */
    public Entity(float x, float y, int width, int height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;

        this.hitBox = new Rectangle((int) x, (int) y, width, height);
    }

    /**
     * Creates a new {@link Entity} with given values.
     * 
     * @param x              The x position
     * @param y              The y position
     * @param width          The width of the {@link Entity}
     * @param height         The height of the {@link Entity}
     * @param maxSpriteImage The maximum number of sprite images of {@link Entity}
     */
    public Entity(float x, float y, int width, int height, int maxSpriteImage) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.maxSpriteImage = maxSpriteImage;

        this.hitBox = new Rectangle((int) x, (int) y, width, height);
    }

    /** Update hitbox of {@link Entity} */
    public void updateHitBox() {
        hitBox.x = (int) this.x;
        hitBox.y = (int) this.y;
    }

    /**
     * Draw hitbox for debugging.
     * 
     * @param g The Graphics component where we draw the hitbox of {@link Entity}
     */
    protected void drawHitBox(Graphics g) {
        g.setColor(Color.PINK);
        g.drawRect(hitBox.x, hitBox.y, hitBox.width, hitBox.height);
    }

    /**
     * Updates {@link Entity} animation index based on animation tick.
     * Used to create image animation.
     * <p>
     * <h4>SOURCE CODE:</h4>
     * <ul>
     * <li>Author: Kaarin Gaming</li>
     * <li>Date: 04.02.2022</li>
     * <li>Title: ANIMATIONS - Episode #05 - Platformer Tutorial Java</li>
     * <li>URL:
     * https://www.youtube.com/watch?v=nuRXTWJ66vc&list=PL4rzdwizLaxYmltJQRjq18a9gsSyEQQ-0&index=6</li>
     * </ul>
     */
    protected void updateAnimationTick() {
        animationTick++;
        if (animationTick >= animationSpeed) {
            animationTick = 0;
            animationIndex++;
            if (animationIndex >= this.maxSpriteImage) {
                animationIndex = 0;
            }
        }
    }

    /**
     * Checks if {@link Entity} is within the game width boundaries.
     * 
     * @param x The x position of {@link Entity}
     * @return true if {@link Entity} is within boundaries, false otherwise
     */
    public boolean canMove(float x) {
        if (x < 0 || x + this.width > GAME_WIDTH) {
            return false;
        }
        return true;
    }

    /** @return hitbox of {@link Entity} */
    public Rectangle getHitBox() {
        return this.hitBox;
    }

    /** @return x position of {@link Entity} */
    public float getX() {
        return this.x;
    }

    /** Set x position of {@link Entity} */
    public void setX(float x) {
        this.x = x;
    }

    /** @return y position of {@link Entity} */
    public float getY() {
        return this.y;
    }

    /** Set y position of {@link Entity} */
    public void setY(float y) {
        this.y = y;
    }

    /** @return width of {@link Entity} */
    public float getWidth() {
        return this.width;
    }

    /** @return height of {@link Entity} */
    public float getHeight() {
        return this.height;
    }
}