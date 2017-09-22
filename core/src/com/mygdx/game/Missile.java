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
    float startTime, maxLife, life;
    Trail trail;

    public Missile(float locX, float locY, float tarX, float tarY, float str){
        super(locX, locY);
        ProjectSolaris.missile = true;

        // Load sprites and textures
        particleTexture = new Texture(Gdx.files.internal("dot.png"));
        particleSprite = new Sprite(particleTexture);
        explosionTexture = new Texture(Gdx.files.internal("explosion.png"));
        explosionSprite = new Sprite(explosionTexture);
        maxLife = 5000; // Missile lasts 5 seconds
        life = maxLife;
        this.radius = 10;
        startTime = TimeUtils.nanosToMillis(TimeUtils.nanoTime());
        // Set initial velocity of missile
        velocity = new Vector(tarX, tarY);
        velocity.sub(position);
        velocity.normalize();
        velocity.mult(str);
        trail = new Trail(locX, locY, radius, 20);

        this.flightParticles = new ParticleSystem(position.x, position.y, velocity, 300, 20, true, particleSprite);
    }

    @ Override
    public void update(){
        velocity.add(acceleration.scale());
        position.add(velocity.scale());
        acceleration.mult(0);
        life = maxLife - (TimeUtils.nanosToMillis(TimeUtils.nanoTime()) - startTime);

        // Destroy missiles outside entity border
        Vector distance = new Vector(position.x - ProjectSolaris.width / 2,
                position.y - ProjectSolaris.height / 2); // Distance from current position to center
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
        ProjectSolaris.camera.position.x += (position.x - ProjectSolaris.camera.position.x) * (maxLife / Math.max(life, 500)) * 0.1;
        ProjectSolaris.camera.position.y += (position.y - ProjectSolaris.camera.position.y) * (maxLife / Math.max(life, 500)) * 0.1;
        if(visible) {
            if (!ProjectSolaris.isPaused) {
                update();
                ProjectSolaris.entities.gravity(this);
                if (ProjectSolaris.entities.collision(this)) {
                    // explode!
                    explode();
                }
                if (life <= 0){
                    explode();
                }
            }
            draw();
        }
        else{
            deathParticles.update(position.x, position.y, velocity);
            if (!deathParticles.isAlive()){
                ProjectSolaris.resetCamera = true;
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
        velocity.add(acceleration.scale());
        position.add(velocity.scale());
        deathParticles = new ParticleSystem(position.x, position.y, 150, 75, false, explosionSprite);
        flightParticles.recycle = false;
    }



}
