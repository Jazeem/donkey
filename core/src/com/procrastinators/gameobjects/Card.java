package com.procrastinators.gameobjects;

import java.util.Comparator;

/**
 * Created by jazeem on 23/05/17.
 */

public class Card implements Comparable<Card>{
    String suit;
    char value;
    int code; //code from 0 indicating the position of card in cards.png
    float x,y;
    int valueCode; //code to compare which card is highest in a suit

    public int getValueCode() {
        return valueCode;
    }

    public void setValueCode(int valueCode) {
        this.valueCode = valueCode;
    }

    public int getOwner() {
        return owner;
    }

    public void setOwner(int owner) {
        this.owner = owner;
    }

    int owner;

    public float getX() {
        return x;
    }

    public void setX(float x) {
        this.x = x;
    }

    public float getY() {
        return y;
    }

    public void setY(float y) {
        this.y = y;
    }

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

    @Override
    public int compareTo(Card card) {
        return code - card.getCode();
    }
}
