package com.mygdx.game;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;

/**
 * Created by Kiko on 11/07/2016.
 */
public class ParticleSystem2 {
    private ArrayList<Particle> particles;

    public ParticleSystem2() {
        particles = new ArrayList<Particle>();
    }


    public void addParticle(Particle particle) {
       particles.add(particle);
    }


    void run() {
        Iterator<Particle> it = particles.iterator();
        while (it.hasNext()) {
            Particle p = it.next();
            p.run();
            if (!p.alive) {
                it.remove();
            }
        }
    }
}
