package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

/**
 * Created by lucas on 8/5/2016.
 */
public class Asteroid extends Obstacle{
    // Orbits planets, don't have gravity
    Planet planet;
    Trail trail;
    public Asteroid(float radius, boolean clockwise, double angle, Planet planet, float altitude) {
        // Circular orbit moon
        super(0, 0, radius);
        position.x = 0;
        position.y = -altitude;
        position.rotate((float)angle);
        position.add(planet.position);
        velocity.x = (float) Math.sqrt((ProjectSolaris.GRAVITY_CONSTANT * planet.mass *
                ProjectSolaris.MASS_UNITS) / (altitude * ProjectSolaris.DISTANCE_UNITS * 1000000));
        if (clockwise) {
            velocity.mult(-1);
        }
        velocity.rotate((float)angle);
        this.planet = planet;
        gravity = true;
        texture = new Texture(Gdx.files.internal("asteroid.png"));
        sprite = new Sprite(texture);
        trail = new Trail(position.x, position.y, radius, 30);
    }

    public Asteroid(float radius, boolean clockwise, double angle, Planet planet, float perigee, float apogee){
        // Elliptical orbit moon
        super(0, 0, radius);
        position.x = 0;
        position.y = -perigee;
        position.rotate((float)angle);
        position.add(planet.position);
        double GM = ProjectSolaris.GRAVITY_CONSTANT * planet.mass * ProjectSolaris.MASS_UNITS;
        double r = perigee * ProjectSolaris.DISTANCE_UNITS * 1000000; // Radius of the point we want to find the velocity for
        double a = (apogee + perigee) / 2 * ProjectSolaris.DISTANCE_UNITS * 1000000; // Radius of the semi major axis
        velocity.x = (float) Math.sqrt(GM * (2/r - 1/a));
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
    public void run(){
        if(visible) {
            if (!ProjectSolaris.isPaused) {
                planet.gravity(this);
                update();
                updateTrail();
            }
            draw();
        }
        else{
            deathParticles.update(position.x, position.y, velocity);
            if (!deathParticles.isAlive()){
                alive = false;
            }
        }
    }

    @Override
    public void draw(){
//        renderer.begin(ShapeRenderer.ShapeType.Filled);
//        renderer.setColor(Color.GRAY);
//        renderer.circle(position.x, position.y, radius);
//        renderer.end();
        ProjectSolaris.batch.begin();
        sprite.setSize(radius * 2, radius * 2);
        sprite.setPosition(position.x - radius, position.y - radius);
        sprite.draw(ProjectSolaris.batch);
        ProjectSolaris.batch.end();
    }

    public void updateTrail(){
        ProjectSolaris.renderer.begin(ShapeRenderer.ShapeType.Filled);
        ProjectSolaris.renderer.setColor(Color.WHITE);
        trail.update(position.x, position.y);
        trail.compute();
        trail.draw();
        ProjectSolaris.renderer.end();
    }

}
