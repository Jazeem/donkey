package com.procrastinators.helpers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

/**
 * Created by jazeem on 23/05/17.
 */

public class AssetLoader {
    public static Texture texture;
    public static TextureRegion cards[] = new TextureRegion[52];
    public static void load(){
        texture = new Texture(Gdx.files.internal("cards.png"));
        texture.setFilter(Texture.TextureFilter.Nearest, Texture.TextureFilter.Nearest);

        for (int i=0;i<4;i++)
            for (int j=0;j<13;j++){
                int index = i*13+j;
                cards[index] = new TextureRegion(texture, 73*j, 98*i, 73, 98);
                cards[index].flip(false, true);
            }
    }

    public static void dispose(){
        texture.dispose();
    }
}
