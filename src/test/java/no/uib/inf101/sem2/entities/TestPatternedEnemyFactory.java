package no.uib.inf101.sem2.entities;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.awt.image.BufferedImage;

import org.junit.jupiter.api.Test;

import no.uib.inf101.sem2.game.model.entities.enemies.EnemyFactory;
import no.uib.inf101.sem2.game.view.Inf101Graphics;
import no.uib.inf101.sem2.helpMethods.CompareImages;

/** Testing the {@link PatternedEnemyFactory}. */
public class TestPatternedEnemyFactory {
    @Test
    public void sanityTestPatternedEnemyFactory() {
        String[] enemyFileList = { "/enemies/tomato.png", "/enemies/carrot.png" };
        EnemyFactory factory = new PatternedEnemyFactory(enemyFileList);

        BufferedImage tomatoImg = Inf101Graphics.loadImageFromResources("/enemies/tomato.png");
        BufferedImage carrotImg = Inf101Graphics.loadImageFromResources("/enemies/carrot.png");

        // first enemy is a tomato
        BufferedImage enemyImageToCompareWith = factory.getNext().getImage();
        // second enemy is a carrot
        BufferedImage enemyImageToCompareWith2 = factory.getNext().getImage();

        assertTrue(CompareImages.compareImages(tomatoImg, enemyImageToCompareWith));
        assertTrue(CompareImages.compareImages(carrotImg, enemyImageToCompareWith2));
    }
}