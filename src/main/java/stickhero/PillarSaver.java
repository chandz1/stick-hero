package stickhero;

import java.io.Serializable;

public class PillarSaver implements Serializable {
    private double x;
    private double width;
    private double bonusZoneX;
    private double cherryX;
    public PillarSaver(Pillar pillar) {
        this.x = pillar.getCurrentX();
        this.width = pillar.getWidth();
        this.bonusZoneX = pillar.getBonusZone().getCurrentX();
        this.cherryX = pillar.getCherry().getCurrentX();
    }

    public Pillar createPillar(boolean isBase) {
        return new Pillar(isBase, this.x, this.width, this.bonusZoneX, this.cherryX);
    }
}
