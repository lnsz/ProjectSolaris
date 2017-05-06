package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;

import java.util.Locale;

/**
 * Created by lucas on 5/5/2017.
 */

public class Background {
    // Colours:
    // 0: Blue
    // 1: Green
    // 2: Yellow
    int colour, pattern;
    Texture texture;
    Sprite sprite;

    public Background(){
        // Sprites
        colour = MissileGame.randomInt(0, 2);
        pattern = MissileGame.randomInt(0, 4);
        generateSprite();
    }

    public Background(int colour, int pattern){
        this.colour = colour;
        this.pattern = pattern;
        generateSprite();
    }

    public void generateSprite(){
        System.out.println("background/ep" + colour + "/bg" + pattern + ".png");
        texture = new Texture(Gdx.files.internal("background/ep" + colour + "/bg" + pattern + ".png"));
        sprite = new Sprite(texture);
    }

    public void setSize(float width, float height){
        sprite.setSize(width, height);
    }

    public void setPosition(float x, float y){
        sprite.setPosition(x, y);
    }

    public void draw(){
        MissileGame.batch.begin();
        sprite.draw(MissileGame.batch);
        MissileGame.batch.end();
    }
}
