package com.mygdx.game;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

import java.util.Random;

/**
 * Created by Kiko on 11/07/2016.
 */
public class Particle extends Entity{
    Random generator;
    float life, decay, randomness;

    public Particle(float locX, float locY, float rad, float duration, Random generator){
        super(locX, locY);
        this.generator = generator;
        radius = rad;
        life = 255;
        decay = (255 - 30) / duration;
        mass = 0;
        randomness = 1;

    }

    public Particle(float locX, float locY, float velX, float velY, float rad, float duration, Random generator){
        super(locX, locY);
        velocity.x = velX;
        velocity.y = velY;
        this.generator = generator;
        radius = rad;
        life = 255;
        decay = (255 - 30) / duration;
        mass = 0;
        randomness = 1;

    }

    @Override
    public void display(SpriteBatch batch, ShapeRenderer renderer){
        renderer.begin(ShapeRenderer.ShapeType.Filled);
        renderer.setColor(255, 255, 255, 1);
        renderer.rect(location.x, location.y, radius, radius);
        renderer.end();
    }

    @Override
    public void run(SpriteBatch batch, ShapeRenderer renderer){
        life -= decay;
        if(life < 30){
            alive = false;
            return;
        }
        acceleration.x += randomness * (float)generator.nextGaussian();
        acceleration.y += randomness * (float)generator.nextGaussian();
        update();
        display(batch, renderer);
    }
}
