package com.mygdx.game;

/**
 * Created by lucas on 8/3/2016.
 */
public class Planet extends Obstacle{
    public Planet(float locX, float locY, float radius, float mass){
        super(locX, locY, radius);
        gravity = true;
        // Mass
        this.mass = mass;
    }

    @Override
    public void update(){
        // planet doesn't move
    }
}
