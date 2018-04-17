package com.mygdx.game;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

/**
 * Created by Kiko on 11/07/2016.
 */
public class Particle extends Entity{
    float life, maxLife, decay, randomness;
    private Sprite sprite;
    Trail trail;
    boolean movesStraight, hasTrail;
    Vector direction;
    int counter;

    public Particle(float locX, float locY, float duration, Sprite sprite){
        super(locX, locY);
        life = 255;
        maxLife = 255;
        decay = (255 - 30) / duration;
        movesStraight = ProjectSolaris.generator.nextInt(100) >= 100;
        hasTrail = ProjectSolaris.generator.nextInt(100) >= 0;
        randomness = 1;
        mass = 0;
        direction = new Vector((float)ProjectSolaris.generator.nextGaussian() * 2 + 1, (float)ProjectSolaris.generator.nextGaussian() * 2 + 1);
        boolean neg = ProjectSolaris.generator.nextFloat() >= 50;
        if (neg){
            direction.x *= -1;
        }
        neg = ProjectSolaris.generator.nextFloat() >= 50;
        if (neg){
            direction.y *= -1;
        }
        counter = 1;
        radius = ProjectSolaris.generator.nextInt(10) + 6;
        trail = new Trail(locX, locY, radius, (int) (radius / 2));
        this.sprite = sprite;

    }

    @Override
    public void update(){
        velocity.add(acceleration);
        position.add(velocity);
        acceleration.mult(0);
        if (hasTrail) {
            updateTrail();
        }
        alive = !ProjectSolaris.outsideScreen(position);
    }

    @Override
    public void draw(){

//        sprite.setPosition(position.x, position.y);
//        sprite.draw(ProjectSolaris.batch);
        ProjectSolaris.renderer.circle(position.x, position.y, radius / 2);
    }

    public void updateTrail(){
        trail.update(position.x, position.y);
        trail.compute();
        trail.draw();
    }


    @Override
    public void run(){
        if (!ProjectSolaris.isPaused) {
            life -= decay;
            if (life < 30) {
                alive = false;
                return;
            }
            if (movesStraight) {
                acceleration.x += direction.x * 1 / counter;
                acceleration.y += direction.y * 1 / counter;
                counter++;
            } else {
                acceleration.x += randomness * (float) ProjectSolaris.generator.nextGaussian();
                acceleration.y += randomness * (float) ProjectSolaris.generator.nextGaussian();
            }
            update();
        }
        draw();
    }

}
