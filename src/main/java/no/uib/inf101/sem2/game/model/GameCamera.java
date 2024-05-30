package no.uib.inf101.sem2.game.model;

import static no.uib.inf101.sem2.game.view.GameView.GAME_HEIGHT;

import no.uib.inf101.sem2.game.model.entities.player.Player;

/**
 * GameCamera of the game which gets updated based on {@link Player}.
 * The GameCamera will follow the {@link Player}'s movements.
 * <p>
 * <h4>SOURCE CODE:</h4>
 * <ul>
 * <li>Author: RealTutsGML</li>
 * <li>Date: 28.11.2013</li>
 * <li>Title: Java Game Programming #10 - Camera Class</li>
 * <li>URL: https://www.youtube.com/watch?v=vdcOIwkB6dA</li>
 * </ul>
 */
public class GameCamera {
    private float x, y;

    /**
     * Create a new {@link GameCamera} with given values.
     * 
     * @param x The x position of the {@link GameCamera}
     * @param y The y position of the {@link GameCamera}
     */
    public GameCamera(float x, float y) {
        this.x = x;
        this.y = y;
    }

    /**
     * Update y position of {@link GameCamera} based on y position of
     * {@link Player}.
     * 
     * @param player The {@link Player}
     */
    public void tick(Player player) {
        this.y = -player.getY() + GAME_HEIGHT / 2;
    }

    /** Set x position of {@link GameCamera} */
    public void setX(float x) {
        this.x = x;
    }

    /** Set y position of {@link GameCamera} */
    public void setY(float y) {
        this.y = y;
    }

    /** @return x position of {@link GameCamera} */
    public float getX() {
        return x;
    }

    /** @return y position of {@link GameCamera} */
    public float getY() {
        return y;
    }
}