package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

import java.util.Random;

/**
 * Created by Kiko on 11/07/2016.
 */
public class Particle extends Entity{
    float life, maxLife, decay, randomness;
    private Sprite sprite;

    public Particle(float locX, float locY, float duration, Sprite sprite){
        super(locX, locY);
        life = 255;
        maxLife = 255;
        decay = (255 - 30) / duration;
        randomness = 2;
        mass = 0;
        this.sprite = sprite;

    }

    @Override
    public void display(SpriteBatch batch, ShapeRenderer renderer){
        sprite.setPosition(location.x, location.y);
        sprite.draw(batch);

//        renderer.begin(ShapeRenderer.ShapeType.Filled);
//        renderer.setColor(255, 255, 255, 1);
//        renderer.rect(location.x, location.y, radius, radius);
//        renderer.end();
    }

    @Override
    public void run(SpriteBatch batch, ShapeRenderer renderer){
        life -= decay;
        if(life < 30){
            alive = false;
            return;
        }
        acceleration.x += randomness * (float)MissileGame.generator.nextGaussian();
        acceleration.y += randomness * (float)MissileGame.generator.nextGaussian();
        update();
        display(batch, renderer);
    }
}
