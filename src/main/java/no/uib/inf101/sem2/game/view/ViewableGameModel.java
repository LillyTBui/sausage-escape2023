package no.uib.inf101.sem2.game.view;

import java.util.List;

import no.uib.inf101.sem2.game.model.GameCamera;
import no.uib.inf101.sem2.game.model.entities.enemies.Chef;
import no.uib.inf101.sem2.game.model.entities.enemies.Enemy;
import no.uib.inf101.sem2.game.model.entities.player.Player;
import no.uib.inf101.sem2.game.model.entities.tile.TileManager;
import no.uib.inf101.sem2.game.model.states.GameState;

/**
 * Methods needed to view the game.
 * Includes getting all the game objects to draw them in the game.
 * 
 * @author Lilly Thi Bui
 */
public interface ViewableGameModel {
    /** @return the {@link GameCamera} of the model */
    GameCamera getGameCamera();

    /** @return the {@link GameState} of the model */
    GameState getGameState();

    /** @return the {@link TileManager} of the model */
    TileManager getTileManager();

    /** @return the {@link Player} of the model */
    Player getPlayer();

    /** @return the {@link Chef} of the model */
    Chef getChef();

    /** @return the list of {@link Enemy} of the model */
    List<Enemy> getEnemyList();
}
