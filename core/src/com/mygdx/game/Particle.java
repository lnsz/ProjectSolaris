package com.mygdx.game;

import com.badlogic.gdx.graphics.g2d.Sprite;

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
        randomness = 1;
        mass = 0;
        radius = 3;
        this.sprite = sprite;

    }

    @Override
    public void update(){
        velocity.add(acceleration);
        position.add(velocity);
        acceleration.mult(0);

        Vector distance = new Vector(position.x - ProjectSolaris.width / 2,
                position.y - ProjectSolaris.height / 2); // Distance from current position to center
        if(distance.mag() > ProjectSolaris.entityBorder){
            alive = false;
        }
    }

    @Override
    public void draw(){
        sprite.setPosition(position.x, position.y);
        sprite.draw(ProjectSolaris.batch);

        // renderer.rect(position.x, position.y, radius, radius);
    }

    @Override
    public void run(){
        if (!ProjectSolaris.isPaused) {
            life -= decay;
            if (life < 30) {
                alive = false;
                return;
            }
            acceleration.x += randomness * (float) ProjectSolaris.generator.nextGaussian();
            acceleration.y += randomness * (float) ProjectSolaris.generator.nextGaussian();
            update();
        }
        draw();
    }

}
