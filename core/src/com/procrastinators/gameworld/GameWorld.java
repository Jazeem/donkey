package com.procrastinators.gameworld;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Rectangle;
import com.procrastinators.gameobjects.Card;
import com.procrastinators.helpers.Constants;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Random;

/**
 * Created by jazeem on 23/05/17.
 */

public class GameWorld {
    private Card cards[];
    private Map<Integer, ArrayList<Card>> players;
    private int numPlayers = 4;

    public int getNumPlayers() {
        return numPlayers;
    }

    private int totalCards = 52;
    private int playerCardCount = totalCards/numPlayers; //should take care of cases when this is not exactly divisible
    private int player = 0;
    private int turn = 0;
    private boolean playerTurn = false;
    private float runTime = 0;
    private float turnThreshold = Constants.TURN_TIME; //time when next turn change happens
    private boolean firstRound = true;
    private ArrayList<Card> pileCards = new ArrayList<Card>();
    private Random random = new Random();
    private boolean discarding = false;
    private int playerToCollect = -1;

    public boolean isGameFinished() {
        return gameFinished;
    }

    private boolean gameFinished = false;
    public GameWorld(){
        cards = new Card[totalCards];
        initializeCards();
        shuffleCards();
        dealCards();
        arrangeCards();
        assignTurn();
    }

    private void assignTurn(){
        int i,j;
        for(i = 0; i < players.size(); i++){
            for(j = 0; j < players.get(i).size(); j++){
                if(players.get(i).get(j).getCode() == Constants.SPADE_ACE_CODE)
                    break;
            }
            if(j != players.get(i).size())
                break;
        }
        turn = i;
    }

    private void arrangeCards(){
        float playerCardLength = Constants.CARD_WIDTH + (playerCardCount-1)*Constants.CARD_SEPERATION;
        float opponentCardOffset = (Constants.GAME_WIDTH - ((numPlayers - 1)*playerCardLength + (numPlayers - 2)*Constants.OPPONENT_CARD_GAP))/2;
        opponentCardOffset += 2 * (playerCardLength + Constants.OPPONENT_CARD_GAP);
        for(int i=0;i<numPlayers;i++){
            if(players.containsKey(i)) { //player could've finished the game
                if(i == player){
                    float cardsOffset = (Constants.GAME_WIDTH - playerCardLength)/2;
                    for(int j=0;j<players.get(i).size();j++){
                        players.get(i).get(j).setX(Constants.CARD_SEPERATION*j + cardsOffset);
                        players.get(i).get(j).setY(Constants.GAME_HEIGHT-Constants.CARD_HEIGHT);
                        players.get(i).get(j).setOwner(i);
                    }
                }
                else{
                    for(int j=0;j<players.get(i).size();j++){
                        players.get(i).get(j).setX(Constants.CARD_SEPERATION*j + opponentCardOffset);
                        players.get(i).get(j).setY(0);
                        players.get(i).get(j).setOwner(i);
                    }
                }
            }
            if(i != player)
                opponentCardOffset -= playerCardLength + Constants.OPPONENT_CARD_GAP;
        }
    }

    private void dealCards(){
        players = new HashMap<Integer, ArrayList<Card>>();
        for(int i=0;i<numPlayers;i++){
            Card playerCards[] = new Card[playerCardCount];
            System.arraycopy(cards, i*playerCardCount, playerCards, 0, playerCardCount);
            players.put(i, new ArrayList<Card>(Arrays.asList(playerCards)));
        }
    }

    public Map<Integer, ArrayList<Card>> getPlayers(){
        return players;
    }

    private void shuffleCards()
    {
        int index;
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
                if(j == 0)
                    cards[index].setValueCode(13);
                else
                    cards[index].setValueCode(j);
            }
        }
    }

    public boolean isPlayerTurn() {
        return playerTurn;
    }

    public int getTurn() {
        return turn;
    }

    public void update(float delta) {
        //Gdx.app.log("GameWorld", "update");
//        if(turn == player)
//            playerTurn = true;
//        else
//            playerTurn = false;
        runTime += delta;
        if(runTime > turnThreshold){
            playTurn();
            turnThreshold += Constants.TURN_TIME;
        }

    }

    public ArrayList<Card> getPileCards() {
        return pileCards;
    }

    private void playTurn(){
        if(!gameFinished){
            if(discarding){
                players.get(playerToCollect).addAll(pileCards);
                pileCards = new ArrayList<Card>();
            }
            discarding = false;
            playerToCollect = -1;
            if(players.containsKey(turn) && players.get(turn).size() > 0){
                if(pileCards.size() <= 1 && players.size() <= 2){
                    if(players.size() == 1){
                        gameFinished = true;
                        return;
                    }
                    else{
                        int otherPlayer = -1;
                        for(int key: players.keySet()){
                            if(key != turn){
                                otherPlayer = key;
                                break;
                            }
                        }
                        if(players.get(otherPlayer).size() == 0){
                            players.remove(otherPlayer);
                            gameFinished = true;
                            return;
                        }
                    }
                }
                Card toPlay = null;
                if(firstRound && pileCards.size() == 0){
                    toPlay = players.get(turn).stream().filter(x -> x.getCode() == Constants.SPADE_ACE_CODE).findFirst().orElse(null);
                }
                else {
                    do{
                        int cardsLeft = players.get(turn).size();
                        toPlay = players.get(turn).get(random.nextInt(cardsLeft));
                    }while (!isValidCard(toPlay));
                }
                if(pileCards.size() >= players.size())
                    pileCards = new ArrayList<Card>();
                float pileCardLength = Constants.CARD_WIDTH + (players.size()-1)*Constants.CARD_SEPERATION;
                toPlay.setX((Constants.GAME_WIDTH-pileCardLength)/2 + pileCards.size()*Constants.CARD_SEPERATION);
                toPlay.setY((float) (Constants.GAME_HEIGHT-Constants.CARD_HEIGHT)/2);
                Card finalToPlay = toPlay; // variables used in lambda should be final
                players.get(turn).removeIf(x -> x.getCode() == finalToPlay.getCode());
                if(pileCards.size() > 0){
                    if(!toPlay.getSuit().equals(pileCards.get(0).getSuit())){
                        discarding = true;
                        Card highestValue = Collections.max(pileCards, Comparator.comparing(c -> c.getValueCode()));
                        playerToCollect = highestValue.getOwner();
                    }
                }
                pileCards.add(toPlay);
                firstRound = false;
            }
            else
                players.remove(turn);

            if(discarding)
                turn = playerToCollect;
            else if(pileCards.size() != players.size())
                turn = turn == numPlayers - 1  ? 0 : turn + 1;
            else {
                Card highestValue = Collections.max(pileCards, Comparator.comparing(c -> c.getValueCode()));
                turn = highestValue.getOwner();
            }
        }
        arrangeCards();
    }

    private boolean isValidCard(Card card){
        if(pileCards.size() == 0)
            return true;
        if(players.get(turn).stream().filter(x -> x.getSuit().equals(pileCards.get(0).getSuit())).count() == 0)
            return true;
        return pileCards.get(0).getSuit().equals(card.getSuit());
    }

}
