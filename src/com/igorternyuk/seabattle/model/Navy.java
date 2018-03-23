package com.igorternyuk.seabattle.model;

import java.util.*;

/**
 * Created by igor on 22.03.18.
 */
public class Navy {
    private static final int SHIPS_TOTAL = 10;
    private static final int CELLS_TOTAL = 20;
    private final List<Ship> ships = new ArrayList<>();
    private final Map<ShipType, Integer> destroyedShips = new HashMap<>();
    private final ShipType[] shipPlacementOrder = { ShipType.FOUR_DECKED, ShipType.THREE_DECKED, ShipType.THREE_DECKED,
            ShipType.TWO_DECKED, ShipType.TWO_DECKED, ShipType.TWO_DECKED, ShipType.ONE_DECKED, ShipType.ONE_DECKED,
            ShipType.ONE_DECKED, ShipType.ONE_DECKED};
    private int currentShipIndex = 0;

    public Navy(){
        resetDestroyedShipMap();
    }

    private void resetDestroyedShipMap(){
        for (final ShipType shipType : ShipType.values()) {
            this.destroyedShips.put(shipType, 0);
        }
    }

    public List<Ship> getShips(){
        return Collections.unmodifiableList(this.ships);
    }

    public int getDestoyedShipsCount(final ShipType shipType){
        return this.destroyedShips.get(shipType);
    }

    public boolean isAlive(){
        return calculateHealthPercentage() > 0;
    }

    public void reset(){
        this.currentShipIndex = 0;
        this.ships.clear();
        this.destroyedShips.clear();
        resetDestroyedShipMap();
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
        boolean isTargetHit = this.ships.stream().anyMatch(ship -> hit(x, y));
        for(final Ship ship: this.ships){
            if(ship.hit(x, y)){
                if(!ship.isAlive()){
                    final ShipType shipType = ship.getType();
                    if(this.destroyedShips.containsKey(shipType)) {
                        this.destroyedShips.put(shipType, this.destroyedShips.get(shipType) + 1);
                    } else {
                        this.destroyedShips.put(shipType, 1);
                    }
                }
            }
        }
        return isTargetHit;
    }
}
