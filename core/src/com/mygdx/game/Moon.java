package com.mygdx.game;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

/**
 * Created by lucas on 8/3/2016.
 */
public class Moon extends Obstacle{
    Planet planet;
    public Moon(float locX, float locY, float radius, boolean clockwise, Planet planet, float altitude){
        // Circular orbit moon
        super(locX, locY, radius);
        location.x = planet.location.x - altitude;
        location.y = planet.location.y;
        velocity.y = (float)Math.sqrt(MissileGame.GRAVITY_CONSTANT * planet.mass / (planet.location.x - location.x));
        if (clockwise){
            velocity.y *= -1;
        }
        this.planet = planet;
        gravity = true;
        mass = (float)Math.pow(radius, 3);
    }

    public Moon(float locX, float locY,  float radius, boolean clockwise, Planet planet, float perigee, float apogee, float angle ){
        // Elliptical orbit moon
        super(locX, locY, radius);
        location.x = planet.location.x;
        location.y = planet.location.y + perigee;
        float e = (apogee - perigee) / (apogee + perigee); // How close orbit is to being a circle
        float axis = (apogee + perigee) / 2;
        float vel = (float)Math.sqrt(MissileGame.GRAVITY_CONSTANT * planet.mass * ((2 / (axis - e)) - (1 / axis)));
        velocity.x = (planet.location.x - location.x);
        velocity.y = (planet.location.y - location.y);
        velocity.normalize();
        velocity.rotate((float)Math.PI /  2);
        velocity.mult(vel);
        if(clockwise){
            velocity.mult(-1);
        }
        this.planet = planet;
        gravity = true;
        mass = (float)Math.pow(radius, 3);
    }

    @Override
    public void run(SpriteBatch batch, ShapeRenderer renderer){
        planet.gravity(this);
        display(batch, renderer);
        update();
    }
}
