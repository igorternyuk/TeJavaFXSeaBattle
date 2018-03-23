package com.igorternyuk.seabattle.model;

import java.awt.*;
import java.awt.geom.Point2D;
import java.util.Objects;

/**
 * Created by igor on 22.03.18.
 */
public class Cell {
    private Point position;
    private boolean alive = true;

    public Cell(final int x, final int y) {
        this.position = new Point(x, y);
    }

    public int getX() {
        return this.position.x;
    }

    public int getY() {
        return this.position.y;
    }

    public Point getPosition(){
        return this.position;
    }

    public boolean isAlive() {
        return this.alive;
    }

    public boolean tryToHit(final int x, final int y){
        return tryToHit(new Point(x, y));
    }

    public boolean tryToHit(final Point2D point) {
        if(this.position.equals(point)){
            this.alive = false;
            return true;
        }
        return false;
    }

    @Override
    public boolean equals(Object other) {
        if (this == other) return true;
        if (!(other instanceof Cell)) return false;
        final Cell otherCell = (Cell) other;
        return Objects.equals(this.getPosition(), otherCell.getPosition())
                && isAlive() == otherCell.isAlive();
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = getPosition().hashCode();
        result = prime * result + (isAlive() ? 1 : 0);
        return result;
    }
}
