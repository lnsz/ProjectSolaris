package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.utils.TimeUtils;

/**
 * Created by Kiko on 11/07/2016.
 */
public class Missile extends Entity{
    ParticleSystem flightParticles, deathParticles;
    Sprite particleSprite, explosionSprite;
    Texture particleTexture, explosionTexture;
    float startTime, life;
    Trail trail;

    public Missile(float locX, float locY, float tarX, float tarY, float str){
        super(locX, locY);
        ProjectSolaris.missile = true;

        // Load sprites and textures
        particleTexture = new Texture(Gdx.files.internal("dot.png"));
        particleSprite = new Sprite(particleTexture);
        explosionTexture = new Texture(Gdx.files.internal("explosion.png"));
        explosionSprite = new Sprite(explosionTexture);
        life = 10000; // Missile lasts 10 seconds
        startTime = TimeUtils.nanosToMillis(TimeUtils.nanoTime());
        // Set initial velocity of missile
        velocity = new Vector(tarX, tarY);
        velocity.sub(position);
        velocity.normalize();
        velocity.mult(str);
        trail = new Trail(locX, locY, radius, 20);

        this.flightParticles = new ParticleSystem(position.x, position.y, velocity, 300, 20, true, particleSprite);
    }

    @Override
    public void draw(){
        if (visible) {
            //float angle = velocity.getHeading();
            ProjectSolaris.renderer.begin(ShapeRenderer.ShapeType.Filled);
            ProjectSolaris.renderer.setColor(Color.WHITE);
            ProjectSolaris.renderer.circle(position.x, position.y, radius);
            ProjectSolaris.renderer.end();
        }
}

    @Override
    public void run(){
        if(visible) {
            if (!ProjectSolaris.isPaused) {
                update();
                ProjectSolaris.entities.gravity(this);
                if (ProjectSolaris.entities.collision(this)) {
                    // explode!
                    explode();
                }
                if (TimeUtils.nanosToMillis(TimeUtils.nanoTime()) - startTime > life){
                    explode();
                }
            }
            draw();
        }
        else{
            deathParticles.update(position.x, position.y, velocity);
            if (!deathParticles.isAlive()){
                alive = false;
            }
        }
        updateTrail();
        //flightParticles.update(position.x, position.y, velocity);
    }

    public void updateTrail(){
        trail.update(position.x, position.y);
        trail.compute();
        trail.draw();
    }


    public void explode(){
        ProjectSolaris.missile = false;
        visible = false;
        deathParticles = new ParticleSystem(position.x, position.y, 500, 100, false, explosionSprite);
        flightParticles.recycle = false;
    }



}
