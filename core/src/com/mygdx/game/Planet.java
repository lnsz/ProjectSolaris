package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

/**
 * Created by lucas on 8/3/2016.
 */
public class Planet extends Obstacle{
    // Stationary, have their own gravity

    public Planet(float locX, float locY, float radius, float mass){
        super(MissileGame.remap(locX, 0, MissileGame.width,
                MissileGame.defaultOriginX,
                MissileGame.defaultOriginX + MissileGame.defaultWidth),
                MissileGame.remap(locY, 0, MissileGame.height,
                        MissileGame.defaultOriginY,
                        MissileGame.defaultOriginY + MissileGame.defaultHeight), radius);
        if(radius > Levels.LARGE_PLANET_RADIUS) {
            texture = new Texture(Gdx.files.internal("largePlanet.png"));
        }
        else if(radius < Levels.SMALL_MOON_RADIUS){
            texture = new Texture(Gdx.files.internal("smallPlanet.png"));
        }
        else{
            texture = new Texture(Gdx.files.internal("earth.png"));
        }
        sprite = new Sprite(texture);
        gravity = true;
        this.mass = mass;
    }

    public Planet(MissileGame.Preset pos, float radius, float mass){
        // Create a planet at a preset position
        super(0, 0, radius);
        position = MissileGame.generatePreset(pos);
        if(radius > Levels.LARGE_PLANET_RADIUS) {
            texture = new Texture(Gdx.files.internal("largePlanet.png"));
        }
        else if(radius < Levels.SMALL_PLANET_RADIUS){
            texture = new Texture(Gdx.files.internal("smallPlanet.png"));
        }
        else{
            texture = new Texture(Gdx.files.internal("earth.png"));
        }
        sprite = new Sprite(texture);
        gravity = true;
        this.mass = mass;
    }

    @Override
    public void update(){
        // planet doesn't move
    }

    @Override
    public void draw(){
        MissileGame.batch.begin();
        sprite.setSize(radius * 2, radius * 2);
        sprite.setPosition(position.x - radius, position.y - radius);
        sprite.draw(MissileGame.batch);
        MissileGame.batch.end();
    }
}
