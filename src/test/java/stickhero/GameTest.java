package stickhero;

import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertTrue;

@RunWith(JUnit4.class)
public class GameTest {

    @BeforeClass
    public static void initTestData() {
        Pane pane = new Pane();
        Utils.setPane(pane);
        Utils.setBasePillar(new Pillar(true));
    }
    @Test
    public void basePillarWidthTest() {
        Pillar pillar = new Pillar(true);
        assertEquals(pillar.getWidth(), 100.0);
    }

    @Test
    public void randomPillarWidthTest() {
        for (int i = 0; i < 1000; i++) {
            assertTrue(new Pillar(false).getWidth() < 300);
        }
    }

    @Test
    public void pillarsCombinedWidthTest() {
        Pillar basePillar = new Pillar(true);
        Pillar randPillar = new Pillar(false);
        assertTrue(randPillar.getWidth() + basePillar.getWidth() < 600);
    }

    @Test
    public void heroSpawnPositionTest() {
        Hero hero = new Hero();
        assertTrue(hero.getCurrentX() < 100);
    }
}