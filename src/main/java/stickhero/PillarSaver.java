package stickhero;

import java.io.Serializable;

// PillarSaver is Serializable as it needs to written to a file
public class PillarSaver implements Serializable {
    private final double x;
    private final double width;
    private final double bonusZoneX;
    private final double cherryX;
    private final boolean prevStickExists;

    // creates a pillar saver
    public PillarSaver(Pillar pillar) {
        // gets the x position and with of the pillar
        this.x = pillar.getCurrentX();
        this.width = pillar.getWidth();

        // checks if pillar bonus zone exists
        if (pillar.getBonusZone() != null) {
            // gets the x position of the bonus zone
            this.bonusZoneX = pillar.getBonusZone().getCurrentX();
        } else {
            // spawns bonus zone off-screen
            this.bonusZoneX = -50;
        }
        // checks if pillar cherry exists
        if (pillar.getCherry() != null) {
            // gets the x position of cherry
            this.cherryX = pillar.getCherry().getCurrentX();
        } else {
            // spawns cherry off-screen
            this.cherryX = 600;
        }

        this.prevStickExists = Utils.getBasePillar().getPrevStick() != null;

    }

    // creates a pillar based on the saved values
    public void createPillar(boolean isBase) {
        new Pillar(isBase, this.x, this.width, this.bonusZoneX, this.cherryX, this.prevStickExists);
    }
}
