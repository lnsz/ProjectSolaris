package com.example.lucas.missile_game;

import android.graphics.Canvas;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;

/**
 * Created by Kiko on 11/07/2016.
 */
public class ParticleSystem {
    private ArrayList<Particle> particles;
    private Random generator;
    public ParticleSystem(){
        particles = new ArrayList<Particle>();
        generator = new Random();
    }

    public void addParticle(float x, float y, float radius){
        particles.add(new Particle(x,y, radius, generator));
    }

    void run(Canvas canvas) {
        Iterator<Particle> it = particles.iterator();
        while (it.hasNext()) {
            Particle p = (Particle)it.next();
            p.acceleration.x = 0.1f *(float)generator.nextGaussian();
            p.acceleration.y = 0.1f *(float)generator.nextGaussian();
            p.run(canvas);
            if (!p.alive) {
                it.remove();
            }
        }
    }
}
