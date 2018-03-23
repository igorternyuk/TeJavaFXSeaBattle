package com.igorternyuk.seabattle.model;

import java.awt.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

/**
 * Created by igor on 22.03.18.
 */
public class Ship {
    private static final int MAX_NUMBER_OF_SHIP_CELLS = 4;
    private List<Cell> cells = new ArrayList<>(MAX_NUMBER_OF_SHIP_CELLS);
    private ShipDirection direction;
    private ShipType type;

    public Ship(final int x, final int y, final ShipType type, final ShipDirection direction) {
        this.direction = direction;
        this.type = type;
        final int length = type.getDeckNumber();
        for(int i = 0; i < length; ++i){
            if(this.direction.equals(ShipDirection.HORIZONTAL)){
                this.cells.add(new Cell(x + i, y));
            } else if(this.direction.equals(ShipDirection.VERTICAL)){
                this.cells.add(new Cell(x, y + i));
            }
        }
    }

    public ShipType getType(){
        return this.type;
    }

    public int getLength(){
        return this.type.getDeckNumber();
    }

    public ShipDirection getDirection(){
        return this.direction;
    }

    public List<Cell> getCells(){
        return Collections.unmodifiableList(this.cells);
    }

    public boolean isAlive(){
        return this.cells.stream().anyMatch(Cell::isAlive);
    }

    public boolean isVertical(){
        return this.direction.equals(ShipDirection.VERTICAL);
    }

    public boolean isHorizonal(){
        return this.direction.equals(ShipDirection.HORIZONTAL);
    }

    public long getNumberOfAliveCells(){
        return this.cells.stream().filter(Cell::isAlive).count();
    }

    public boolean hit(final int x, final int y){
        return this.cells.stream().anyMatch(cell -> cell.tryToHit(x, y));
    }

    public boolean isValidPosition(){
        return this.cells.stream().allMatch(cell -> Game.isValidPosition(cell.getX(), cell.getY()));
    }

    public boolean collides(final Cell other){
        return this.cells.stream().anyMatch(cell -> {
            final List<Point> neighbours = Game.getNeighboursMoore(cell.getPosition());
            return neighbours.stream().anyMatch(neighbour -> other.getPosition().equals(neighbour));
        });
    }

    public boolean collides(final Ship other){
        return other.getCells().stream().anyMatch(this::collides);
    }

    @Override
    public boolean equals(Object other) {
        if (this == other) return true;
        if (!(other instanceof Ship)) return false;
        final Ship otherShip = (Ship) other;
        return Objects.equals(this.cells, otherShip.getCells())
                && Objects.equals(this.direction, otherShip.getDirection())
                && getLength() == otherShip.getLength();
    }

    @Override
    public int hashCode() {
        final int prime = 73;
        int result = getCells().hashCode();
        result = prime * result + direction.hashCode();
        result = prime * result + getLength();
        return result;
    }
}
