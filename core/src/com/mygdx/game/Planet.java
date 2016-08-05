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
        super(locX, locY, radius);
        texture = new Texture(Gdx.files.internal("earth.png"));
        sprite = new Sprite(texture);
        gravity = true;
        this.mass = mass;
    }

    @Override
    public void update(){
        // planet doesn't move
    }

    @Override
    public void display(SpriteBatch batch, ShapeRenderer renderer){
        batch.begin();
        sprite.setSize(radius * 2, radius * 2);
        sprite.setPosition(location.x - radius, location.y - radius);
        sprite.draw(batch);
        batch.end();
    }
}
