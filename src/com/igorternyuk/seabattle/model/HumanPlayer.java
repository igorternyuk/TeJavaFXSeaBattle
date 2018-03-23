package com.igorternyuk.seabattle.model;

import java.awt.*;

/**
 * Created by igor on 23.03.18.
 */
public class HumanPlayer extends AbstractPlayer {

    public HumanPlayer(Game game) {
        super(game);
    }

    public void reset(){
        this.navy.reset();
        this.shots.clear();
    }

    public void shoot(final int x, final int y){
        System.out.println("Size = " + this.game.getComputerPlayer().getNavy().getShips().size());
        if(!this.game.getComputerPlayer().getNavy().hit(x, y)){
            System.out.println("Miss");
            this.shots.add(new Point(x, y));
            /*this.game.getHumanPlayer().getShots().forEach(pos -> {
                System.out.println(pos);
            });*/
            this.game.setGameState(GameState.COMPUTER_TO_PLAY);
            this.game.getComputerPlayer().shoot();
        } else {
            System.out.println("Target hit");
            this.shots.add(new Point(x, y));
            /*this.game.getHumanPlayer().getShots().forEach(pos -> {
                System.out.println(pos);
            });*/
            if(!this.game.getComputerPlayer().getNavy().isAlive()){
                this.game.setGameState(GameState.HUMAN_WON);
            }
        }
    }

}
