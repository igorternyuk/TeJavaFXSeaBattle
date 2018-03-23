package com.igorternyuk.seabattle.model;

/**
 * Created by igor on 22.03.18.
 */
public enum GameState {
    HUMAN_SHIP_PLACEMENT("Place you ships"),
    HUMAN_TO_PLAY("Your turn"),
    COMPUTER_TO_PLAY("Computer's turn"),
    HUMAN_WON("You won!!!"),
    COMPUTER_WON("Computer won!!!");

    private String text;

    GameState(final String text){
        this.text = text;
    }

    @Override
    public String toString(){
        return this.text;
    }
}
