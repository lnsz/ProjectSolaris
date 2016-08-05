package com.mygdx.game;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

/**
 * Created by lucas on 8/3/2016.
 */
public class Moon extends Obstacle{
    Planet planet;
    public Moon(float locX, float locY, float radius, float mass, boolean clockwise, double angle, Planet planet, float altitude) {
        // Circular orbit moon
        super(locX, locY, radius);
        location.x = 0;
        location.y = -altitude;
        location.rotate((float)angle);
        location.add(planet.location);
        velocity.x = (float) Math.sqrt((MissileGame.GRAVITY_CONSTANT * planet.mass *
                MissileGame.MASS_UNITS) / (altitude * MissileGame.DISTANCE_UNITS * 1000000));
        if (clockwise) {
            velocity.y *= -1;
        }
        velocity.rotate((float)angle);
        this.planet = planet;
        this.mass = mass;
        gravity = true;
    }

    public Moon(float locX, float locY,  float radius, float mass, boolean clockwise, double angle, Planet planet, float perigee, float apogee){
        // Elliptical orbit moon
        super(locX, locY, radius);
        location.x = 0;
        location.y = -perigee;
        location.rotate((float)angle);
        location.add(planet.location);
        double GM = MissileGame.GRAVITY_CONSTANT * planet.mass * MissileGame.MASS_UNITS;
        double r = perigee * MissileGame.DISTANCE_UNITS * 1000000; // Radius of the point we want to find the velocity for
        double a = (apogee + perigee) / 2 * MissileGame.DISTANCE_UNITS * 1000000; // Radius of the semi major axis
        velocity.x = (float) Math.sqrt(GM * (2/r - 1/a));
        System.out.println(velocity);
        if(clockwise){
            velocity.mult(-1);
        }
        velocity.rotate((float)angle);
        this.planet = planet;
        this.mass = mass;
        gravity = true;
    }

    @Override
    public void run(SpriteBatch batch, ShapeRenderer renderer){
        planet.gravity(this);
        display(batch, renderer);
        update();
    }
}
