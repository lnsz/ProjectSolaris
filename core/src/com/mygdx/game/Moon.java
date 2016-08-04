package com.mygdx.game;

/**
 * Created by lucas on 8/3/2016.
 */
public class Moon extends Obstacle{
    public Moon(float locX, float locY, float radius, boolean clockwise, float altitude){
        // Circular orbit moon
        super(locX, locY, radius);
        gravity = true;
        mass = (float)Math.pow(radius, 3);
    }

    public Moon(float locX, float locY,  float radius, boolean clockwise, float perigee, float apogee, float angle ){
        // Elliptical orbit moon
        super(locX, locY, radius);
        gravity = true;
        mass = (float)Math.pow(radius, 3);
    }
}
