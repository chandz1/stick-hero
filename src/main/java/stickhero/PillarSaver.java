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
        if (pillar.getBonusZone() != null) {
            this.bonusZoneX = pillar.getBonusZone().getCurrentX();
        } else {
            this.bonusZoneX = 600;
        }
        if (pillar.getCherry() != null) {
            this.cherryX = pillar.getCherry().getCurrentX();
        } else {
            this.cherryX = 600;
        }

    }

    public void createPillar(boolean isBase) {
        new Pillar(isBase, this.x, this.width, this.bonusZoneX, this.cherryX);
    }
}
