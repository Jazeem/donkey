package com.procrastinators.gameobjects;

/**
 * Created by jazeem on 23/05/17.
 */

public class Card {
    String suit;
    char value;
    int code; //code from 0 indicating the position of card in cards.png

    public String getSuit() {
        return suit;
    }

    public void setSuit(String suit) {
        this.suit = suit;
    }

    public char getValue() {
        return value;
    }

    public void setValue(char value) {
        this.value = value;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }
}
