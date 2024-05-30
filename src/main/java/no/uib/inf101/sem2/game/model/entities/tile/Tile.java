package no.uib.inf101.sem2.game.model.entities.tile;

import java.awt.image.BufferedImage;

import no.uib.inf101.sem2.game.model.entities.Entity;

/**
 * Tile sprite for the game that functions as platform.
 * Extends {@link Entity}.
 * 
 * @author Lilly Thi bui
 */
public class Tile extends Entity {
    private BufferedImage img;
    private Boolean isSolid = false;
    private Boolean isDogHouse = false;

    /**
     * Creates a new {@link Tile} with given values.
     * 
     * @param x          Th x position
     * @param y          The y position
     * @param width      The width of the {@link Tile}
     * @param height     The height of the {@link Tile}
     * @param img        The image of the {@link Tile}
     * @param isSolid    Boolean value which says if {@link Tile} is solid
     * @param isDogHouse Boolean value which says if {@link Tile} is dog house
     */
    public Tile(float x, float y, int width, int height, BufferedImage img, Boolean isSolid, Boolean isDogHouse) {
        super(x, y, width, height);
        this.img = img;
        this.isSolid = isSolid;
        this.isDogHouse = isDogHouse;
    }

    /** @return image of {@link Tile} */
    public BufferedImage getImage() {
        return this.img;
    }

    /** @return if {@link Tile} is solid */
    public boolean isSolid() {
        return this.isSolid;
    }

    /** @return if {@link Tile} is dog house */
    public boolean isDogHouse() {
        return this.isDogHouse;
    }
}
