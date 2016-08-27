package com.mygdx.game;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;

/**
 * Created by Kiko on 11/07/2016.
 */
public class ParticleSystem2 {
    private ArrayList<Particle> particles;
    public Random generator;

    public ParticleSystem2() {
        particles = new ArrayList<Particle>();


        generator = new Random();
    }


    public void addParticle(Particle particle) {
       particles.add(particle);
    }



    void run(boolean move) {
        Iterator<Particle> it = particles.iterator();
        while (it.hasNext()) {
            Particle p = it.next();
            p.run(move);
            if (!p.alive) {
                it.remove();
            }
        }
    }
}
