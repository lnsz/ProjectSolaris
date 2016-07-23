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
    Vector location;

    public ParticleSystem(float x, float y,  int size, int duration, boolean recycle, Sprite sprite){
        this.sprite = sprite;
        location = new Vector(x, y);
        this.recycle = recycle;
        this.size = size;
        particleDuration = duration;
        if (size < particleDuration){
            particleDuration = size;
        }
        maxRecycle = (int)(size / particleDuration);
        // Particle system starts with 1 particle and recycles maxRecycle on every loop, or all particles if it's not recyclable
        Particle p = new Particle(x, y, particleDuration, sprite);
        particles.add(p);
        for (int i = 0; i < size; i++){
            p = new Particle(x, y, particleDuration, sprite);
            if (recycle) {
                p.alive = false;
            }
            particles.add(p);
        }
        if (recycle) {
            lastAlive = 1;
        }
        else{
            lastAlive = size - 1;
        }
    }


    public ParticleSystem(float x, float y, Vector velocity, int size, int duration, boolean recycle, Sprite sprite){
        this.sprite = sprite;
        location = new Vector(x, y);
        this.recycle = recycle;
        this.size = size;
        particleDuration = duration;
        if (size < particleDuration){
            particleDuration = size;
        }
        maxRecycle = (int)(size / particleDuration);
        // Particle system starts with 1 particle and recycles maxRecycle on every loop, or all particles if it's not recyclable
        Particle p = new Particle(x, y, particleDuration, sprite);
        particles.add(p);
        for (int i = 0; i < size; i++){
            p = new Particle(x, y, particleDuration, sprite);
            p.velocity = Vector.mult(velocity, -1);
            p.velocity.normalize();
            p.velocity.mult(10);
            if (recycle) {
                p.alive = false;
            }
            particles.add(p);
        }
        if (recycle) {
            lastAlive = 1;
        }
        else{
            lastAlive = size - 1;
        }
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
                    Particle m = new Particle (x, y, particleDuration, sprite);
                    m.velocity = Vector.mult(velocity, -1);
                    m.velocity.normalize();
                    m.velocity.mult(10);
                    particles.set(lastAlive + i, m);
                    lastAlive ++;
                }
            }
        }
        batch.end();
    }

    public boolean isAlive(){
        return lastAlive > 0;
    }
}
