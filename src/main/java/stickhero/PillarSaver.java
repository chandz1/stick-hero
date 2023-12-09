package stickhero;

import java.io.Serializable;

public class PillarSaver implements Serializable {
    private final double x;
    private final double width;
    private final double bonusZoneX;
    private final double cherryX;
    public PillarSaver(Pillar pillar) {
        this.x = pillar.getCurrentX();
        this.width = pillar.getWidth();
        this.bonusZoneX = pillar.getBonusZone().getCurrentX();
        this.cherryX = pillar.getCherry().getCurrentX();
    }

    public void createPillar(boolean isBase) {
        new Pillar(isBase, this.x, this.width, this.bonusZoneX, this.cherryX);
    }
}
