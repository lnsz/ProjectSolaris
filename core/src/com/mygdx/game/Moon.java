package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

/**
 * Created by lucas on 8/3/2016.
 */
public class Moon extends Obstacle{
    // Orbits planets, have gravity
    Planet planet;
    public Moon(float radius, float mass, boolean clockwise, double angle, Planet planet, float altitude) {
        // Circular orbit moon
        super(0, 0, radius);
        position.x = 0;
        position.y = -altitude;
        position.rotate((float)angle);
        position.add(planet.position);
        velocity.x = (float) Math.sqrt((MissileGame.GRAVITY_CONSTANT * planet.mass *
                MissileGame.MASS_UNITS) / (altitude * MissileGame.DISTANCE_UNITS * 1000000));
        if (clockwise) {
            velocity.mult(-1);
        }
        velocity.rotate((float)angle);
        this.planet = planet;
        this.mass = mass;
        gravity = true;
        texture = new Texture(Gdx.files.internal("moon.png"));
        sprite = new Sprite(texture);
    }

    public Moon(float radius, float mass, boolean clockwise, double angle, Planet planet, float perigee, float apogee){
        // Elliptical orbit moon
        super(0, 0, radius);
        position.x = 0;
        position.y = -perigee;
        position.rotate((float)angle);
        position.add(planet.position);
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
        texture = new Texture(Gdx.files.internal("moon.png"));
        sprite = new Sprite(texture);
    }

    @Override
    public void run(){
        if (!MissileGame.isPaused) {
            planet.gravity(this);
            update();
        }
        draw();
    }

    @Override
    public void draw(){
//        renderer.begin(ShapeRenderer.ShapeType.Filled);
//        renderer.setColor(255, 255, 255, 1);
//        renderer.circle(position.x, position.y, radius);
//        renderer.end();
        MissileGame.batch.begin();
        sprite.setSize(radius * 2, radius * 2);
        sprite.setPosition(position.x - radius, position.y - radius);
        sprite.draw(MissileGame.batch);
        MissileGame.batch.end();
    }
}
