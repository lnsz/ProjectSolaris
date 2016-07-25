package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

/**
 * Created by Kiko on 11/07/2016.
 */
public class Missile extends Entity{
    ParticleSystem particles;
    EntitySystem entities;
    Sprite particleSprite, explosionSprite;
    Texture particleTexture, explosionTexture;
    public Missile(float locX, float locY, float tarX, float tarY, float str, EntitySystem entities){
        super(locX, locY);
        // Load sprites and textures
        particleTexture = new Texture(Gdx.files.internal("dot.png"));
        particleSprite = new Sprite(particleTexture);
        explosionTexture = new Texture(Gdx.files.internal("explosion.png"));
        explosionSprite = new Sprite(explosionTexture);


        //get entities array
        this.entities = entities;
        // Set initial velocity of missile
        velocity = new Vector(tarX, tarY);
        velocity.sub(location);
        velocity.normalize();
        velocity.mult(str);
        this.particles = new ParticleSystem(location.x, location.y, velocity, 500, 40, true, particleSprite);
    }

    @Override
    public void display(SpriteBatch batch, ShapeRenderer renderer){
        if (visible) {
            float angle = velocity.getHeading();
            renderer.begin(ShapeRenderer.ShapeType.Filled);
            renderer.setColor(255, 255, 255, 1);
            renderer.circle(location.x, location.y, radius);
            renderer.end();
        }
}

    @Override
    public void run(SpriteBatch batch, ShapeRenderer renderer){
        if(visible) {
            entities.gravity(this);
            if (entities.collision(this)) {
                // explode!
                explode();
            }
            display(batch, renderer);
        }
        else{
            if (!particles.isAlive()){
                alive = false;
            }
        }
        particles.update(location.x, location.y, velocity, batch, renderer);
        update();
    }

    public void explode(){
        visible = false;
        particles = new ParticleSystem(location.x, location.y, 1000, 225, false, explosionSprite);
    }



}
