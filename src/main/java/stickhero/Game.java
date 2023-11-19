package stickhero;

import java.io.Serializable;
import java.util.ArrayList;

public class Game implements Serializable {
    private Hero hero;
    private final ArrayList<Pillar> PILLARS = new ArrayList<>();
}
