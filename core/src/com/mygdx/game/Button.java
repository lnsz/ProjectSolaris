package com.mygdx.game;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

/**
 * Created by lucas on 8/12/2016.
 */
public class Button {
    Sprite sprite;
    float width, height, startingWidth, startingHeight;
    Vector position, startingposition;
    String text;
    boolean selected;
    public Button(float xPos, float yPos, float width, float height, Sprite sprite){
        position = new Vector(xPos, yPos);
        startingposition = new Vector(xPos, yPos);
        this.sprite = sprite;
        this.width = width;
        this.height = height;
        startingWidth = width;
        startingHeight = height;
        selected = false;
        text = "";
    }
    public Button(float xPos, float yPos, float width, float height, Sprite sprite, String text){
        position = new Vector(xPos, yPos);
        startingposition = new Vector(xPos, yPos);
        this.sprite = sprite;
        this.width = width;
        this.height = height;
        startingWidth = width;
        startingHeight = height;
        selected = false;
        this.text = text;
    }


    public boolean isClicked(float x, float y){
        if (x > position.x && x < (position.x + width) &&
                y > position.y && y < (position.y + height)) {
            return true;
        }
        return false;
    }

    public void draw(){
        MissileGame.batch.begin();
        sprite.setSize(width, height);
        if (selected) {
            sprite.setColor(Color.YELLOW);
        }
        else{
            sprite.setColor(Color.WHITE);
        }
        sprite.setPosition(position.x, position.y);
        sprite.setFlip(false, true);
        sprite.draw(MissileGame.batch);

        MissileGame.arial.getData().setScale(height / 300);
        MissileGame.arial.getRegion().getTexture().setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        MissileGame.glyphLayout.setText(MissileGame.arial, text);
        float textX = position.x + width / 2 - MissileGame.glyphLayout.width / 2;
        float textY = position.y + height / 2 - MissileGame.glyphLayout.height / 2;
        MissileGame.arial.draw(MissileGame.batch, MissileGame.glyphLayout, textX, textY);
        MissileGame.batch.end();
    }

    public void scale(){
        // Moves button so it's in the same position when zoom is changed
        position.x = MissileGame.remap(startingposition.x, 0, MissileGame.width, MissileGame.cameraOriginX,
                MissileGame.cameraOriginX + MissileGame.cameraWidth);
        position.y = MissileGame.remap(startingposition.y, 0, MissileGame.height, MissileGame.cameraOriginY,
                MissileGame.cameraOriginY + MissileGame.cameraHeight);
        width = startingWidth * MissileGame.camera.zoom;
        height = startingHeight * MissileGame.camera.zoom;

    }
}
