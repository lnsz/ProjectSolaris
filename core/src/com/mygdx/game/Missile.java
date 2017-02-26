package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
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
    public Missile(float locX, float locY, float tarX, float tarY, float str){
        super(locX, locY);
        MissileGame.missile = true;

        // Load sprites and textures
        particleTexture = new Texture(Gdx.files.internal("dot.png"));
        particleSprite = new Sprite(particleTexture);
        explosionTexture = new Texture(Gdx.files.internal("explosion.png"));
        explosionSprite = new Sprite(explosionTexture);
        life = 20000; // Missile lasts 20 seconds
        startTime = TimeUtils.nanosToMillis(TimeUtils.nanoTime());
        // Set initial velocity of missile
        velocity = new Vector(tarX, tarY);
        velocity.sub(position);
        velocity.normalize();
        velocity.mult(str);
        this.flightParticles = new ParticleSystem(position.x, position.y, velocity, 300, 20, true, particleSprite);
    }

    @Override
    public void draw(){
        if (visible) {
            //float angle = velocity.getHeading();
            MissileGame.renderer.begin(ShapeRenderer.ShapeType.Filled);
            MissileGame.renderer.setColor(Color.WHITE);
            MissileGame.renderer.circle(position.x, position.y, radius);
            MissileGame.renderer.end();
        }
}

    @Override
    public void run(){
        if(visible) {
            if (!MissileGame.isPaused) {
                update();
                MissileGame.entities.gravity(this);
                if (MissileGame.entities.collision(this)) {
                    // explode!
                    explode();
                }
                //System.out.println(TimeUtils.nanosToMillis(TimeUtils.nanoTime()) - startTime + " " + TimeUtils.nanosToMillis(TimeUtils.nanoTime()) + " " + startTime);
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
        flightParticles.update(position.x, position.y, velocity);

    }

    public void explode(){
        MissileGame.missile = false;
        visible = false;
        deathParticles = new ParticleSystem(position.x, position.y, 500, 100, false, explosionSprite);
        flightParticles.recycle = false;
    }



}
