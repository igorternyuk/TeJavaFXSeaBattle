package com.igorternyuk.seabattle.model;

import java.awt.*;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Created by igor on 23.03.18.
 */
public class ComputerPlayer extends AbstractPlayer {
    private Set<Point> nextShoots = new HashSet<>();
    private boolean isTargetAcquired = false;

    ComputerPlayer(final Game game) {
        super(game);
        reset();
    }

    public void reset(){
        this.navy.reset();
        placeShipsRandomly();
        this.shots.clear();
        this.nextShoots.clear();
    }

    private void placeShipsRandomly(){
        while(!this.navy.isFullyEquipped()) {
            boolean isShipPlaced;
            do {
                int randX = random.nextInt(Game.FIELD_SIZE), randY = random.nextInt(Game.FIELD_SIZE);
                ShipDirection direction = random.nextBoolean() ? ShipDirection.HORIZONTAL : ShipDirection.VERTICAL;
                isShipPlaced = this.navy.tryToPlaceShipAt(randX, randY, direction);
            } while (!isShipPlaced);
        }
    }

    void shoot() {
        for(;;){
            int targetX, targetY;
            if(this.isTargetAcquired && !this.nextShoots.isEmpty()){
                final Point nextTarget = this.nextShoots.iterator().next();
                targetX = nextTarget.x;
                targetY = nextTarget.y;
                this.nextShoots.remove(nextTarget);

            } else {
                this.isTargetAcquired = false;
                targetX = this.random.nextInt(Game.FIELD_SIZE);
                targetY = this.random.nextInt(Game.FIELD_SIZE);
                boolean isTargetOK = false;
                while (!isTargetOK) {
                    targetX = this.random.nextInt(Game.FIELD_SIZE);
                    targetY = this.random.nextInt(Game.FIELD_SIZE);
                    final Point target = new Point(targetX, targetY);
                    isTargetOK = this.game.getHumanPlayer().getDestroyedShips().stream().noneMatch(ship -> {
                        List<Cell> cells = ship.getCells();
                        return cells.stream().noneMatch(cell -> {
                            List<Point> neighbours = Game.getNeighboursMoore(cell.getPosition());
                            return neighbours.stream().noneMatch(pos -> pos.equals(target));
                        });
                    });
                }
            }
            int humanDestroyedShipsCountBefore = this.game.getHumanPlayer().getDestroyedShips().size();
            boolean hitTheTarget = this.game.getHumanPlayer().getNavy().hit(targetX, targetY);
            this.shots.add(new Point(targetX, targetY));
            if(hitTheTarget){
                if (!this.game.getHumanPlayer().isInPlay()) {
                    this.game.setGameState(GameState.COMPUTER_WON);
                    break;
                }
                int humanDestroyedShipsCountAfter = this.game.getHumanPlayer().getDestroyedShips().size();
                if (humanDestroyedShipsCountBefore == humanDestroyedShipsCountAfter) {
                    this.isTargetAcquired = true;
                    List<Point> neighbours = Game.getNeighboursVonNeumann(targetX, targetY);
                    this.nextShoots.addAll(neighbours.stream().filter(pos -> !this.shots.contains(pos))
                            .collect(Collectors.toList()));
                } else if (humanDestroyedShipsCountAfter > humanDestroyedShipsCountBefore) {
                    this.nextShoots.clear();
                    this.isTargetAcquired = false;
                }
            } else {
                this.game.setGameState(GameState.HUMAN_TO_PLAY);
                break;
            }
        }
    }
}
