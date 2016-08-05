package com.mygdx.game;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

/**
 * Created by lucas on 7/13/2016.
 */
public class Obstacle extends Entity{
    boolean gravity;
    public Obstacle(float locX, float locY, float radius){
        super(locX, locY);
        this.radius = radius;
    }


    public void gravity (Entity other){
        Vector newAcceleration =  new Vector(this.location.x - other.location.x, this.location.y - other.location.y);
        float distanceSq = newAcceleration.magSq();
        newAcceleration.normalize();
        newAcceleration.mult(MissileGame.GRAVITY_CONSTANT * this.mass / distanceSq);
        other.acceleration.add(newAcceleration);
    }

}
