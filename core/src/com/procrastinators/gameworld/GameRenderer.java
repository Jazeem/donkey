package com.procrastinators.gameworld;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.procrastinators.gameobjects.Card;
import com.procrastinators.helpers.AssetLoader;

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
        cam.setToOrtho(true, 480, 320);
        shapeRenderer = new ShapeRenderer();
        shapeRenderer.setProjectionMatrix(cam.combined);
        batcher = new SpriteBatch();
        batcher.setProjectionMatrix(cam.combined);

    }

    public void render() {
        Gdx.app.log("GameRenderer", "render");

        Card[] cards = myWorld.getCards();
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

        for (int i=0;i<13;i++){
            Gdx.app.log("code", cards[i].getCode()+"");
            batcher.draw(AssetLoader.cards[cards[i].getCode()], 15*i, 0, 73, 98);
        }

        //Gdx.app.log("code", cards[0].getCode()+"");
        //batcher.draw(AssetLoader.cards[1], 0, 0, 73, 98);


        // End SpriteBatch
        batcher.end();
    }
}
