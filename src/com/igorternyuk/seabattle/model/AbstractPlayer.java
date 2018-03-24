package com.igorternyuk.seabattle.model;

import java.awt.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

/**
 * Created by igor on 23.03.18.
 */
public abstract class AbstractPlayer {
    protected final Game game;
    final Navy navy = new Navy();
    final List<Point> shots = new ArrayList<>(Game.FIELD_SIZE * Game.FIELD_SIZE);

    final Random random = new Random();

    AbstractPlayer(final Game game) {
        this.game = game;
    }

    public Navy getNavy(){
        return this.navy;
    }

    public List<Point> getShots(){
        return Collections.unmodifiableList(this.shots);
    }

    List<Ship> getDestroyedShips() {
        return this.navy.getDestoyedShips();
    }

    public abstract void reset();

    boolean isInPlay() {
        return this.navy.isAlive();
    }
}
