package com.igorternyuk.seabattle.model;

import java.util.*;

/**
 * Created by igor on 22.03.18.
 */
public class Navy {
    private static final int SHIPS_TOTAL = 10;
    private static final int CELLS_TOTAL = 20;
    private final List<Ship> ships = new ArrayList<>();
    private final Map<ShipType, Integer> destroyedShipsMap = new HashMap<>();
    private final List<Ship> destroyedShips = new ArrayList<>(SHIPS_TOTAL);
    private final ShipType[] shipPlacementOrder = { ShipType.FOUR_DECKED, ShipType.THREE_DECKED, ShipType.THREE_DECKED,
            ShipType.TWO_DECKED, ShipType.TWO_DECKED, ShipType.TWO_DECKED, ShipType.ONE_DECKED, ShipType.ONE_DECKED,
            ShipType.ONE_DECKED, ShipType.ONE_DECKED};
    private int currentShipIndex = 0;

    Navy() {
        resetDestroyedShipMap();
    }

    private void resetDestroyedShipMap(){
        for (final ShipType shipType : ShipType.values()) {
            this.destroyedShipsMap.put(shipType, 0);
        }
    }

    public List<Ship> getShips(){
        return Collections.unmodifiableList(this.ships);
    }

    List<Ship> getDestoyedShips() {
        return Collections.unmodifiableList(this.destroyedShips);
    }

    boolean isAlive() {
        return calculateHealthPercentage() > 0;
    }

    void reset() {
        this.currentShipIndex = 0;
        this.ships.clear();
        this.destroyedShipsMap.clear();
        this.destroyedShips.clear();
        resetDestroyedShipMap();
    }

    boolean tryToPlaceShipAt(final int x, final int y, final ShipDirection direction) {
        final Ship newShip = new Ship(x, y, shipPlacementOrder[currentShipIndex], direction);
        boolean isAllCellInFieldBounds = newShip.getCells().stream()
                .allMatch(cell -> Game.isValidPosition(cell.getPosition()));
        boolean isPositionOK = this.ships.stream().noneMatch(ship -> ship.collides(newShip));
        if(isAllCellInFieldBounds && isPositionOK){
            this.ships.add(newShip);
            ++currentShipIndex;
            return true;
        }
        return false;
    }

    boolean isFullyEquipped() {
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

    boolean hit(final int x, final int y) {
        boolean isTargetHit = false;
        for(final Ship ship: this.ships){
            if(ship.hit(x, y)){
                isTargetHit = true;
                if(!ship.isAlive()){
                    this.destroyedShips.add(ship);
                    final ShipType shipType = ship.getType();
                    if (this.destroyedShipsMap.containsKey(shipType)) {
                        this.destroyedShipsMap.put(shipType, this.destroyedShipsMap.get(shipType) + 1);
                    } else {
                        this.destroyedShipsMap.put(shipType, 1);
                    }
                }
            }
        }
        return isTargetHit;
    }
}
