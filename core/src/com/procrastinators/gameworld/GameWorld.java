package com.procrastinators.gameworld;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Rectangle;
import com.procrastinators.gameobjects.Card;

import java.util.Random;

/**
 * Created by jazeem on 23/05/17.
 */

public class GameWorld {
    private Card cards[];

    public GameWorld(){
        cards = new Card[52];
        initializeCards();
        shuffleCards();
    }

    public Card[] getCards(){
        return cards;
    }

    private void shuffleCards()
    {
        int index;
        Random random = new Random();
        for (int i = cards.length - 1; i > 0; i--)
        {
            index = random.nextInt(i + 1);
            Card card = cards[index];
            cards[index] = cards[i];
            cards[i] = card;
        }
    }

    private void initializeCards(){
        for(int i=0;i<4;i++){
            String suit = null;
            switch (i){
                case 0:
                    suit = "clubs";
                    break;
                case 1:
                    suit = "spades";
                    break;
                case 2:
                    suit = "hearts";
                    break;
                case 3:
                    suit = "diamonds";
                    break;
                default:
                    break;
            }
            for (int j=0;j<13;j++){
                int index = i*13+j;
                char value;
                switch (j){
                    case 0:
                        value = 'A';
                        break;
                    case 10:
                        value = 'J';
                        break;
                    case 11:
                        value = 'Q';
                        break;
                    case 12:
                        value = 'K';
                        break;
                    default:
                        value = (char) ((j+1)+48);
                        break;
                }
                cards[index] = new Card();
                cards[index].setSuit(suit);
                cards[index].setValue(value);
                cards[index].setCode(index);
            }
        }
    }

    public void update(float delta) {
        Gdx.app.log("GameWorld", "update");
    }


}
