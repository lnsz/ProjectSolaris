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
    private ArrayList<Entity> entityBuffer;
    private Iterator<Entity> it;
    public Random generator;
    public EntitySystem(){
        entities = new ArrayList<Entity>();
        entityBuffer = new ArrayList<Entity>();
        generator = new Random();
    }


    public void addEntity(Entity entity){
        entityBuffer.add(entity);
    }

    void run(Canvas canvas) {
        entities.addAll(entityBuffer);
        entityBuffer.clear();

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
