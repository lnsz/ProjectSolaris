package com.mygdx.game;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

/**
 * Created by lucas on 8/12/2016.
 */
public class Button {
    Sprite sprite;
    float width, height;
    Vector location;
    public Button(float xPos, float yPos, float width, float height, Sprite sprite){
        location = new Vector(xPos, yPos);
        this.sprite = sprite;
        this.width = width;
        this.height = height;
    }

    public boolean isClicked(float x, float y){
        if (x > location.x && x < (location.x + width) &&
                y > location.y && y < (location.y + height)) {
            return true;
        }
        return false;
    }

    public void draw(){
        MissileGame.batch.begin();
        sprite.setSize(width, height);
        sprite.setPosition(location.x, location.y);
        sprite.setFlip(false, true);
        sprite.draw(MissileGame.batch);
        MissileGame.batch.end();
    }
}
