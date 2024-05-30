package no.uib.inf101.sem2;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import no.uib.inf101.sem2.game.model.GameCamera;
import no.uib.inf101.sem2.game.model.GameModel;
import no.uib.inf101.sem2.game.model.entities.enemies.Chef;
import no.uib.inf101.sem2.game.model.entities.enemies.Enemy;
import no.uib.inf101.sem2.game.model.entities.enemies.EnemyFactory;
import no.uib.inf101.sem2.game.model.entities.enemies.RandomEnemyFactory;
import no.uib.inf101.sem2.game.model.entities.player.Player;
import no.uib.inf101.sem2.game.model.entities.tile.TileManager;
import no.uib.inf101.sem2.game.model.states.GameState;

/** Test the {@link GameModel}. */
public class TestGameModel {
    @Test
    public void sanityTestGameModel() {
        GameModel model = new GameModel();
        Player player = new Player(300, 0, 64, 64, model);
        Chef chef = new Chef(0, 0, 64, 64);
        EnemyFactory enemyFactory = new RandomEnemyFactory();
        TileManager tileManager = new TileManager();
        GameCamera camera = new GameCamera(0, 0);

        GameModel model2 = new GameModel(player, chef, enemyFactory, tileManager, camera);

        assertEquals(player, model2.getPlayer());
        assertEquals(chef, model2.getChef());
        assertEquals(tileManager, model2.getTileManager());
        assertEquals(camera, model2.getGameCamera());
    }

    @Test
    public void testStartGame() {
        GameModel model = new GameModel();

        assertEquals(GameState.GAME_START, model.getGameState());
    }

    @Test
    public void testNewGame() {
        // test the reset values before starting a new game
        GameModel model = new GameModel();
        model.newGame();

        assertEquals(GameState.GAME_START, model.getGameState());
        // all the enemies should be cleared when starting a new game
        assertEquals(0, model.getEnemyList().size());
    }

    @Test
    public void testPlayerMove() {
        // test movements of player
        GameModel model = new GameModel();

        // player cannot go outside the game window
        assertFalse(model.getPlayer().canMove(1000));
        assertFalse(model.getPlayer().canMove(-1000));

        // player can move 1 step to the left
        float x = model.getPlayer().getX() - 1;
        assertTrue(model.getPlayer().canMove(x));

        // player can move 1 step to the right
        float x2 = model.getPlayer().getX() + 1;
        assertTrue(model.getPlayer().canMove(x2));
    }

    @Test
    public void testGameEndsWhenPlayerHitsChef() {
        // checks if game ends with player is caught by chef
        GameModel model = new GameModel();

        // when the game begins the chef and player are not colliding
        assertFalse(model.playerIsTakenByChef());

        // move the chef to the same x position as the player
        model.getChef().setX(300);
        model.getChef().updateHitBox();
        // make sure the game state is active so that the model can be updated
        model.setGameState(GameState.ACTIVE_GAME);
        model.update();

        // chef and player colliding and the game ends
        assertTrue(model.playerIsTakenByChef());
        // check that game ends when player hits chef
        assertEquals(GameState.GAME_OVER, model.getGameState());
    }

    @Test
    public void testEnemyGetCreated() {
        // test if new enemy get created
        GameModel model = new GameModel();
        model.createNewEnemy();

        // check if one enemy was created
        assertEquals(1, model.getEnemyList().size());
        model.createNewEnemy();
        // check if there is two enemies
        assertEquals(2, model.getEnemyList().size());

        // check if all the enemies were removed
        model.removeOldEnemies();
        model.removeOldEnemies();
        assertEquals(0, model.getEnemyList().size());

        // check that the model does not try to remove enemies when there is no enemies
        model.removeOldEnemies();
        assertEquals(0, model.getEnemyList().size());
    }

    @Test
    public void testPlayerHitByEnemy() {
        // test if player is hit by enemy
        GameModel model = new GameModel();
        model.createNewEnemy();

        // checks that one enemy got created
        assertEquals(1, model.getEnemyList().size());

        // change position of enemy to hit player
        for (Enemy enemy : model.getEnemyList()) {
            enemy.setX(300);
            enemy.setY(0);
            enemy.updateHitBox();
        }

        // checks that player got hit
        assertTrue(model.playerIsHitByEnemy());
        // checks that the enemy is removed when hitting the player
        assertEquals(0, model.getEnemyList().size());
    }

    @Test
    public void testPlayerLivesGetsReducedWhenEnemyHit() {
        // test if player's life get reduced when hit by enemy
        GameModel model = new GameModel();
        model.createNewEnemy();

        // checks that one enemy got created
        assertEquals(1, model.getEnemyList().size());

        // change position of enemy to hit player
        for (Enemy enemy : model.getEnemyList()) {
            enemy.setX(300);
            enemy.setY(0);
            enemy.updateHitBox();
        }
        // in the model update method the player should be hit by an enemy and one life
        // should be reduced
        model.setGameState(GameState.ACTIVE_GAME);
        model.update();
        // need to change playerIsAlreadyHit to false or else the player will not be hit
        model.setPlayerIsAlreadyHit(false);

        // checks that player loses one life
        assertEquals(2, model.getPlayer().getPlayerLives());

        // create two more enemies and get hit to check if game ends
        model.createNewEnemy();
        model.createNewEnemy();

        for (Enemy enemy : model.getEnemyList()) {
            enemy.setX(300);
            enemy.setY(0);
            enemy.updateHitBox();
        }

        model.update();
        model.setPlayerIsAlreadyHit(false);
        model.update();
        // checks that player has lost 3 lives
        assertEquals(0, model.getPlayer().getPlayerLives());
        // checks that the game ends
        assertEquals(GameState.GAME_OVER, model.getGameState());
    }
}
