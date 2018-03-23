package com.igorternyuk.seabattle.model;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by igor on 22.03.18.
 */
public class Game {
    public static final int FIELD_SIZE = 10;
    private static final int MAX_NEIGHBOUR_MOORE_COUNT = 8;
    private static final int MAX_NEIGHBOUR_VON_NEUMANN_COUNT = 4;
    private final HumanPlayer humanPlayer;
    private final ComputerPlayer computerPlayer;
    private GameState gameState = GameState.HUMAN_SHIP_PLACEMENT;

    public Game(){
        this.humanPlayer = new HumanPlayer(this);
        this.computerPlayer = new ComputerPlayer(this);
    }

    public void prepareNewGame(){
        this.humanPlayer.reset();
        this.computerPlayer.reset();
        this.gameState = GameState.HUMAN_SHIP_PLACEMENT;
    }

    public void setGameState(final GameState gameState){
        this.gameState = gameState;
    }

    public HumanPlayer getHumanPlayer(){
        return this.humanPlayer;
    }

    public ComputerPlayer getComputerPlayer(){
        return this.computerPlayer;
    }

    public void shootsHuman(final int x, final int y){
        if(this.gameState.equals(GameState.HUMAN_TO_PLAY)) {
            this.humanPlayer.shoot(x, y);
        }
    }

    public void tryToPlaceHorizontalShip(final int x, final int y){
        this.humanPlayer.getNavy().tryToPlaceShipAt(x, y, ShipDirection.HORIZONTAL);
    }

    public void tryToPlaceVerticalShip(final int x, final int y){
        this.humanPlayer.getNavy().tryToPlaceShipAt(x, y, ShipDirection.VERTICAL);
    }

    public static boolean isValidPosition(final int x, final int y){
        return x >= 0 && x < FIELD_SIZE && y >= 0 && y < FIELD_SIZE;
    }

    public static List<Point> getNeighboursMoore(final Point pos){
        return Game.getNeighboursMoore(pos.x, pos.y);
    }

    public static List<Point> getNeighboursMoore(final int x, final int y){
        List<Point> neighbours = new ArrayList<>(MAX_NEIGHBOUR_MOORE_COUNT);
        for(int dx = -1; dx <= 1; ++dx){
            for(int dy = -1; dy <= 1; ++dy){
                if(dx == 0 && dy == 0) continue;
                final int nx = x + dx;
                final int ny = y + dy;
                if(isValidPosition(nx, ny)){
                    neighbours.add(new Point(nx, ny));
                }
            }
        }
        return neighbours;
    }

    public static List<Point> getNeighboursVonNeumann(final Point pos){
        return Game.getNeighboursVonNeumann(pos.x, pos.y);
    }

    public static List<Point> getNeighboursVonNeumann(final int x, final int y){
        List<Point> neighbours = new ArrayList<>(MAX_NEIGHBOUR_VON_NEUMANN_COUNT);
        final int[] dx = { 1, 0, -1 , 0 };
        final int[] dy = { 0, 1, 0, -1 };
        for(int dir = 0; dir < MAX_NEIGHBOUR_VON_NEUMANN_COUNT; ++dir){
            final int nx = x + dx[dir];
            final int ny = y + dy[dir];
            if(isValidPosition(nx, ny)){
                neighbours.add(new Point(nx, ny));
            }
        }
        return neighbours;
    }
}
