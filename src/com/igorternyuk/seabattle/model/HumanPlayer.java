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
}
