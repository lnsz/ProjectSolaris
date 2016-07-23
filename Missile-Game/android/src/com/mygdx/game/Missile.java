package com.mygdx.game;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

/**
 * Created by Kiko on 11/07/2016.
 */
public class Missile extends Entity{
    EntitySystem entities;
    public Missile(float locX, float locY, float tarX, float tarY, float str, EntitySystem entities){
        super(locX, locY);
        //get entities array
        this.entities = entities;
        // Set initial velocity of missile
        velocity = new Vector(tarX, tarY);
        velocity.sub(location);
        velocity.normalize();
        velocity.mult(str);

    }

    @Override
    public void display(SpriteBatch batch, ShapeRenderer renderer){
        float angle = velocity.getHeading();
        renderer.begin(ShapeRenderer.ShapeType.Filled);
        renderer.setColor(255, 255, 255, 1);
        //renderer.rotate(0, 1, 0 , angle);
        renderer.circle(location.x, location.y, radius);
        renderer.end();

        //canvas.drawPath(path, paint);

        Particle p;
        for (int i = 0; i < 8; i++) {
            p = new Particle(location.x, location.y, 3, 20, entities.generator);
            p.randomness = 2;
            p.velocity = Vector.mult(this.velocity, -1);
            p.velocity.normalize();
            p.velocity.mult(20);
            entities.addEntity(p, false);
        }
    }

    @Override
    public void run(SpriteBatch batch, ShapeRenderer renderer){
        entities.gravity(this);
        if(entities.collision(this)){
            //explode!
            explode();
        }
        display(batch, renderer);
        update();
    }

    public void explode(){
        alive = false;
        for(int i = 0; i < 4; i++) {
            Particle p = new Particle(location.x, location.y, 10, 60, entities.generator);
            if(i % 2 == 0) {
                //p.paint.setARGB(255,255, 0, 0);
            }else{
                //p.paint.setARGB(255, 255, 69, 0);
            }
            p.velocity = Vector.random();
            p.velocity.mult(0.5f);
            p.radius = 40;
            entities.addEntity(p, false);
        }
    }



}
