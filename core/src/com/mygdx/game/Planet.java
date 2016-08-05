package com.mygdx.game;

/**
 * Created by lucas on 8/3/2016.
 */
public class Planet extends Obstacle{
    public Planet(float locX, float locY, float radius){
        super(locX, locY, radius);
        gravity = true;
        mass = (float)Math.pow(radius, 3);
    }

    @Override
    public void update(){
        // planet doesn't move
    }
}
