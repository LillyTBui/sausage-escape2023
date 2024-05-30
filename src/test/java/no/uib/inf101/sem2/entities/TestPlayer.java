package no.uib.inf101.sem2.entities;

import org.junit.jupiter.api.Test;

import no.uib.inf101.sem2.game.model.GameModel;
import no.uib.inf101.sem2.game.model.entities.player.Player;
import no.uib.inf101.sem2.game.model.states.PlayerState;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.awt.Rectangle;

/** Testing the {@link Player}. */
public class TestPlayer {
    @Test
    public void sanityTestPlayer() {
        GameModel model = new GameModel();
        Player player = new Player(400, 40, 64, 64, model);

        // Position
        assertEquals(400, player.getX());
        assertEquals(40, player.getY());

        // width/height
        assertEquals(64, player.getWidth());
        assertEquals(64, player.getHeight());

        // hitbox
        Rectangle hitBox = new Rectangle(400, 40, 64, 64);
        assertEquals(hitBox, player.getHitBox());

        // player lives
        assertEquals(3, player.getPlayerLives());
    }

    @Test
    public void testPlayerImages() {
        /**
         * How to check that the correct player images are displayed
         * 1. Run the program
         * 2. Click play
         * 3. See that a dog is sitting facing you
         * 4. Hold the right arrow button and see that a dog is running to the right
         * 5. Hold the left arrow button and see that a dog is running to the left
         * 6. Check that if you are not holding any button the dog should be sitting
         * facing you
         */
    }

    @Test
    public void testPlayerHitPlatform() {
        // Checks if player hit platform
        GameModel model = new GameModel();
        Player player = new Player(0, 0, 64, 64, model);

        // initial position the player is not on a platform
        assertFalse(player.checkCollision(player.getX(), player.getY()));

        // try to move player into a platform img
        float y = 100;
        assertTrue(player.checkCollision(player.getX(), y));
    }

    @Test
    public void testPlayerGravity() {
        // Checks player gravity
        GameModel model = new GameModel();
        Player player = new Player(0, 0, 64, 64, model);
        float initialPlayerY = player.getY();

        // the update method continually updates player's gravity by playerSpeed = 1
        player.update();

        // checks that player y value has changed because of gravity
        assertNotEquals(initialPlayerY, player.getY());
    }

    @Test
    public void testPlayerMoveRight() {
        // Checks if player can move right
        GameModel model = new GameModel();
        Player player = new Player(0, 0, 64, 64, model);
        float initialXpos = player.getX();

        // set moving right to true
        player.isMovingRight(true);
        // update player
        player.update();
        // check that playerState is updated
        assertEquals(PlayerState.RUNNING_RIGHT, player.getPlayerState());
        // the player should be moved 1 step to the right
        assertNotEquals(initialXpos, player.getX());
        assertEquals(1, player.getX());

        // update player
        player.update();
        player.update();
        player.update();
        // player should now be moved 3 more steps
        assertEquals(4, player.getX());
    }

    @Test
    public void testPlayerMoveLeft() {
        // Checks if player can move left
        GameModel model = new GameModel();
        Player player = new Player(300, 0, 64, 64, model);
        float initialXpos = player.getX();

        // set moving left to true
        player.isMovingLeft(true);
        // update player
        player.update();
        // check that playerState is updated
        assertEquals(PlayerState.RUNNING_LEFT, player.getPlayerState());
        // the player should be moved 1 step to the left
        assertNotEquals(initialXpos, player.getX());
        assertEquals(299, player.getX());

        // update player
        player.update();
        player.update();
        player.update();
        // player should now be moved 3 more steps
        assertEquals(296, player.getX());
    }
}
