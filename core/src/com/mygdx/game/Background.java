package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.utils.TiledDrawable;

import java.util.ArrayList;

import static com.badlogic.gdx.graphics.Texture.TextureWrap.Repeat;

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
    TiledDrawable tile;
    int multiplier;

    public Background(){
        // Sprites
        colour = ProjectSolaris.randomInt(0, 2);
        pattern = ProjectSolaris.randomInt(0, 4);
        multiplier = ProjectSolaris.bgMultiplier;
        generateSprite();
    }

    public Background(int colour, int pattern){
        this.colour = colour;
        this.pattern = pattern;
        multiplier = ProjectSolaris.bgMultiplier;
        generateSprite();
    }

    public void generateSprite(){
        texture = new Texture(Gdx.files.internal("background/ep" + colour + "/bg" + pattern + ".png"));
        texture.setWrap(Repeat, Repeat);
        sprite = new Sprite(texture);
    }

    public void setSize(float width, float height){
        sprite.setSize(width * multiplier, height * multiplier);
    }

    public void setPosition(float x, float y){
        sprite.setPosition(x, y);
    }

    public void draw(){
        Gdx.gl.glClearColor(0,0,0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        ProjectSolaris.shaderProgram.begin();
        ProjectSolaris.shaderProgram.setUniformf("center",
                new Vector2(ProjectSolaris.remap(ProjectSolaris.shaderPosition.x, ProjectSolaris.bgOrigin.x, ProjectSolaris.width * multiplier / 2 + ProjectSolaris.width / 2, 0, 1),
                        1 - ProjectSolaris.remap(ProjectSolaris.shaderPosition.y, ProjectSolaris.bgOrigin.y, ProjectSolaris.height * multiplier / 2 + ProjectSolaris.height / 2, 0, 1)));
        ProjectSolaris.shaderProgram.setUniformf("time", ProjectSolaris.shaderTime);
        ProjectSolaris.shaderProgram.setUniformf("shockParams", new Vector3(10.0f, 0.8f, 0.1f));
        ProjectSolaris.shaderProgram.setUniformf("sizeMultiplier",12f);
        ProjectSolaris.shaderProgram.end();
        ProjectSolaris.batch.begin();
        ProjectSolaris.batch.setShader(ProjectSolaris.shaderProgram);
        sprite.draw(ProjectSolaris.batch);
        ProjectSolaris.batch.setShader(null);
        ProjectSolaris.batch.end();
        ProjectSolaris.shaderTime += 0.02;
    }
}
