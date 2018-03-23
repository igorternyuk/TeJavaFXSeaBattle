package com.igorternyuk.seabattle.model;

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
        if(!this.game.getComputerPlayer().getNavy().hit(x, y)){
            this.game.setGameState(GameState.COMPUTER_TO_PLAY);
        } else {
            if(!this.game.getComputerPlayer().getNavy().isAlive()){
                this.game.setGameState(GameState.HUMAN_WON);
            }
        }
    }

}
