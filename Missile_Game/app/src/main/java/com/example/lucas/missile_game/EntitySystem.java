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
    private ArrayList<Entity> entitieBuffer;
    private Iterator<Entity> it;
    public Random generator;
    public EntitySystem(){
        entities = new ArrayList<Entity>();
        entitieBuffer = new ArrayList<Entity>();
        generator = new Random();
    }


    public void addEntity(Entity entity){

        entitieBuffer.add(entity);
    }

    void run(Canvas canvas) {
        entities.addAll(entitieBuffer);
        entitieBuffer.clear();
        it= entities.iterator();
        while (it.hasNext()) {
            Entity p = it.next();
            p.run(canvas);
            if (!p.alive) {
                it.remove();
            }
        }
    }
}
