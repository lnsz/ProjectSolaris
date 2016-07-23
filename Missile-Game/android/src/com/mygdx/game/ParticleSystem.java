package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

import java.util.ArrayList;

/**
 * Created by lucas on 7/22/2016.
 */
public class ParticleSystem {
    ArrayList<Particle> particles = new ArrayList<Particle>();
    boolean recycle;  // True iff this particle system can recycle dead particles
    int lastAlive, maxRecycle, size;  // Index of last live particle, number of particles that can be recycled per loop, total size of particle system
    float particleDuration;
    Sprite sprite;
    Texture texture;
    Vector location;

    public ParticleSystem(float x, float y, int size, boolean recycle){
        texture = new Texture(Gdx.files.internal("dot.png"));
        sprite = new Sprite(texture);
        location = new Vector(x, y);
        this.recycle = recycle;
        this.size = size;
        particleDuration = 1;
        if (size < particleDuration){
            particleDuration = size;
        }
        maxRecycle = (int)(size / particleDuration);
        // Particle system starts with some particles and recycles maxRecycle on every loop
        for (int i = 0; i < size; i++){
            particles.add(new Particle(x, y, particleDuration, sprite));
        }
        lastAlive = size - 1;
    }

    public void update(float x, float y, Vector velocity, SpriteBatch batch, ShapeRenderer renderer){
        this.location.x = x;
        this.location.y = y;
        int index = 0;
        Particle p;
        batch.begin();
        while (index < lastAlive){
            p = particles.get(index);

            if (p.alive) {
                p.run(batch, renderer);
                index++;
            }
            else{ // Swap dead particle with last live particle
                particles.set(index, particles.get(lastAlive));
                particles.set(lastAlive, p);
                lastAlive--;
            }
        }
        if(recycle){
            for (int i = 1; i < maxRecycle + 1; i++) {
                if (lastAlive + i < size) {
                    p = particles.get(lastAlive + i);
                    p.alive = true;
                    p.location = this.location;
                    p.life = p.maxLife;
                    p.acceleration.reset();
                    p.velocity = Vector.mult(velocity, -1);
                    p.velocity.normalize();
                    p.velocity.mult(10);
                    lastAlive ++;
                }
            }
        }
        batch.end();
    }
}
