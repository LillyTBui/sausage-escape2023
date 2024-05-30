package no.uib.inf101.sem2.entities;

import java.awt.image.BufferedImage;
import no.uib.inf101.sem2.game.model.entities.enemies.Enemy;
import no.uib.inf101.sem2.game.model.entities.enemies.EnemyFactory;
import no.uib.inf101.sem2.game.view.Inf101Graphics;

/**
 * Patterned enemy factory to get specific {@link Enemy} for testing purposes.
 * Implements {@link EnemyFactory}.
 * 
 * @author Lilly Thi Bui
 */
public class PatternedEnemyFactory implements EnemyFactory {
    private String[] enemyStringList;
    private int stringIndex = -1;

    /**
     * Creates a {@link PatternedEnemyFactory} based on enemy list.
     * 
     * @param enemyStringList with {@link Enemy} url image strings
     */
    public PatternedEnemyFactory(String[] enemyStringList) {
        this.enemyStringList = enemyStringList;
    }

    @Override
    public Enemy getNext() {
        if (stringIndex < this.enemyStringList.length - 1) {
            stringIndex++;
        } else {
            stringIndex = 0;
        }
        String enemyString = this.enemyStringList[stringIndex];
        BufferedImage image = Inf101Graphics.loadImageFromResources(enemyString);
        Enemy newEnemy = new Enemy(0, 0, 36, 36, image);
        return newEnemy;
    }

}
