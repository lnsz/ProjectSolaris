package com.mygdx.game;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;

/**
 * Created by Kiko on 11/07/2016.
 */
public class EntitySystem {
    private ArrayList<Entity> entities;

    private ArrayList<Entity> buffer;

    public EntitySystem() {
        entities = new ArrayList<Entity>();
        buffer = new ArrayList<Entity>();
    }


    public void addEntity(Entity entity) {
        buffer.add(entity);

    }

    public Entity collision(Entity missile){
        Iterator<Entity> it = entities.iterator();
        while (it.hasNext()) {
            Entity ent = it.next();
            if(Entity.collision(missile, ent)){
                return ent;
            }
        }
        return null;
    }

    public void gravity(Entity missile){
        Iterator<Entity> it = entities.iterator();
        while (it.hasNext()) {
            Entity ent = it.next();
            if (ent instanceof Obstacle){
                ((Obstacle)ent).gravity(missile);
            }
        }
    }

    void run() {
        entities.addAll(buffer);
        buffer.clear();

        Iterator<Entity> it = entities.iterator();
        while (it.hasNext()) {
            Entity p = it.next();
            p.run();
            if (!p.alive) {
                it.remove();
            }
        }
    }

    void reSeed(){
        Iterator<Entity> it = entities.iterator();
        while (it.hasNext()) {
            Entity p = it.next();
            if(p instanceof Planet){
                ((Planet) p).generateVariables();
                ((Planet) p).generateSprites();
            }
        }
    }

    void clear(){
        entities.clear();
    }
}