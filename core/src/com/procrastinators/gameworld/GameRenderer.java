package com.procrastinators.gameworld;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.procrastinators.gameobjects.Card;
import com.procrastinators.helpers.AssetLoader;
import com.procrastinators.helpers.Constants;

import java.util.ArrayList;

/**
 * Created by jazeem on 23/05/17.
 */

public class GameRenderer {
    private GameWorld myWorld;
    private OrthographicCamera cam;
    private ShapeRenderer shapeRenderer;
    private SpriteBatch batcher;
    public GameRenderer(GameWorld world) {
        myWorld = world;
        cam = new OrthographicCamera();
        cam.setToOrtho(true, Constants.GAME_WIDTH, Constants.GAME_HEIGHT);
        shapeRenderer = new ShapeRenderer();
        shapeRenderer.setProjectionMatrix(cam.combined);
        batcher = new SpriteBatch();
        batcher.setProjectionMatrix(cam.combined);

    }

    public void render() {
        //Gdx.app.log("GameRenderer", "render");

        /*
         * 1. We draw a black background. This prevents flickering.
         */

        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        /*
         * 2. We draw the Filled rectangle
         */

        // Begin SpriteBatch
        batcher.begin();
        // Disable transparency
        // This is good for performance when drawing images that do not require
        // transparency.
        batcher.disableBlending();

        //Render Player Cards
        for(int i=0;i<myWorld.getNumPlayers();i++){
            if(i == myWorld.getTurn())
                batcher.setColor(new Color((float)180/255, (float)252/255, (float)185/255, 0.0f));
            else
                batcher.setColor(Color.WHITE);
            ArrayList<Card> player = myWorld.getPlayers().get(i);
            if(player != null){ // player could've finished the game.
                int cardWidth = Constants.CARD_WIDTH;
                int cardHeight = Constants.CARD_HEIGHT;
                if(i == myWorld.getPlayer()){
                    cardWidth = Constants.USER_CARD_WIDTH;
                    cardHeight = Constants.USER_CARD_HEIGHT;
                }
                if(i != myWorld.getPlayer() && Constants.FLIP_OPPONENT_CARDS){
                    for (int j=0;j<player.size();j++){
                        batcher.draw(AssetLoader.cardBack, player.get(j).getX(),player.get(j).getY(), cardWidth, cardHeight);
                    }
                }
                else{
                    for (int j=0;j<player.size();j++){
                        batcher.draw(AssetLoader.cards[player.get(j).getCode()], player.get(j).getX(),player.get(j).getY(), cardWidth, cardHeight);
                    }
                }
            }
        }

        //Render Pile Cards
        batcher.setColor(Color.WHITE);
        for(int i = 0; i < myWorld.getPileCards().size(); i++){
            Card card = myWorld.getPileCards().get(i);
            batcher.draw(AssetLoader.cards[card.getCode()], card.getX(),card.getY(), Constants.CARD_WIDTH, Constants.CARD_HEIGHT);
        }

        if(myWorld.isGameFinished()){
            BitmapFont font = new BitmapFont(true);
            //font.setColor(Color.CORAL);
            font.getData().setScale(2.0f, 2.0f);
            font.draw(batcher, "Player "+ myWorld.getPlayers().keySet().toArray()[0] +" is donkey!", Constants.GAME_WIDTH/2 - 150, Constants.GAME_HEIGHT/2 + 50);
        }

        //Gdx.app.log("Turn", myWorld.getTurn()+"");

        // End SpriteBatch
        batcher.end();
    }
}
