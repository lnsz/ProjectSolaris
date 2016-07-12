package com.example.lucas.missile_game;

import android.graphics.Canvas;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;

/**
 * Created by Kiko on 11/07/2016.
 */
public class EntitySystem {
    private ArrayList<Entity> entities;
    private Random generator;
    public EntitySystem(){
        entities = new ArrayList<Entity>();
        generator = new Random();
    }

    public void addParticle(float x, float y, float radius){
        entities.add(new Particle(x,y, radius, generator));
    }
    public void addParticle(float x, float y, float vx, float vy, float radius){
        entities.add(new Particle(x,y, vx, vy, radius, generator));
    }

    void run(Canvas canvas) {
        Iterator<Entity> it = entities.iterator();
        while (it.hasNext()) {
            Entity p = it.next();
            p.run(canvas);
            if (!p.alive) {
                it.remove();
            }
        }
    }
}
