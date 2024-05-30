package no.uib.inf101.sem2.game.model;

import java.util.ArrayList;
import java.util.List;

import no.uib.inf101.sem2.game.controller.ControllableModel;
import no.uib.inf101.sem2.game.model.entities.enemies.Chef;
import no.uib.inf101.sem2.game.model.entities.enemies.Enemy;
import no.uib.inf101.sem2.game.model.entities.enemies.EnemyFactory;
import no.uib.inf101.sem2.game.model.entities.enemies.RandomEnemyFactory;
import no.uib.inf101.sem2.game.model.entities.player.Player;
import no.uib.inf101.sem2.game.model.entities.tile.TileManager;
import no.uib.inf101.sem2.game.model.states.GameState;
import no.uib.inf101.sem2.game.view.ViewableGameModel;

import static no.uib.inf101.sem2.game.view.GameView.GAME_HEIGHT;

/**
 * GameModel of the game.
 * Handles game functionality including {@link Player}, {@link Chef},
 * {@link EnemyFactory}, {@link TileManager} and {@link GameState}.
 * Implements {@link ViewableGameModel} and {@link ControllableModel}.
 * 
 * @author Lilly Thi bui
 */
public class GameModel implements ViewableGameModel, ControllableModel {
    private GameState gameState = GameState.GAME_START;

    private Player player;
    private Chef chef;
    private EnemyFactory enemyFactory;
    private List<Enemy> enemyList = new ArrayList<Enemy>();
    private TileManager tileManager;
    private GameCamera camera;

    // enemies timer
    private int timeToCreateNewEnemy = 700;
    private int timeToRemoveOldEnemy = 5000;

    // player and chef variables
    private boolean playerIsAlreadyHit = false;
    private int playerSize = 45;
    private int chefSize = 64;

    // timer
    private int timerDelay = 7;

    // platform
    private int platformHeight = 1370;

    /**
     * Creates a {@link GameModel} with default values.
     */
    public GameModel() {
        this.player = new Player(300, 0, playerSize, playerSize, this);
        this.chef = new Chef(0, 0, chefSize, chefSize);
        this.enemyFactory = new RandomEnemyFactory();
        this.tileManager = new TileManager();
        this.camera = new GameCamera(0, 0);
    }

    /**
     * Creates a {@link GameModel} with given values. For testing purposes.
     * 
     * @param player       The {@link Player} of the game
     * @param chef         The {@link Chef} of the game
     * @param enemyFactory The {@link EnemyFactory} to create {@link Enemy}
     * @param tileManager  The {@link TileManager} to create game platform
     * @param gameCamera   The {@link GameCamera} to control what we see
     */
    public GameModel(Player player, Chef chef, EnemyFactory enemyFactory,
            TileManager tileManager, GameCamera gameCamera) {
        this.player = player;
        this.chef = chef;
        this.enemyFactory = enemyFactory;
        this.tileManager = tileManager;
        this.camera = gameCamera;
    }

    @Override
    public void update() {
        if (this.gameState == GameState.ACTIVE_GAME) {
            // stop the game camera when the player reaches the end of platform
            if (this.player.getY() < this.platformHeight) {
                this.camera.tick(this.getPlayer());
            }
            // checks if player is hit by enemy.
            if (playerIsHitByEnemy() && !playerIsAlreadyHit) {
                playerIsAlreadyHit = true;
                // if player is hit by an enemy, the player loses one life.
                int currentPlayerLives = this.player.getPlayerLives();
                currentPlayerLives--;
                this.player.loseOneLife();
                this.player.setPlayerLives(currentPlayerLives);
                // if the player has 0 lives left the game is over.
                if (this.player.getPlayerLives() == 0) {
                    this.gameState = GameState.GAME_OVER;
                }
            }

            // checks if player is taken by the chef
            if (playerIsTakenByChef()) {
                this.gameState = GameState.GAME_OVER;
            }

            // handles when a new enemy is created
            timeToCreateNewEnemy--;
            if (timeToCreateNewEnemy == 0) {
                createNewEnemy();
                timeToCreateNewEnemy = 700;
            }

            // handles when an old enemy should be removed
            timeToRemoveOldEnemy--;
            if (timeToRemoveOldEnemy == 0) {
                removeOldEnemies();
                timeToRemoveOldEnemy = 5000;
            }

            this.player.update();
            this.chef.update();

            // update enemies if there are enemies in the list
            if (this.enemyList.size() > 0) {
                for (Enemy enemy : this.enemyList) {
                    enemy.update();
                }
            }
        }
    }

    /** @return true if {@link Player} is taken by {@link Chef}, false otherwise. */
    public boolean playerIsTakenByChef() {
        if (this.player.getHitBox().intersects(this.chef.getHitBox())) {
            return true;
        }
        return false;
    }

    /**
     * @return true if {@link Player} is hit by {@link Enemy}, false otherwise.
     *         If {@link Player} is hit the {@link Enemy} is removed.
     */
    public boolean playerIsHitByEnemy() {
        for (Enemy enemy : this.enemyList) {
            if (this.player.getHitBox().intersects(enemy.getHitBox())) {
                this.enemyList.remove(enemy);
                return true;
            }
        }
        playerIsAlreadyHit = false;
        return false;
    }

    /** Creates a new enemy at a random position and add it to the enemy list */
    public void createNewEnemy() {
        Enemy enemy = enemyFactory.getNext();
        // makes sure that enemies start from top of game window even if the player
        // moves further down
        float topOfGameWindow = this.player.getY() - (GAME_HEIGHT / 2);
        enemy.placeAtRandomPosition(topOfGameWindow);
        this.enemyList.add(enemy);
    }

    /** Removes the oldest {@link Enemy} from the list. */
    public void removeOldEnemies() {
        if (this.enemyList != null && this.enemyList.size() > 0) {
            // remove the first enemy in the list which is the oldest
            this.enemyList.remove(0);
        }
    }

    /** Set PlayerIsAlreadyHit for testing purpose. */
    public void setPlayerIsAlreadyHit(boolean hit) {
        this.playerIsAlreadyHit = hit;
    }

    @Override
    public GameState getGameState() {
        return this.gameState;
    }

    @Override
    public void setGameState(GameState state) {
        this.gameState = state;
    }

    @Override
    public void startGame() {
        this.gameState = GameState.ACTIVE_GAME;
    }

    @Override
    public void newGame() {
        this.gameState = GameState.GAME_START;
        this.player = new Player(300, 0, playerSize, playerSize, this);
        this.chef = new Chef(0, 0, chefSize, chefSize);
        this.enemyList.clear();
        this.camera = new GameCamera(0, 0);
    }

    @Override
    public Player getPlayer() {
        return this.player;
    }

    @Override
    public TileManager getTileManager() {
        return this.tileManager;
    }

    @Override
    public Chef getChef() {
        return this.chef;
    }

    @Override
    public List<Enemy> getEnemyList() {
        return this.enemyList;
    }

    @Override
    public GameCamera getGameCamera() {
        return this.camera;
    }

    @Override
    public int getTimerDelay() {
        return this.timerDelay;
    }
}