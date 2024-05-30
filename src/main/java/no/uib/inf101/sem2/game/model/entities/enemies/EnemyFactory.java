package no.uib.inf101.sem2.game.model.entities.enemies;

/**
 * Method needed to get a new {@link Enemy}.
 * 
 * @author Lilly Thi Bui
 */
public interface EnemyFactory {
    /**
     * Returns an Enemy object
     * 
     * @return Enemy object
     */
    Enemy getNext();
}
