package no.uib.inf101.sem2.game.model.entities.enemies;

import java.awt.image.BufferedImage;

import no.uib.inf101.sem2.game.view.Inf101Graphics;

/**
 * Creates random {@link Enemy}.
 * Implements {@link EnemyFactory}.
 * 
 * @author Lilly Thi Bui
 */
public class RandomEnemyFactory implements EnemyFactory {
    @Override
    public Enemy getNext() {
        String[] possibleEnemies = { "/enemies/tomato.png", "/enemies/carrot.png", "/enemies/broccoli.png" };
        // generate a random number between 0 and 2
        int randomNumber = (int) (Math.random() *
                possibleEnemies.length);
        String randomEnemy = possibleEnemies[randomNumber];
        BufferedImage image = Inf101Graphics.loadImageFromResources(randomEnemy);
        Enemy newEnemy = new Enemy(0, 0, 36, 36, image);
        return newEnemy;
    }
}
