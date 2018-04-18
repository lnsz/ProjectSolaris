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

    public Particle(float locX, float locY, float duration, Sprite sprite){
        super(locX, locY);
        life = 255;
        maxLife = 255;
        decay = 255 / duration;
        movesStraight = ProjectSolaris.generator.nextInt(100) >= 70;
        randomness = 0.5f;
        mass = 0;
        direction = new Vector(1, 0);
        boolean neg = ProjectSolaris.generator.nextFloat() >= 50;
        if (neg){
            direction.x *= -1;
        }
        neg = ProjectSolaris.generator.nextFloat() >= 50;
        if (neg){
            direction.mult(-1);
        }
        direction.mult(ProjectSolaris.generator.nextInt(10) + 5);
        direction.rotate((float) (ProjectSolaris.generator.nextFloat() * Math.PI * 2));
        if (movesStraight){
            velocity = direction;
        }
        velocity = direction;
        radius = ProjectSolaris.generator.nextInt(8) + 6;
        hasTrail = radius < 10;
        if (hasTrail){
            trail = new Trail(locX, locY, radius, (int) (radius * 0.75));
        }
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
//                float slowRatio = 1 / (float) Math.pow(Math.max(0, (maxLife - life)), 4);
//                acceleration.x += direction.x * slowRatio;
//                acceleration.y += direction.y * slowRatio;
                acceleration.x = -direction.x * decay / maxLife;
                acceleration.y = -direction.y * decay / maxLife;
            } else {
                acceleration.x += randomness * (float) ProjectSolaris.generator.nextGaussian();
                acceleration.y += randomness * (float) ProjectSolaris.generator.nextGaussian();
            }
            update();
        }
        draw();
    }

}
