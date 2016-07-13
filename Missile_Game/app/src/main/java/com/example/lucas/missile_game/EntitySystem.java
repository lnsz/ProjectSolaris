package com.example.lucas.missile_game;

import android.graphics.Canvas;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;

/**
 * Created by Kiko on 11/07/2016.
 */
public class EntitySystem {
    private ArrayList<Entity> solidEntities;
    private ArrayList<Entity> ghostEntities;

    private ArrayList<Entity> solidBuffer;
    private ArrayList<Entity> ghostBuffer;
    private Iterator<Entity> it;
    public Random generator;

    public EntitySystem() {
        solidEntities = new ArrayList<Entity>();
        ghostEntities = new ArrayList<Entity>();

        ghostBuffer = new ArrayList<Entity>();
        solidBuffer = new ArrayList<Entity>();

        generator = new Random();
    }


    public void addEntity(Entity entity, boolean collision) {
        if (collision) {
            solidBuffer.add(entity);
        } else {
            ghostBuffer.add(entity);

        }
    }

    void run(Canvas canvas) {
        solidEntities.addAll(solidBuffer);
        solidBuffer.clear();

        ghostEntities.addAll(ghostBuffer);
        ghostBuffer.clear();

        it = solidEntities.iterator();
        while (it.hasNext()) {
            Entity p = it.next();
            p.run(canvas);
            if (!p.alive) {
                it.remove();
            }
        }

        it = ghostEntities.iterator();
        while (it.hasNext()) {
            Entity p = it.next();
            p.run(canvas);
            if (!p.alive) {
                it.remove();
            }
        }


    }
}
