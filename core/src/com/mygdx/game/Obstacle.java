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
        double distance = newAcceleration.mag();
        distance = distance * MissileGame.DISTANCE_UNITS * 1000; // Convert pixels to meters
        double distanceSq = Math.pow(distance, 2);
        double m = this.mass * MissileGame.MASS_UNITS; // Convert from game mass units to kg
        newAcceleration.normalize();
        newAcceleration.mult((float)(MissileGame.GRAVITY_CONSTANT * m / distanceSq));
        newAcceleration.mult((float)MissileGame.DISTANCE_UNITS); // Convert from m/s^2 to pixels/s^2
        other.acceleration.add(newAcceleration);
    }

}
