package com.igorternyuk.seabattle.model;

import java.awt.*;
import java.util.*;
import java.util.List;

/**
 * Created by igor on 23.03.18.
 */
public abstract class AbstractPlayer {
    protected final Game game;
    protected final Navy navy = new Navy();
    protected final List<Point> shots = new ArrayList<>(Game.FIELD_SIZE * Game.FIELD_SIZE);
    protected final Map<ShipType, Integer> destroyedShips = new HashMap<>();
    protected final Random random = new Random();

    public AbstractPlayer(final Game game){
        this.game = game;
    }

    public Navy getNavy(){
        return this.navy;
    }

    public List<Point> getShots(){
        return Collections.unmodifiableList(this.shots);
    }

    public abstract void reset();

    public boolean isInPlay(){
        return this.navy.isAlive();
    }
}
