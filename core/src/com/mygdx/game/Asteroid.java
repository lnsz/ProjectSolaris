package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

/**
 * Created by lucas on 8/5/2016.
 */
public class Asteroid extends Obstacle{
    // Orbits planets, don't have gravity
    Planet planet;
    public Asteroid(float radius, boolean clockwise, double angle, Planet planet, float altitude) {
        // Circular orbit moon
        super(0, 0, radius);
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
        gravity = true;
        texture = new Texture(Gdx.files.internal("asteroid.png"));
        sprite = new Sprite(texture);
    }

    public Asteroid(float radius, boolean clockwise, double angle, Planet planet, float perigee, float apogee){
        // Elliptical orbit moon
        super(0, 0, radius);
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
        gravity = true;
        texture = new Texture(Gdx.files.internal("asteroid.png"));
        sprite = new Sprite(texture);
    }

    @Override
    public void run(SpriteBatch batch, ShapeRenderer renderer){
        planet.gravity(this);
        display(batch, renderer);
        update();
    }

    @Override
    public void display(SpriteBatch batch, ShapeRenderer renderer){
//        renderer.begin(ShapeRenderer.ShapeType.Filled);
//        renderer.setColor(Color.GRAY);
//        renderer.circle(location.x, location.y, radius);
//        renderer.end();
        batch.begin();
        sprite.setSize(radius * 2, radius * 2);
        sprite.setPosition(location.x - radius, location.y - radius);
        sprite.draw(batch);
        batch.end();
    }
}
