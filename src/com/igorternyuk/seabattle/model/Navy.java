package com.igorternyuk.seabattle.model;

import java.util.*;

/**
 * Created by igor on 22.03.18.
 */
public class Navy {
    private static final int SHIPS_TOTAL = 10;
    private static final int CELLS_TOTAL = 20;
    private static final Map<ShipType, Integer> shipsPattern = createShipsPattern();
    private final List<Ship> ships = new ArrayList<>();
    private final ShipType[] shipPlacementOrder = { ShipType.FOUR_DECKED, ShipType.THREE_DECKED, ShipType.THREE_DECKED,
            ShipType.TWO_DECKED, ShipType.TWO_DECKED, ShipType.TWO_DECKED, ShipType.ONE_DECKED, ShipType.ONE_DECKED,
            ShipType.ONE_DECKED, ShipType.ONE_DECKED};
    private int currentShipIndex = 0;

    private static Map<ShipType, Integer> createShipsPattern() {
        Map<ShipType, Integer> pattern = new HashMap<>();
        pattern.put(ShipType.FOUR_DECKED, 1);
        pattern.put(ShipType.THREE_DECKED, 2);
        pattern.put(ShipType.TWO_DECKED, 3);
        pattern.put(ShipType.ONE_DECKED, 4);
        return pattern;
    }

    public List<Ship> getShips(){
        return Collections.unmodifiableList(this.ships);
    }

    public boolean isAlive(){
        return calculateHealthPercentage() > 0;
    }

    public void reset(){
        currentShipIndex = 0;
        this.ships.clear();
    }

    public boolean tryToPlaceShipAt(final int x, final int y, final ShipDirection direction){
        final Ship newShip = new Ship(x, y, shipPlacementOrder[currentShipIndex], direction);
        boolean isCollision = this.ships.stream().anyMatch(ship -> ship.collides(newShip));
        if(isCollision){
            return false;
        }
        this.ships.add(newShip);
        ++currentShipIndex;
        return true;
    }

    public boolean isFullyEquipped(){
        return currentShipIndex >= SHIPS_TOTAL;
    }

    public double calculateHealthPercentage(){
        long totalNumberOfAliveCells = 0;
        for(final Ship ship: this.ships){
            final long numOfAliveCells = ship.getCells().stream().filter(Cell::isAlive).count();
            totalNumberOfAliveCells += numOfAliveCells;
        }
        return totalNumberOfAliveCells * 100.f / CELLS_TOTAL;
    }

    public boolean hit(final int x, final int y){
        return this.ships.stream().anyMatch(ship -> hit(x, y));
    }
}
