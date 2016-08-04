package com.mygdx.game;

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
        newAcceleration.mult((float)(1 * this.mass / distanceSq));
        other.acceleration.add(newAcceleration);
    }
}
