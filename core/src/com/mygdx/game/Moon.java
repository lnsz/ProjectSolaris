package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;

import java.util.Locale;

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
        velocity.x = (float) Math.sqrt((ProjectSolaris.GRAVITY_CONSTANT * planet.mass *
                ProjectSolaris.MASS_UNITS) / (altitude * ProjectSolaris.DISTANCE_UNITS * 1000000));
        if (clockwise) {
            velocity.mult(-1);
        }
        velocity.rotate((float)angle);
        this.planet = planet;
        this.mass = mass;
        gravity = true;
        generateSprites();
    }

    public Moon(float radius, float mass, boolean clockwise, double angle, Planet planet, float perigee, float apogee){
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
        this.mass = mass;
        gravity = true;
        generateSprites();
    }

    public void generateSprites(){
        int spriteNum = ProjectSolaris.randomInt(1, 3);
        texture = new Texture(Gdx.files.internal("moon/layer_" + String.format(Locale.US, "%02d", spriteNum) + ".png"));
        sprite = new Sprite(texture);
    }

    @Override
    public void run(){
        if (!ProjectSolaris.isPaused) {
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
        ProjectSolaris.batch.begin();
        sprite.setSize(radius * 2, radius * 2);
        sprite.setPosition(position.x - radius, position.y - radius);
        sprite.draw(ProjectSolaris.batch);
        ProjectSolaris.batch.end();
    }
}
