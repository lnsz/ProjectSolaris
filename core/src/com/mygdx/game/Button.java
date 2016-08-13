package com.mygdx.game;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

/**
 * Created by lucas on 8/12/2016.
 */
public class Button {
    Sprite sprite;
    float width, height, startingWidth, startingHeight;
    Vector location, startingLocation;
    public Button(float xPos, float yPos, float width, float height, Sprite sprite){
        location = new Vector(xPos, yPos);
        startingLocation = new Vector(xPos, yPos);
        this.sprite = sprite;
        this.width = width;
        this.height = height;
        startingWidth = width;
        startingHeight = height;
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

    public void scale(){
        // Moves button so it's in the same position when zoom is changed
        location.x = MissileGame.remap(startingLocation.x, 0, MissileGame.width, MissileGame.cameraOriginX,
                MissileGame.cameraOriginX + MissileGame.cameraWidth);
        location.y = MissileGame.remap(startingLocation.y, 0, MissileGame.height, MissileGame.cameraOriginY,
                MissileGame.cameraOriginY + MissileGame.cameraHeight);
        width = startingWidth * MissileGame.camera.zoom;
        height = startingHeight * MissileGame.camera.zoom;

    }
}
