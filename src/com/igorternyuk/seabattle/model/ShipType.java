package com.igorternyuk.seabattle.model;

/**
 * Created by igor on 23.03.18.
 */
public enum ShipType {
    ONE_DECKED(1),
    TWO_DECKED(2),
    THREE_DECKED(3),
    FOUR_DECKED(4);

    private int deckNumber;

    ShipType(final int deckNumber){
        this.deckNumber = deckNumber;
    }

    public int getDeckNumber(){
        return this.deckNumber;
    }
}
