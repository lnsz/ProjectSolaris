package com.mygdx.game;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;

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
        ProjectSolaris.batchUI.begin();
        sprite.setSize(width, height);
        if (selected) {
            sprite.setColor(Color.YELLOW);
        }
        else{
            sprite.setColor(Color.WHITE);
        }
        sprite.setPosition(position.x, position.y);
        sprite.setFlip(false, true);
        sprite.draw(ProjectSolaris.batchUI);

        ProjectSolaris.dinPro.getData().setScale(height / 400);
        ProjectSolaris.dinPro.getRegion().getTexture().setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        ProjectSolaris.glyphLayout.setText(ProjectSolaris.dinPro, text);
        float textX = position.x + width / 2 - ProjectSolaris.glyphLayout.width / 2;
        float textY = position.y + height / 2 - ProjectSolaris.glyphLayout.height;
        ProjectSolaris.dinPro.draw(ProjectSolaris.batchUI, ProjectSolaris.glyphLayout, textX, textY);
        ProjectSolaris.batchUI.end();
    }

    public void scale(){
////        // Moves button so it's in the same position when zoom is changed
//        position.x = ProjectSolaris.remap(startingposition.x, 0, ProjectSolaris.width, ProjectSolaris.origin.x,
//                ProjectSolaris.origin.x + ProjectSolaris.width * ProjectSolaris.camera.zoom);
//        position.y = ProjectSolaris.remap(startingposition.y, 0, ProjectSolaris.height, ProjectSolaris.origin.y,
//                ProjectSolaris.origin.y + ProjectSolaris.height * ProjectSolaris.camera.zoom);
//        width = startingWidth * ProjectSolaris.camera.zoom;
//        height = startingHeight * ProjectSolaris.camera.zoom;

    }
}
